package com.example.a171y034.travelbook719.Schedule.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.a171y034.travelbook719.MainActivity;
import com.example.a171y034.travelbook719.R;
import com.example.a171y034.travelbook719.Schedule.Adapter.ScheduleListAdapter;
import com.example.a171y034.travelbook719.Schedule.Add.AddSchedule;

import java.util.List;

/**
 * Created by 171y034 on 2018/07/19.
 */

public class ScheduleMainFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String TAG = ScheduleMainFragment.class.getSimpleName();

    public static final String ACTION_CREATE_SCHEDULE = "action-create_todo";

    private static final int MENU_ID_DELETE = 1;

    private ScheduleListAdapter mAdapter;

    private List<AddSchedule> mAddList;

    public static ScheduleMainFragment newInstance() {
        ScheduleMainFragment fragment = new ScheduleMainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_schedule_main, container, false);

        //データを作成してAdapterにセット
        mAddList = ((MainActivity) getActivity()).getAddList();
        mAdapter = new ScheduleListAdapter(getActivity(), mAddList);

        //ListViewを初期化
        ListView listView = (ListView) v.findViewById(R.id.list);
        listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        listView.setAdapter((ListAdapter) mAdapter);

        //ListViewにコンテキストメニューを設定
        registerForContextMenu(listView);

        // FloatingActionButtonをクリックしたら画面遷移
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Scheduleリストを追加
                ((MainActivity) getActivity()).showScheduleForm(null);
            }
        });

        //BroadcastReceiverを登録
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mAddTodoReceiver, new IntentFilter(ACTION_CREATE_SCHEDULE));
        return v;
    }

    @Override
    public void onDestroy() {
        //BroadcastReceiverを解除
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mAddTodoReceiver);
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //クリックしたアイテムを表示
        ((MainActivity) getActivity()).showScheduleForm(mAdapter.getItem(position));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        //ListViewのコンテキストメニューを作成
        if(view.getId() == R.id.list){
            menu.setHeaderTitle("選択アイテム");
            menu.add(0, MENU_ID_DELETE, 0, "削除");
        }
    }

    //コンテキストメニュークリック時のリスナー
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        int itemId = item.getItemId();
        if (itemId == MENU_ID_DELETE) {
            //アイテムを削除
            mAddList.remove(info.position);
            mAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Scheduleリストの作成・変更を検知するBroadcastReceiver.
     */
    BroadcastReceiver mAddTodoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //スケジュールデータを作成
            int category = intent.getIntExtra(ScheduleFormFragment.ARGS_CATEGORY, AddSchedule.Category.TOURISM);
            String time = intent.getStringExtra(ScheduleFormFragment.ARGS_TIME);
            String endTime = intent.getStringExtra(ScheduleFormFragment.ARGS_ENDTIME);
            String value = intent.getStringExtra(ScheduleFormFragment.ARGS_VALUE);
            long createdTime = intent.getLongExtra(ScheduleFormFragment.ARGS_CREATEDTIME, 0);
            String date = intent.getStringExtra(ScheduleFormFragment.ARGS_DATE);
            AddSchedule newItem = new AddSchedule(category, time, endTime, value, createdTime, date);

            //作成時間を既に存在するデータか確認
            int updateIndex = -1;
            for (int i = 0; i < mAdapter.getCount(); i++) {
                AddSchedule item = mAdapter.getItem(i);
                if (item.getCreatedTime() == newItem.getCreatedTime()) {
                    updateIndex = i;
                }
            }
            if (updateIndex == -1) {
                //既存データがなければ新規として追加
                mAddList.add(newItem);
            } else {
                //既存データがあれば上書き
                mAddList.remove(updateIndex);
                mAddList.add(updateIndex, newItem);
            }

            //スケジュールリストを更新
            mAdapter.notifyDataSetChanged();

        }
    };
}
