package com.example.a171y034.travelbook719.Album.presenter;

import android.content.Context;
import android.support.v4.app.LoaderManager;

/**
 * 图片扫描Presenter层
 画像スキャンプレゼンターレイヤー
 * <p/>
 * Created by Clock on 2016/3/19.
 */
public interface ImageScannerPresenter {

    /**
     * 扫描获取图片文件夹列表スキャンして画像フォルダのリストを取得する
     *
     * @param context
     * @param loaderManager 获取系统图片的LoaderManager
    システムイメージのLoaderManagerを取得する
     */
    public void startScanImage(Context context, LoaderManager loaderManager);

}
