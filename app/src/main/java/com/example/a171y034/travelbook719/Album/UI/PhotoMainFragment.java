package com.example.a171y034.travelbook719.Album.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a171y034.travelbook719.Album.View.AlbumView;
import com.example.a171y034.travelbook719.Album.View.ImageChooseView;
import com.example.a171y034.travelbook719.R;

/**
 * Created by 171y034 on 2018/07/19.
 */

public class PhotoMainFragment extends Fragment implements View.OnClickListener, ImageChooseView, AlbumView {

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
        return inflater.inflate(R.layout.fragment_photo_main, container, false);
    }
}
