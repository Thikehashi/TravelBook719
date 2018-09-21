package com.example.a171y034.travelbook719.Album.Model;

import android.content.Context;
import android.support.v4.app.LoaderManager;

import com.example.a171y034.travelbook719.Album.presenter.entity.ImageScanResult;
import com.example.a171y034.travelbook719.Album.View.entity.AlbumViewData;

/**
 * イメージスキャンモデルレイヤーインターフェイス
 * <p/>
 */
public interface ImageScannerModel {

    /**
     * すべてのイメージの情報のリストを取得します（イメージディレクトリの絶対パスはマップのキーで、値はイメージディレクトリ下のすべてのイメージファイルの情報です）
     *
     * @param context
     * @param loaderManager
     * @param onScanImageFinish イメージをスキャンした後に結果を返すコールバックインターフェイス
     * @return
     */
    public void startScanImage(Context context, LoaderManager loaderManager, OnScanImageFinish onScanImageFinish);

    /**
     * アルバム情報をアーカイブする
     *
     * @param imageScanResult
     * @return 整理されたアルバムディレクトリ情報
     */
    public AlbumViewData archiveAlbumInfo(Context context, ImageScanResult imageScanResult);

    /**
     * イメージスキャン結果コールバックインターフェイス
     */
    public static interface OnScanImageFinish {

        /**
         * スキャン終了時にこの関数を実行する
         *
         * @param imageScanResult スキャン結果を返し、イメージがない場合はnullを返します。
         */
        public void onFinish(ImageScanResult imageScanResult);
    }
}
