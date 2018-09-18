package com.example.a171y034.travelbook719.Belongings.CheckBox;

import android.content.Context;

/**
 * Created by 171y034 on 2018/07/27.
 */

public class CompatUtils {
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
