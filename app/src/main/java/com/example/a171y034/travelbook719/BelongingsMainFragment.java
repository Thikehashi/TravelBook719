package com.example.a171y034.travelbook719;

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

import java.util.List;

/**
 * Created by 171y034 on 2018/07/19.
 */

public class BelongingsMainFragment  extends Fragment implements AdapterView.OnItemClickListener {

    public static final String TAG = BelongingsMainFragment.class.getSimpleName();

    public static final String ACTION_CREATE_BELONGINGS = "action-create_belongings";

    private static final int MENU_ID_DELETE = 1;

    private BelongingsListAdapter mAdapter;

    private List<AddBelongings> mAddList;

    public static BelongingsMainFragment newInstance() {
        BelongingsMainFragment fragment = new BelongingsMainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_belongings_main, container, false);

        //データを作成してAdapterにセット
        mAddList = ((MainActivity) getActivity()).getAddListBelongings();
        mAdapter = new BelongingsListAdapter(getActivity(), mAddList);

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
                //Memoリストを追加
                ((MainActivity) getActivity()).showBelongingsForm(null);
            }
        });
        //BroadcastReceiverを登録
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mAddTodoReceiver, new IntentFilter(ACTION_CREATE_BELONGINGS));
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
        ((MainActivity) getActivity()).showBelongingsForm(mAdapter.getItem(position));
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
            //Belongingsデータを作成
//            int color = intent.getIntExtra(BelongingsFormFragment.ARGS_COLORLABEL, AddBelongings.ColorLabel.NONE);
            String value = intent.getStringExtra(BelongingsFormFragment.ARGS_VALUE);
            long createdTime = intent.getLongExtra(BelongingsFormFragment.ARGS_CREATEDTIME, 0);
            AddBelongings newItem = new AddBelongings(value, createdTime);

            //作成時間を既に存在するデータか確認
            int updateIndex = -1;
            for (int i = 0; i < mAdapter.getCount(); i++) {
                AddBelongings item = mAdapter.getItem(i);
                if (item.getCreatedTime() == newItem.getCreatedTime()) {
                    updateIndex = i;
                }
            }
            if (updateIndex == -1) {
                //既存データがなければ新規Todoとして追加
                mAddList.add(newItem);
            } else {
                //既存データがあれば上書き
                mAddList.remove(updateIndex);
                mAddList.add(updateIndex, newItem);
            }

            //TODOリストを更新
            mAdapter.notifyDataSetChanged();

        }
    };
}
