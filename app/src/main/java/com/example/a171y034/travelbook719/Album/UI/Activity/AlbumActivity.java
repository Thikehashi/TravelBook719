package com.example.a171y034.travelbook719.Album.UI.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a171y034.travelbook719.Album.Entity.AlbumFolderInfo;
import com.example.a171y034.travelbook719.Album.Entity.ImageInfo;
import com.example.a171y034.travelbook719.Album.UI.Activity.base.BaseActivity;
import com.example.a171y034.travelbook719.Album.UI.Fragment.AlbumDetailFragment;
import com.example.a171y034.travelbook719.Album.UI.Fragment.AlbumFolderFragment;
import com.example.a171y034.travelbook719.Album.View.AlbumView;
import com.example.a171y034.travelbook719.Album.View.ImageChooseView;
import com.example.a171y034.travelbook719.Album.View.entity.AlbumViewData;
import com.example.a171y034.travelbook719.Album.presenter.ImageScannerPresenter;
import com.example.a171y034.travelbook719.Album.presenter.ImageScannerPresenterImpl;
import com.example.a171y034.travelbook719.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * システムアルバムページ
 *
 * @author Clock
 */
public class AlbumActivity extends BaseActivity implements View.OnClickListener, ImageChooseView, AlbumView {

    private final static String TAG = AlbumActivity.class.getSimpleName();
    private final static String FRAGMENT_BACK_STACK = "FragmentBackStack";
    private final static String PACKAGE_URL_SCHEME = "package:";

    /**
     *Android Mのランタイムパーミッション機能のアプリケーション権限
     */
    private final static int REQUEST_READ_EXTERNAL_STORAGE_CODE = 1;
    /**
     * アルバムリストページ
     */
    private AlbumFolderFragment mAlbumFolderFragment;
    /**
     * アルバムの詳細ページ
     */
    private HashMap<AlbumFolderInfo, AlbumDetailFragment> mAlbumDetailFragmentMap = new HashMap<>();
    /**
     * 選択された画像ファイルリスト
     */
    private ArrayList<File> mSelectedImageFileList = new ArrayList<>();

    private ImageScannerPresenter mImageScannerPresenter;
    /**
     * アルバムディレクトリ情報リスト
     */
    private List<AlbumFolderInfo> mAlbumFolderInfoList;
    /**
     *イメージディレクトリの名前を表示し、イメージのボタンを選択します
     */
    private TextView mTitleView, mSelectedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);



        mTitleView = (TextView) findViewById(R.id.tv_dir_title);
        mSelectedView = (TextView) findViewById(R.id.tv_selected_ok);
        mSelectedView.setOnClickListener(this);

        findViewById(R.id.iv_back).setOnClickListener(this);

        mImageScannerPresenter = new ImageScannerPresenterImpl(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, R.string.grant_advice_read_album, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE_CODE);
        } else {
            mImageScannerPresenter.startScanImage(getApplicationContext(), getSupportLoaderManager());
        }
    }

/********************************************* パーミッション要求ここから**********************************************************************************/

    /**
     *アクセス許可のプロンプトを表示するための表示ダイアログボックス
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.help);
        builder.setMessage(R.string.help_content);

        builder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AlbumActivity.this, R.string.grant_permission_failure, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startSystemSettings();
                finish();
            }
        });

        builder.show();
    }

    /**
     *起動システム許可設定インターフェース
     */
    private void startSystemSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE_CODE) {
            boolean granted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (granted) {
                Toast.makeText(this, R.string.grant_permission_success, Toast.LENGTH_SHORT).show();
                mImageScannerPresenter.startScanImage(getApplicationContext(), getSupportLoaderManager());

            } else {
                showMissingPermissionDialog();//プロンプトダイアログ
                //Toast.makeText(this, R.string.grant_permission_failure, Toast.LENGTH_SHORT).show();
            }
        }
    }

/********************************************* パーミッション要求ここまで**********************************************************************************/

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        // 前の画面に戻る
        if (viewId == R.id.iv_back) {
            onBackPressed();
        } else if (viewId == R.id.tv_selected_ok) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            /*
            Intent intent = new Intent(AlbumActivity.this, PhotoMainFragment.class);
            intent.putExtra(PhotoMainFragment.EXTRA_SELECTED_IMAGE_LIST, mSelectedImageFileList);
            Intent showSelectedIntent = new Intent(this, ImageSelectActivity.class);
            showSelectedIntent.putExtra(ImageSelectActivity.EXTRA_SELECTED_IMAGE_LIST, mSelectedImageFileList);
            */
            /*
            PhotoMainFragment fragment = new PhotoMainFragment();
            Bundle args = new Bundle();
            args.putStringArrayList(PhotoMainFragment.EXTRA_SELECTED_IMAGE_LIST, mSelectedImageFileList);
            fragment.setArguments(args);
            fragmentManager.beginTransaction().replace(R.id.content, fragment);
            */
            /*
            Bundle bundle = new Bundle();
            bundle.putAll(PhotoMainFragment.EXTRA_SELECTED_IMAGE_LIST, mSelectedImageFileList);
            PhotoMainFragment fragment = new PhotoMainFragment();
            fragment.setArguments(bundle);
            */
            finish();
            /*
            Intent showSelectedIntent = new Intent(this, ImageSelectActivity.class);
            showSelectedIntent.putExtra(ImageSelectActivity.EXTRA_SELECTED_IMAGE_LIST, mSelectedImageFileList);
            startActivity(showSelectedIntent);
            finish();
            */
        }
    }

    @Override
    public void switchAlbumFolder(AlbumFolderInfo albumFolderInfo) {
        if (albumFolderInfo != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            AlbumDetailFragment albumDetailFragment = mAlbumDetailFragmentMap.get(albumFolderInfo);
            if (albumDetailFragment == null) {
                List<ImageInfo> imageInfoList = albumFolderInfo.getImageInfoList();
                albumDetailFragment = AlbumDetailFragment.newInstance(imageInfoList);
                mAlbumDetailFragmentMap.put(albumFolderInfo, albumDetailFragment);
            }
            fragmentTransaction.replace(R.id.fragment_container, albumDetailFragment);
            fragmentTransaction.addToBackStack(FRAGMENT_BACK_STACK);
            fragmentTransaction.commit();

            refreshFolderName(albumFolderInfo.getFolderName());
        }
    }

    /**
     * ディレクトリ名をリフレッシュ
     *
     * @param albumFolderName
     */
    private void refreshFolderName(String albumFolderName) {
        if (!TextUtils.isEmpty(albumFolderName)) {
            mTitleView.setText(albumFolderName);
        }
    }

    /**
     * アルバムリストに切り替え
     */
    private void switchAlbumFolderList() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mAlbumFolderFragment);
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int backStackCount = fragmentManager.getBackStackEntryCount();
                if (backStackCount == 0) {
                    AlbumFolderInfo albumFolderInfo = mAlbumFolderInfoList.get(0);
                    String folderName = albumFolderInfo.getFolderName();
                    refreshFolderName(folderName);
                }
            }
        });
        //fragmentTransaction.commit(); //生産 java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        fragmentTransaction.commitAllowingStateLoss();//http://stackoverflow.com/questions/25486656/java-lang-illegalstateexceptioncan-not-perform-this-action-after-onsaveinstance
    }


    /**
     * 選択したボタンの状態を更新
     */
    private void refreshSelectedViewState() {
        if (mSelectedImageFileList.size() == 0) {
            mSelectedView.setVisibility(View.GONE);

        } else {
            String selectedStringFormat = getString(R.string.selected_ok);
            int selectedSize = mSelectedImageFileList.size();
            AlbumFolderInfo albumFolderInfo = mAlbumFolderInfoList.get(0);
            int totalSize = albumFolderInfo.getImageInfoList().size();
            String selectedString = String.format(selectedStringFormat, selectedSize, totalSize);
            mSelectedView.setText(selectedString);
            mSelectedView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void refreshAlbumData(AlbumViewData albumData) {
        if (albumData != null) {
            mAlbumFolderInfoList = albumData.getAlbumFolderInfoList();
            mAlbumFolderFragment = AlbumFolderFragment.newInstance(mAlbumFolderInfoList);
            switchAlbumFolderList();

            findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);//アルバムのリストエリアを表示

        } else {
            findViewById(R.id.fragment_container).setVisibility(View.GONE);//アルバムリストを表示する領域を非表示
            findViewById(R.id.tv_no_image).setVisibility(View.VISIBLE);//写真なしのヒントを表示

        }
    }

    @Override
    public void refreshSelectedCounter(ImageInfo imageInfo) {
        if (imageInfo != null) {
            boolean isSelected = imageInfo.isSelected();
            File imageFile = imageInfo.getImageFile();
            if (isSelected) {//選択
                if (!mSelectedImageFileList.contains(imageFile)) {
                    mSelectedImageFileList.add(imageFile);
                }
            } else {//チェックしない
                if (mSelectedImageFileList.contains(imageFile)) {
                    mSelectedImageFileList.remove(imageFile);
                }
            }
            refreshSelectedViewState();
        }
    }
}
