package com.example.a171y034.travelbook719.Album.UI;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a171y034.travelbook719.MainActivity;
import com.example.a171y034.travelbook719.R;

/**
 * Created by 171y034 on 2018/07/19.
 */

public class PhotoMainFragment extends Fragment  {

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

        // FloatingActionButtonをクリックしたら画面遷移
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent albumIntent = new Intent(String.valueOf(AlbumActivity.class));
                //startActivity(albumIntent);
                //Memoリストを追加
                ((MainActivity) getActivity()).showMemoForm(null);
            }
        });


        return v;
    }
}
