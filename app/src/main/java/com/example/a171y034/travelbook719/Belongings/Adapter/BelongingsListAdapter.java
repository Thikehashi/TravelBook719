package com.example.a171y034.travelbook719.Belongings.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a171y034.travelbook719.Belongings.Add.AddBelongings;
import com.example.a171y034.travelbook719.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 171y034 on 2018/07/24.
 */

public class BelongingsListAdapter extends ArrayAdapter<AddBelongings> {

    /** フィールド */
    private LayoutInflater mInflator;

    public BelongingsListAdapter(Context context, List<AddBelongings> objects) {
        super(context, 0, objects);
        mInflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.item_list_row_belongings, parent, false);
            holder = new ViewHolder();
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


    private class ViewHolder {

        TextView tvCreatedTime;

        TextView tvValue;
    }
}
