package com.example.a171y034.travelbook719.Album.Model;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.a171y034.travelbook719.Album.Entity.AlbumFolderInfo;
import com.example.a171y034.travelbook719.Album.Entity.ImageInfo;
import com.example.a171y034.travelbook719.Album.presenter.entity.ImageScanResult;
import com.example.a171y034.travelbook719.Album.View.entity.AlbumViewData;
import com.example.a171y034.travelbook719.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ImageScannerModelImpl implements ImageScannerModel {

    private final static String TAG = ImageScannerModelImpl.class.getSimpleName();
    /**
     *  Loader固有のID番号
     */
    private final static int IMAGE_LOADER_ID = 1000;
    /**
     *  データマッピングを読み込む
     */
    private final static String[] IMAGE_PROJECTION = new String[]{
            MediaStore.Images.Media.DATA,//画像パス
            MediaStore.Images.Media.DISPLAY_NAME,//画像ファイル名（接尾辞名を含む）
            MediaStore.Images.Media.TITLE//画像ファイル名、接尾辞なし
    };

    private OnScanImageFinish mOnScanImageFinish;

    private Handler mRefreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ImageScanResult imageScanResult = (ImageScanResult) msg.obj;
            if (mOnScanImageFinish != null && imageScanResult != null) {
                mOnScanImageFinish.onFinish(imageScanResult);
            }
        }
    };

    @Override
    public void startScanImage(final Context context, LoaderManager loaderManager, final OnScanImageFinish onScanImageFinish) {
        mOnScanImageFinish = onScanImageFinish;
        LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                Log.i(TAG, "-----onCreateLoader-----");
                CursorLoader imageCursorLoader = new CursorLoader(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
                return imageCursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                Log.i(TAG, "-----onLoadFinished-----");
                if (data.getCount() == 0) {
                    if (onScanImageFinish != null) {
                        onScanImageFinish.onFinish(null);//イメージが直接nullを返さない
                    }

                } else {
                    int dataColumnIndex = data.getColumnIndex(MediaStore.Images.Media.DATA);
                    //int displayNameColumnIndex = data.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
                    //int titleColumnIndex = data.getColumnIndex(MediaStore.Images.Media.TITLE);
                    ArrayList<File> albumFolderList = new ArrayList<>();
                    HashMap<String, ArrayList<File>> albumImageListMap = new HashMap<>();
                    while (data.moveToNext()) {
                        File imageFile = new File(data.getString(dataColumnIndex));//画像ファイル
                        File albumFolder = imageFile.getParentFile();//写真のカタログ
                        if (!albumFolderList.contains(albumFolder)) {
                            albumFolderList.add(albumFolder);
                        }
                        String albumPath = albumFolder.getAbsolutePath();
                        ArrayList<File> albumImageFiles = albumImageListMap.get(albumPath);
                        if (albumImageFiles == null) {
                            albumImageFiles = new ArrayList<>();
                            albumImageListMap.put(albumPath, albumImageFiles);
                        }
                        albumImageFiles.add(imageFile);//対応するアルバムディレクトリに追加する
                    }

                    sortByFileLastModified(albumFolderList);//画像ディレクトリをソートする


                    Set<String> keySet = albumImageListMap.keySet();
                    for (String key : keySet) {//画像ディレクトリ内のすべての画像ファイルをソートする
                        ArrayList<File> albumImageList = albumImageListMap.get(key);
                        sortByFileLastModified(albumImageList);
                    }

                    ImageScanResult imageScanResult = new ImageScanResult();
                    imageScanResult.setAlbumFolderList(albumFolderList);
                    imageScanResult.setAlbumImageListMap(albumImageListMap);

                    //Fix CursorLoader Bug
                    //http://stackoverflow.com/questions/7746140/android-problems-using-fragmentactivity-loader-to-update-fragmentstatepagera
                    Message message = mRefreshHandler.obtainMessage();
                    message.obj = imageScanResult;
                    mRefreshHandler.sendMessage(message);

                }

            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                Log.i(TAG, "-----onLoaderReset-----");
            }
        };
        loaderManager.initLoader(IMAGE_LOADER_ID, null, loaderCallbacks);//指定したIDでLoaderを初期化する
    }

    @Override
    public AlbumViewData archiveAlbumInfo(Context context, ImageScanResult imageScanResult) {
        if (imageScanResult != null) {

            List<File> albumFolderList = imageScanResult.getAlbumFolderList();
            Map<String, ArrayList<File>> albumImageListMap = imageScanResult.getAlbumImageListMap();

            if (albumFolderList != null && albumFolderList.size() > 0 && albumImageListMap != null) {

                List<AlbumFolderInfo> albumFolderInfoList = new ArrayList<>();

                AlbumFolderInfo allImageFolder = createAllImageAlbum(context, albumImageListMap);
                if (allImageFolder != null) {
                    albumFolderInfoList.add(allImageFolder);
                }

                int albumFolderSize = albumFolderList.size();
                for (int albumFolderPos = 0; albumFolderPos < albumFolderSize; albumFolderPos++) {

                    File albumFolder = albumFolderList.get(albumFolderPos);
                    AlbumFolderInfo albumFolderInfo = new AlbumFolderInfo();

                    String folderName = albumFolder.getName();
                    albumFolderInfo.setFolderName(folderName);

                    String albumPath = albumFolder.getAbsolutePath();
                    List<File> albumImageList = albumImageListMap.get(albumPath);
                    File frontCover = albumImageList.get(0);
                    albumFolderInfo.setFrontCover(frontCover);//最初の画像を設定する

                    List<ImageInfo> imageInfoList = ImageInfo.buildFromFileList(albumImageList);
                    albumFolderInfo.setImageInfoList(imageInfoList);
                    allImageFolder.getImageInfoList().addAll(imageInfoList);// "すべての写真"ディレクトリに保存

                    albumFolderInfoList.add(albumFolderInfo);
                }

                AlbumViewData albumViewData = new AlbumViewData();
                albumViewData.setAlbumFolderInfoList(albumFolderInfoList);

                return albumViewData;
            }

            return null;
        } else {
            return null;
        }
    }

    /**
     * 「すべての写真」ディレクトリを作成する
     *
     * @param albumImageListMap
     * @return
     */
    private AlbumFolderInfo createAllImageAlbum(Context context, Map<String, ArrayList<File>> albumImageListMap) {
        if (albumImageListMap != null) {
            AlbumFolderInfo albumFolderInfo = new AlbumFolderInfo();
            albumFolderInfo.setFolderName(context.getString(R.string.all_image));//ディレクトリ名を設定する

            List<ImageInfo> totalImageInfoList = new ArrayList<>();
            albumFolderInfo.setImageInfoList(totalImageInfoList);//すべての画像ファイルを設定する

            boolean isFirstAlbum = true; //最初のディレクトリですか？

            Set<String> albumKeySet = albumImageListMap.keySet();
            for (String albumKey : albumKeySet) {//各ディレクトリの画像
                List<File> albumImageList = albumImageListMap.get(albumKey);

                if (isFirstAlbum == true) {
                    File frontCover = albumImageList.get(0);
                    albumFolderInfo.setFrontCover(frontCover);//最初の画像を設定する

                    isFirstAlbum = false;
                }
            }

            return albumFolderInfo;
        } else {
            return null;
        }
    }


    /**
     * ファイルの変更時刻に応じて並べ替え、最近変更されたもの、より高度なもの
     */
    private void sortByFileLastModified(List<File> files) {
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                if (lhs.lastModified() > rhs.lastModified()) {
                    return -1;
                } else if (lhs.lastModified() < rhs.lastModified()) {
                    return 1;
                }
                return 0;
            }
        });
    }

}
