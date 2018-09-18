package com.example.a171y034.travelbook719.Album.ImageLoader;

import android.widget.ImageView;

import java.io.File;

/**
 * 图片加载功能接口 画像読み込み機能のインターフェース
 * <p/>
 * Created by Clock on 2016/1/18.
 */
public interface ImageLoaderWrapper {

    /**
     * 显示 图片 画像を表示する
     *
     * @param imageView 显示图片的ImageView 画像を表示する
     * @param imageFile 图片文件 画像ファイル
     * @param option    显示参数设置 パラメータ設定を表示する
     */
    public void displayImage(ImageView imageView, File imageFile, DisplayOption option);

    /**
     * 显示图片
     画像を表示する
     *
     * @param imageView 显示图片的ImageView 画像を表示する
     * @param imageUrl  图片资源的URL 画像リソース
     * @param option    显示参数设置 パラメータ設定を表示する
     */
    public void displayImage(ImageView imageView, String imageUrl, DisplayOption option);

    /**
     * 图片加载参数
     画像読み込みパラメータ
     */
    public static class DisplayOption {
        /**
         * 加载中的资源id 読み込まれたリソース
         */
        public int loadingResId;
        /**
         * 加载失败的资源id
         失敗したリソースを読み込む
         */
        public int loadErrorResId;
    }
}
