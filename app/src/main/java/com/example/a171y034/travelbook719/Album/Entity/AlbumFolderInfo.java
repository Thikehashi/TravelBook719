package com.example.a171y034.travelbook719.Album.Entity;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 *ディレクトリ情報
 * <p/>
 */
public class AlbumFolderInfo implements Serializable {


    /**
     * ディレクトリ名
     */
    private String folderName;
    /**
     *すべての画像情報が含まれています
     */
    private List<ImageInfo> imageInfoList;
    /**
     * 最初の写真
     */
    private File frontCover;

    public File getFrontCover() {
        return frontCover;
    }

    public void setFrontCover(File frontCover) {
        this.frontCover = frontCover;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public List<ImageInfo> getImageInfoList() {
        return imageInfoList;
    }

    public void setImageInfoList(List<ImageInfo> imageInfoList) {
        this.imageInfoList = imageInfoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlbumFolderInfo that = (AlbumFolderInfo) o;

        if (getFolderName() != null ? !getFolderName().equals(that.getFolderName()) : that.getFolderName() != null)
            return false;
        if (getImageInfoList() != null ? !getImageInfoList().equals(that.getImageInfoList()) : that.getImageInfoList() != null)
            return false;
        return !(getFrontCover() != null ? !getFrontCover().equals(that.getFrontCover()) : that.getFrontCover() != null);

    }

    @Override
    public int hashCode() {
        int result = getFolderName() != null ? getFolderName().hashCode() : 0;
        result = 31 * result + (getImageInfoList() != null ? getImageInfoList().hashCode() : 0);
        result = 31 * result + (getFrontCover() != null ? getFrontCover().hashCode() : 0);
        return result;
    }
}
