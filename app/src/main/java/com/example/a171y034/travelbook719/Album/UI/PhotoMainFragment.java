package com.example.a171y034.travelbook719.Album.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.a171y034.travelbook719.Album.ImageLoader.ImageLoaderWrapper;
import com.example.a171y034.travelbook719.Album.UI.Activity.AlbumActivity;
import com.example.a171y034.travelbook719.R;

import java.io.File;
import java.util.List;

/**
 * Created by 171y034 on 2018/07/19.
 */

public class PhotoMainFragment extends Fragment  {

    public static final String TAG = AlbumActivity.class.getSimpleName();

    private GridView mSelectedImagegridView;

    private List<File> mSelectedImageList;

    private ImageLoaderWrapper mImageLoaderWrapper;


    public static PhotoMainFragment newInstance(){
        PhotoMainFragment fragment = new PhotoMainFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_photo_main, container, false);
        /*
        mSelectedImageList = (List<File>) getActivity().getIntent().getSerializableExtra(EXTRA_SELECTED_IMAGE_LIST);
        mSelectedImagegridView = (GridView) v.findViewById(R.id.main_image_selected);
        */
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
        return v;
    }
    /*
    public void callFromOut(){
        mSelectedImagegridView.setAdapter(new SelectedImageGridAdapter());
    }
    */
}
