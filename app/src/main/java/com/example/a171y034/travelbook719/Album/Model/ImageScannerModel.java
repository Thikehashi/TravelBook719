package com.example.a171y034.travelbook719.Album.Model;

import android.content.Context;
import android.support.v4.app.LoaderManager;

import com.example.a171y034.travelbook719.Album.presenter.entity.ImageScanResult;
import com.example.a171y034.travelbook719.Album.View.entity.AlbumViewData;

/**
 * 图片扫描Model层接口
 イメージスキャンモデルレイヤーインターフェイス
 * <p/>
 * Created by Clock on 2016/3/19.
 */
public interface ImageScannerModel {

    /**
     * 获取所有图片的信息列表（图片目录的绝对路径作为map的key，value是该图片目录下的所有图片文件信息）
     すべてのイメージの情報のリストを取得します（イメージディレクトリの絶対パスはマップのキーで、値はイメージディレクトリ下のすべてのイメージファイルの情報です）
     *
     * @param context
     * @param loaderManager
     * @param onScanImageFinish 扫描图片结束返回结果的回调接口
    イメージをスキャンした後に結果を返すコールバックインターフェイス
     * @return
     */
    public void startScanImage(Context context, LoaderManager loaderManager, OnScanImageFinish onScanImageFinish);

    /**
     * 归档整理相册信息 アルバム情報をアーカイブする
     *
     * @param imageScanResult
     * @return 整理好的相册目录信息 整理されたアルバムディレクトリ情報
     */
    public AlbumViewData archiveAlbumInfo(Context context, ImageScanResult imageScanResult);

    /**
     * 图片扫描结果回调接口 イメージスキャン結果コールバックインターフェイス
     */
    public static interface OnScanImageFinish {

        /**
         * 扫描结束的时候执行此函数
         スキャン終了時にこの関数を実行する
         *
         * @param imageScanResult 返回扫描结果，不存在图片则返回null
        スキャン結果を返し、イメージがない場合はnullを返します。
         */
        public void onFinish(ImageScanResult imageScanResult);
    }
}
