package com.example.a171y034.travelbook719.Album.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.clock.utils.common.RuleUtils;
import com.example.a171y034.travelbook719.Album.ImageLoader.ImageLoaderWrapper;
import com.example.a171y034.travelbook719.Album.UI.Activity.AlbumActivity;
import com.example.a171y034.travelbook719.R;

import java.io.File;
import java.util.List;

/**
 * Created by 171y034 on 2018/07/19.
 */

public class PhotoMainFragment extends Fragment {

    public static final String TAG = PhotoMainFragment.class.getSimpleName();

    public final static String EXTRA_SELECTED_IMAGE_LIST = "selectImage";

    private GridView mSelectedImageGridView;
    private List<File> mSelectedImageList;
    private ImageLoaderWrapper mImageLoaderWrapper;

    public static PhotoMainFragment newInstance(){
        PhotoMainFragment fragment = new PhotoMainFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Intent intent = getActivity().getIntent();
        //mSelectedImageList = (List<File>) intent.getSerializableExtra(EXTRA_SELECTED_IMAGE_LIST);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_photo_main, container, false);

        // FloatingActionButtonをクリックしたら画面遷移
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AlbumActivityをstartさせる
                Intent albumIntent;
                albumIntent = new Intent(getActivity(),AlbumActivity.class);
                startActivity(albumIntent);
            }
        });
        /*
        // AlbumActivityから値の受け取り
        Bundle bundle = this.getArguments();
        mImageLoaderWrapper = ImageLoaderFactory.getLoader();
        // 選択した写真を表示させるためのmSelectedImageGridView
        mSelectedImageList = (List<File>) bundle.getParcelableArrayList(EXTRA_SELECTED_IMAGE_LIST);
        mSelectedImageGridView = (GridView) v.findViewById(R.id.main_image_selected);
        mSelectedImageGridView.setAdapter(new SelectedImageGridAdapter());
        */
        return v;
    }

    /*********************************************************選択した写真をまとめたものを表示*************************************************************************/
    public class SelectedImageGridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mSelectedImageList == null) {
                return 0;
            }
            return mSelectedImageList.size();
        }

        @Override
        public Object getItem(int position) {
            return mSelectedImageList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SelectedImageHolder holder = null;
            if (convertView == null) {
                holder = new SelectedImageHolder();
                convertView = View.inflate(parent.getContext(), R.layout.selected_image_item, null);

                int gridItemSpacing = (int) RuleUtils.convertDp2Px(parent.getContext(), 2);
                int gridEdgeLength = (RuleUtils.getScreenWidth(parent.getContext()) - gridItemSpacing * 2) / 3;

                AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(gridEdgeLength, gridEdgeLength);
                convertView.setLayoutParams(layoutParams);
                holder.selectedImageView = (ImageView) convertView.findViewById(R.id.iv_selected_item);
                convertView.setTag(holder);

            } else {
                holder = (SelectedImageHolder) convertView.getTag();

            }

            ImageLoaderWrapper.DisplayOption displayOption = new ImageLoaderWrapper.DisplayOption();
            displayOption.loadingResId = R.mipmap.img_default;
            displayOption.loadErrorResId = R.mipmap.img_error;
            mImageLoaderWrapper.displayImage(holder.selectedImageView, mSelectedImageList.get(position), displayOption);

            return convertView;
        }
    }

    public static class SelectedImageHolder {
        ImageView selectedImageView;
    }
/**********************************************************************************************************************************/
}
