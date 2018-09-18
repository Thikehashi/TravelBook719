package com.example.a171y034.travelbook719.Schedule.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a171y034.travelbook719.R;
import com.example.a171y034.travelbook719.Schedule.Add.AddSchedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 171y034 on 2018/07/19.
 */

public class ScheduleListAdapter extends ArrayAdapter<AddSchedule> {

    /** フィールド */
    private LayoutInflater mInflator;

    public ScheduleListAdapter(Context context, List<AddSchedule> objects) {
        super(context, 0, objects);
        mInflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.item_list_schedule_row, parent, false);
            holder = new ViewHolder();
            holder.tvCategoryIcon = (TextView) convertView.findViewById(R.id.category_icon);
            holder.tvTime = (TextView) convertView.findViewById(R.id.StartTime);
            holder.tvEndTime = (TextView) convertView.findViewById(R.id.EndTime);
            holder.tvValue = (TextView) convertView.findViewById(R.id.value);
            holder.tvCreatedTime = (TextView) convertView.findViewById(R.id.created_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 開始予定時刻をセット
        String time = getItem(position).getStartTime();
        if(!TextUtils.isEmpty(time)){
            holder.tvTime.setText(time);
        }

        // 終了予定時刻をセット
        String endTime = getItem(position).getEndTime();
        if(!TextUtils.isEmpty(endTime)){
            holder.tvEndTime.setText(endTime);
        }

        //値をセット
        String value = getItem(position).getValue();
        if (!TextUtils.isEmpty(value)) {
            holder.tvValue.setText(value);
        }

        //カラーラベルをセット
        int category = getItem(position).getCategory();
        holder.tvCategoryIcon.setBackgroundResource(getCategoryIconResource(category));


        //日付をセット
        String createdtime = getCreatedTime(getItem(position).getCreatedTime());
        if (!TextUtils.isEmpty(createdtime)) {
            holder.tvCreatedTime.setText(createdtime);
        }
        return convertView;
    }

    /**
     * 現在年月をDate型返却.
     */
    public static String getCreatedTime(long createdTime) {
        Date date = new Date(createdTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH:mm");
        return sdf.format(date);
    }

    /**
     * カテゴリアイコンdrawableリソースIDを返却.
     *
     * @param  category : カテゴリアイコン
     */
    private int getCategoryIconResource(int category) {
        int resId = R.drawable.bg_colorlabel_grey;
        switch (category) {
            case AddSchedule.Category.NONE:
                resId = R.drawable.bg_colorlabel_white;
                break;
            case AddSchedule.Category.TOURISM:
                resId = R.drawable.category_icon_tourism;
                break;
            case AddSchedule.Category.MOVE:
                resId = R.drawable.category_icon_move;
                break;
            case AddSchedule.Category.LUNCH:
                resId = R.drawable.category_icon_lunch;
                break;
            case AddSchedule.Category.SHOPPING:
                resId = R.drawable.category_icon_shopping;
                break;
            case AddSchedule.Category.DORMITORY:
                resId = R.drawable.category_icon_dormitory;
                break;
            case AddSchedule.Category.EXPERIENCE:
                resId  = R.drawable.category_icon_experience;
                break;
        }
        return resId;
    }

    private class ViewHolder {

        TextView tvCategoryIcon;

        TextView tvTime;

        TextView tvEndTime;

        TextView tvCreatedTime;

        TextView tvValue;


    }
}
