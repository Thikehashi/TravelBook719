package com.example.a171y034.travelbook719.Album.presenter;

import android.content.Context;
import android.support.v4.app.LoaderManager;

/**
 * 画像スキャンプレゼンターレイヤー
 * <p/>
 */
public interface ImageScannerPresenter {

    /**
     * スキャンして画像フォルダのリストを取得する
     *
     * @param context
     * @param loaderManager システムイメージのLoaderManagerを取得する
     */
    public void startScanImage(Context context, LoaderManager loaderManager);

}
