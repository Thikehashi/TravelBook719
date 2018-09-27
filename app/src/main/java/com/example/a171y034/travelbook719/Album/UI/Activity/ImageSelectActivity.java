package com.example.a171y034.travelbook719.Album.UI.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.clock.utils.common.RuleUtils;
import com.example.a171y034.travelbook719.Album.ImageLoader.ImageLoaderFactory;
import com.example.a171y034.travelbook719.Album.ImageLoader.ImageLoaderWrapper;
import com.example.a171y034.travelbook719.Album.UI.Activity.base.BaseActivity;
import com.example.a171y034.travelbook719.R;

import java.io.File;
import java.util.List;

/**
 * 選択した画像のインターフェイスを表示
 *
 * @author Clock
 */
public class ImageSelectActivity extends BaseActivity implements View.OnClickListener {

    public final static String EXTRA_SELECTED_IMAGE_LIST = "selectImage";

    private GridView mSelectedImageGridView;
    private List<File> mSelectedImageList;
    private ImageLoaderWrapper mImageLoaderWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);

        findViewById(R.id.iv_back).setOnClickListener(this);

        mImageLoaderWrapper = ImageLoaderFactory.getLoader();
        mSelectedImageList = (List<File>) getIntent().getSerializableExtra(EXTRA_SELECTED_IMAGE_LIST);
        mSelectedImageGridView = (GridView) findViewById(R.id.gv_image_selected);
        mSelectedImageGridView.setAdapter(new SelectedImageGridAdapter());

    }

    /**
     *  クリックイベント
     * @param v
     */
    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.iv_back) {
            // ←（バック）ボタン
            onBackPressed();
        }
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
