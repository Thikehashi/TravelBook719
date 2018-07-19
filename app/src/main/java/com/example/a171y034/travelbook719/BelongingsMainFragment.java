package com.example.a171y034.travelbook719;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 171y034 on 2018/07/19.
 */

public class BelongingsMainFragment extends Fragment {


    public static BelongingsMainFragment newInstance(){
        BelongingsMainFragment fragment = new BelongingsMainFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_belongings_main, container, false);

/*        // FloatingActionButtonをクリックしたらScheduleAddActivityに画面遷移
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), ListAddActivity.class);
                startActivity(in);
            }
        });
        */
        return v;
    }
}
