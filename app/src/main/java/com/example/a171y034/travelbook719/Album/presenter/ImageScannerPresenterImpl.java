package com.example.a171y034.travelbook719.Album.presenter;

import android.content.Context;
import android.support.v4.app.LoaderManager;

import com.example.a171y034.travelbook719.Album.presenter.entity.ImageScanResult;
import com.example.a171y034.travelbook719.Album.View.AlbumView;
import com.example.a171y034.travelbook719.Album.View.entity.AlbumViewData;
import com.example.a171y034.travelbook719.Album.Model.ImageScannerModel;
import com.example.a171y034.travelbook719.Album.Model.ImageScannerModelImpl;


/**
 * 图片扫描Presenter实现类
 イメージスキャンプレゼンター実装クラス
 * <p/>
 * Created by Clock on 2016/3/21.
 */
public class ImageScannerPresenterImpl implements ImageScannerPresenter {

    private ImageScannerModel mScannerModel;
    private AlbumView mAlbumView;

    public ImageScannerPresenterImpl(AlbumView albumView) {
        mScannerModel = new ImageScannerModelImpl();
        mAlbumView = albumView;
    }

    @Override
    public void startScanImage(final Context context, LoaderManager loaderManager) {
        mScannerModel.startScanImage(context, loaderManager, new ImageScannerModel.OnScanImageFinish() {
            @Override
            public void onFinish(ImageScanResult imageScanResult) {
                if (mAlbumView != null) {
                    AlbumViewData albumData = mScannerModel.archiveAlbumInfo(context, imageScanResult);
                    mAlbumView.refreshAlbumData(albumData);
                }
            }
        });
    }

}
