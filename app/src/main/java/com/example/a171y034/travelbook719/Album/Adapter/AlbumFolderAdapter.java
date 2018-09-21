package com.example.a171y034.travelbook719.Album.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a171y034.travelbook719.Album.Entity.AlbumFolderInfo;
import com.example.a171y034.travelbook719.Album.Entity.ImageInfo;
import com.example.a171y034.travelbook719.Album.ImageLoader.ImageLoaderWrapper;
import com.example.a171y034.travelbook719.R;

import java.io.File;
import java.util.List;

/**
 * アルバムディレクトリアダプタ
 * <p/>
 */
public class AlbumFolderAdapter extends BaseAdapter {

    private List<AlbumFolderInfo> mAlbumFolderInfoList;
    private ImageLoaderWrapper mImageLoaderWrapper;

    public AlbumFolderAdapter(List<AlbumFolderInfo> albumFolderInfoList, ImageLoaderWrapper imageLoaderWrapper) {
        this.mAlbumFolderInfoList = albumFolderInfoList;
        this.mImageLoaderWrapper = imageLoaderWrapper;
    }

    @Override
    public int getCount() {
        if (mAlbumFolderInfoList == null) {
            return 0;
        }
        return mAlbumFolderInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAlbumFolderInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.album_directory_item, null);
            holder = new ViewHolder();
            holder.ivAlbumCover = (ImageView) convertView.findViewById(R.id.iv_album_cover);
            holder.tvDirectoryName = (TextView) convertView.findViewById(R.id.tv_directory_name);
            holder.tvChildCount = (TextView) convertView.findViewById(R.id.tv_child_count);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        AlbumFolderInfo albumFolderInfo = mAlbumFolderInfoList.get(position);


        File frontCover = albumFolderInfo.getFrontCover();
        ImageLoaderWrapper.DisplayOption displayOption = new ImageLoaderWrapper.DisplayOption();
        displayOption.loadingResId = R.mipmap.img_default;
        displayOption.loadErrorResId = R.mipmap.img_error;
        mImageLoaderWrapper.displayImage(holder.ivAlbumCover, frontCover, displayOption);

        String folderName = albumFolderInfo.getFolderName();
        holder.tvDirectoryName.setText(folderName);

        List<ImageInfo> imageInfoList = albumFolderInfo.getImageInfoList();
        holder.tvChildCount.setText(imageInfoList.size() + "");

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivAlbumCover;
        TextView tvDirectoryName;
        TextView tvChildCount;
    }
}
