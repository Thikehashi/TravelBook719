package com.example.a171y034.travelbook719.Album.View;

import com.example.a171y034.travelbook719.Album.Entity.ImageInfo;

/**
 * 图片选择器View层接口
 * <p/>
 * Created by Clock on 2016/3/21.
 */
public interface ImageChooseView {

    /**
     * 刷新图片的计数器
     *
     * @param imageInfo 进行操作的文件信息
     */
    public void refreshSelectedCounter(ImageInfo imageInfo);

}
