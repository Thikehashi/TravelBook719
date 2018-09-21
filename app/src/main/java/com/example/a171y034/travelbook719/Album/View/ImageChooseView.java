package com.example.a171y034.travelbook719.Album.View;

import com.example.a171y034.travelbook719.Album.Entity.ImageInfo;

/**
 * 画像セレクタViewレイヤインターフェイス
 * <p/>
 * Created by Clock on 2016/3/21.
 */
public interface ImageChooseView {

    /**
     * ピクチャカウンタをリフレッシュ
     *
     * @param imageInfo 操作のためのファイル情報
     */
    public void refreshSelectedCounter(ImageInfo imageInfo);

}
