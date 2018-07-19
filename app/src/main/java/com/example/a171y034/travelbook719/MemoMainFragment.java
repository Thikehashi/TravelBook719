package com.example.a171y034.travelbook719;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 171y034 on 2018/07/19.
 */

public class MemoMainFragment extends Fragment {

    public static MemoMainFragment newInstance(){
        MemoMainFragment fragment = new MemoMainFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_memo_main, container, false);
    }
}
