package com.example.a171y034.travelbook719.Schedule.PickerDialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import com.example.a171y034.travelbook719.R;

import java.util.Locale;

/**
 * Created by 171y034 on 2018/07/30.
 */

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        return datePickerDialog;
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int day){
        // 日付が選択された時の処理
        // 日付テキスト
        EditText editText = (EditText) getActivity().findViewById(R.id.editDate);
        String str = String.format(Locale.JAPAN, "%d年%d月%d日",year, month+1, day);
        editText.setText( str );
    }
}
