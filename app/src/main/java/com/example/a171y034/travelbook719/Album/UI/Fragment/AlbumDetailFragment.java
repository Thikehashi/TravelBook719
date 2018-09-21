package com.example.a171y034.travelbook719.Album.UI.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.a171y034.travelbook719.Album.View.ImageChooseView;
import com.example.a171y034.travelbook719.Album.Adapter.AlbumGridAdapter;
import com.example.a171y034.travelbook719.Album.Entity.ImageInfo;
import com.example.a171y034.travelbook719.Album.ImageLoader.ImageLoaderFactory;
import com.example.a171y034.travelbook719.Album.ImageLoader.ImageLoaderWrapper;
import com.example.a171y034.travelbook719.Album.UI.Activity.ImagePreviewActivity;
import com.example.a171y034.travelbook719.Album.UI.Fragment.base.BaseFragment;
import com.example.a171y034.travelbook719.R;

import java.io.Serializable;
import java.util.List;

/**
 * アルバム詳細ページ
 *
 * @author Clock
 */
public class AlbumDetailFragment extends BaseFragment implements AlbumGridAdapter.OnClickPreviewImageListener {

    public static final int PREVIEW_REQUEST_CODE = 1000;

    private static final String ARG_PARAM1 = "param1";

    /**
     * 画像選択View
     * レイヤインタラクションインターフェイス
     */
    private ImageChooseView mImageChooseView;
    /**
     * アルバム情報リスト
     */
    private List<ImageInfo> mImageInfoList;
    /**
     * アルバムビューコントロール
     */
    private GridView mAlbumGridView;
    private BaseAdapter mAlbumGridViewAdapter;

    /**
     * @param imageInfoList アルバムリスト
     * @return
     */
    public static AlbumDetailFragment newInstance(List<ImageInfo> imageInfoList) {
        AlbumDetailFragment fragment = new AlbumDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) imageInfoList);
        fragment.setArguments(args);
        return fragment;
    }

    public AlbumDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImageInfoList = (List<ImageInfo>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_album_detail, container, false);
        mAlbumGridView = (GridView) rootView.findViewById(R.id.gv_album);
        ImageLoaderWrapper loaderWrapper = ImageLoaderFactory.getLoader();
        mAlbumGridViewAdapter = new AlbumGridAdapter(mImageInfoList, loaderWrapper, mImageChooseView, this);
        mAlbumGridView.setAdapter(mAlbumGridViewAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ImageChooseView) {
            mImageChooseView = (ImageChooseView) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mImageChooseView = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PREVIEW_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            List<ImageInfo> newSelectedImageList = (List<ImageInfo>) data.getSerializableExtra(ImagePreviewActivity.EXTRA_NEW_IMAGE_LIST);
            refreshSelectedImage(newSelectedImageList);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 新しく選択した画像のデータの更新
     *
     * @param newSelectedImageList
     */
    private void refreshSelectedImage(List<ImageInfo> newSelectedImageList) {
        int imageSize = newSelectedImageList.size();
        for (int imagePos = 0; imagePos < imageSize; imagePos++) {
            ImageInfo srcImageInfo = newSelectedImageList.get(imagePos);
            ImageInfo destImageInfo = mImageInfoList.get(imagePos);
            destImageInfo.setIsSelected(srcImageInfo.isSelected());//選択した状態をトラバースする
            if (mImageChooseView != null) {
                mImageChooseView.refreshSelectedCounter(destImageInfo);
            }
        }
        mAlbumGridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickPreview(ImageInfo imageInfo) {
        Intent previewIntent = new Intent(getContext(), ImagePreviewActivity.class);
        previewIntent.putExtra(ImagePreviewActivity.EXTRA_IMAGE_INFO, imageInfo);
        previewIntent.putExtra(ImagePreviewActivity.EXTRA_IMAGE_INFO_LIST, (Serializable) mImageInfoList);
        startActivityForResult(previewIntent, PREVIEW_REQUEST_CODE);
    }

}
