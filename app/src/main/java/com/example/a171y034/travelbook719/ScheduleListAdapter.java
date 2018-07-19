package com.example.a171y034.travelbook719;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 171y034 on 2018/07/19.
 */

public class ScheduleListAdapter extends Fragment {

    /** フィールド */
    private LayoutInflater mInflator;

    public static ScheduleListAdapter newInstance() {
        ScheduleListAdapter fragment = new ScheduleListAdapter();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule_add, container, false);
    }

    // チェックボタンを表示させる
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_sub, menu);
        menu.findItem(R.id.actionok).setVisible(true);
    }

    // チェックボタンを押したときの処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionok) {
            Toast.makeText(getActivity(), "追加しました", Toast.LENGTH_SHORT).show();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            //ft.addToBackStack(null);
            ScheduleMainFragment fragment = new ScheduleMainFragment();
            ft.replace(R.id.content, fragment);
            ft.commit();
        }
        return true;
    }

/*    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.item_list_row, parent, false);
            holder = new ViewHolder();
            holder.tvColorLabel = (TextView) convertView.findViewById(R.id.color_label);
            holder.tvValue = (TextView) convertView.findViewById(R.id.value);
            holder.tvCreatedTime = (TextView) convertView.findViewById(R.id.created_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //値をセット
        String value = getItem(position).getValue();
        if (!TextUtils.isEmpty(value)) {
            holder.tvValue.setText(value);
        }

        //カラーラベルをセット
        int color = getItem(position).getColorLabel();
        holder.tvColorLabel.setBackgroundResource(getColorLabelResource(color));
        if (!TextUtils.isEmpty(value)) {
            holder.tvColorLabel.setText(value.substring(0, 1));
        }

        //日付をセット
        String createdtime = getCreatedTime(getItem(position).getCreatedTime());
        if (!TextUtils.isEmpty(createdtime)) {
            holder.tvCreatedTime.setText(createdtime);
        }
    }
*/
    /**
     * 現在年月をDate型返却.
     */
    public static String getCreatedTime(long createdTime) {
        Date date = new Date(createdTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH:mm");
        return sdf.format(date);
    }

    /**
     * カラーラベルのdrawableリソースIDを返却.
     *
     * @param color : カラー
     */
    private int getColorLabelResource(int color) {
        int resId = R.drawable.bg_colorlabel_grey;
        switch (color) {
            case Add.ColorLabel.PINK:
                resId = R.drawable.bg_colorlabel_pink;
                break;
            case Add.ColorLabel.INDIGO:
                resId = R.drawable.bg_colorlabel_indigo;
                break;
            case Add.ColorLabel.GREEN:
                resId = R.drawable.bg_colorlabel_green;
                break;
            case Add.ColorLabel.AMBER:
                resId = R.drawable.bg_colorlabel_amber;
                break;
        }
        return resId;
    }

    private class ViewHolder {

        TextView tvColorLabel;

        TextView tvCreatedTime;

        TextView tvValue;
    }
}
