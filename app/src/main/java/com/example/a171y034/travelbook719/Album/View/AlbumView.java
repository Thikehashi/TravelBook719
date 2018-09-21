package com.example.a171y034.travelbook719.Album.View;

import com.example.a171y034.travelbook719.Album.Entity.AlbumFolderInfo;
import com.example.a171y034.travelbook719.Album.View.entity.AlbumViewData;

/**
 * Created by Clock on 2016/3/19.
 */
public interface AlbumView {

    /**
     * アルバムデータ情報を更新
     *
     * @param albumData
     */
    public void refreshAlbumData(AlbumViewData albumData);

    /**
     * 画像ディレクトリを切り替え
     *
     * @param albumFolderInfo イメージディレクトリの情報を指定
     */
    public void switchAlbumFolder(AlbumFolderInfo albumFolderInfo);

}
