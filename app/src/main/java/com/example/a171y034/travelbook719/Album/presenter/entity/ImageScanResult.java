package com.example.a171y034.travelbook719.Album.presenter.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * イメージ結果をスキャン
 * <p/>
 * Created by Clock on 2016/3/21.
 */
public class ImageScanResult {

    /**
     * システム内の画像を含むすべてのファイル
     */
    private List<File> albumFolderList;
    /**
     * 画像フォルダのしたに画像
     */
    private Map<String, ArrayList<File>> albumImageListMap;

    /**
     * 携帯電話の画像、すべてのディレクトリを取得
     *
     * @return
     */
    public List<File> getAlbumFolderList() {
        return albumFolderList;
    }

    public void setAlbumFolderList(List<File> albumFolderList) {
        this.albumFolderList = albumFolderList;
    }

    /**
     * 携帯電話のすべての画像ディレクトリに含まれる画像を取得
     *
     * @return 一个Map，key是图片目录路径，value是对应目录下包含的所有图片文件
     */
    public Map<String, ArrayList<File>> getAlbumImageListMap() {
        return albumImageListMap;
    }

    public void setAlbumImageListMap(Map<String, ArrayList<File>> albumImageListMap) {
        this.albumImageListMap = albumImageListMap;
    }

}
