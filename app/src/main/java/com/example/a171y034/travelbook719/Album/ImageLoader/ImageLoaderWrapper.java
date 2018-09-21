package com.example.a171y034.travelbook719.Album.ImageLoader;

import android.widget.ImageView;

import java.io.File;

/**
 * 画像読み込み機能のインターフェース
 * <p/>
 */
public interface ImageLoaderWrapper {

    /**
     *画像を表示する
     *
     * @param imageView ImageView 画像を表示する
     * @param imageFile 画像ファイル
     * @param option    パラメータ設定を表示する
     */
    public void displayImage(ImageView imageView, File imageFile, DisplayOption option);

    /**
     * 画像を表示する
     *
     * @param imageView ImageView 画像を表示する
     * @param imageUrl  URL 画像リソース
     * @param option    パラメータ設定を表示する
     */
    public void displayImage(ImageView imageView, String imageUrl, DisplayOption option);

    /**
     * 画像読み込みパラメータ
     */
    public static class DisplayOption {
        /**
         * id 読み込まれたリソース
         */
        public int loadingResId;
        /**
         * id 失敗したリソースを読み込む
         */
        public int loadErrorResId;
    }
}
