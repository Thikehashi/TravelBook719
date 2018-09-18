package com.example.a171y034.travelbook719.Schedule.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a171y034.travelbook719.Schedule.PickerDialog.DatePickerDialogFragment;
import com.example.a171y034.travelbook719.R;
import com.example.a171y034.travelbook719.Schedule.Add.AddSchedule;
import com.example.a171y034.travelbook719.Schedule.PickerDialog.TimePickerDialogFragment;
import com.example.a171y034.travelbook719.Schedule.PickerDialog.TimePickerDialogFragment2;

import static com.example.a171y034.travelbook719.R.id.editendTime;
import static com.example.a171y034.travelbook719.R.id.editstartTime;

/**
 * Created by 171y034 on 2018/07/20.
 */

public class ScheduleFormFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = ScheduleFormFragment.class.getSimpleName();

    public static final String ARGS_CATEGORY = "key-category";

    public static final String ARGS_TIME = "key-time";

    public static final String ARGS_ENDTIME = "key-endtime";

    public static final String ARGS_DATE = "key-date";

    public static final String ARGS_VALUE = "key-value";

    public static final String ARGS_CREATEDTIME = "key-createdtime";

    private int mCategoryIcon = AddSchedule.Category.NONE;

    private long mCreatedTime = 0;

    private EditText mEtInput;
    private EditText mDateInput;
    private EditText mStartTime;
    private EditText mEndTime;

    private boolean mIsTextEdited = false;

    public static ScheduleFormFragment newInstance() {
        return new ScheduleFormFragment();
    }

    public static ScheduleFormFragment newInstance(int category, String time, String endTime, String date, String value, long createdTime) {
        ScheduleFormFragment fragment = new ScheduleFormFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_CATEGORY, category);
        args.putString(ARGS_TIME, time);
        args.putString(ARGS_ENDTIME, endTime);
        args.putString(ARGS_DATE, date);
        args.putString(ARGS_VALUE, value);
        args.putLong(ARGS_CREATEDTIME, createdTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // MenuItemの追加を許可
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_schedule_add, container, false);

        //カテゴリアイコンのインスタンスを取得
        rootView.findViewById(R.id.color_white).setOnClickListener((View.OnClickListener) this);
        rootView.findViewById(R.id.category_tourism).setOnClickListener((View.OnClickListener) this);
        rootView.findViewById(R.id.category_move).setOnClickListener((View.OnClickListener) this);
        rootView.findViewById(R.id.category_lunch).setOnClickListener((View.OnClickListener) this);
        rootView.findViewById(R.id.category_shopping).setOnClickListener((View.OnClickListener) this);
        rootView.findViewById(R.id.category_dormitory).setOnClickListener((View.OnClickListener) this);
        rootView.findViewById(R.id.category_experience).setOnClickListener((View.OnClickListener)this);

        //入力フォームのインスタンスを取得
        mEtInput = (EditText) rootView.findViewById(R.id.input);
        mEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //テキストの中身が変更されたら編集したと判定
                mIsTextEdited = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 入力フォーム（日付）のインスタンスを取得
        mDateInput = (EditText) rootView.findViewById(R.id.editDate);
        mDateInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //テキストの中身が変更されたら編集したと判定
                mIsTextEdited = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 入力フォーム(開始予定時刻)のインスタンスを取得
        mStartTime = (EditText) rootView.findViewById(R.id.editstartTime);
        mStartTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // テキストの中身が変更されたら編集したと判定
                mIsTextEdited = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 入力フォーム(終了予定時刻)のインスタンスを取得
        mEndTime = (EditText) rootView.findViewById(R.id.editendTime);
        mEndTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // テキストの中身が変更されたら編集したと判定
                mIsTextEdited = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //編集データを受け取っていたらセット
        Bundle args = getArguments();
        if (args != null) {
            //カテゴリアイコンをセット
            mCategoryIcon = args.getInt(ARGS_CATEGORY, AddSchedule.Category.NONE);

            //値をセット
            String value = args.getString(ARGS_VALUE);
            mEtInput.setText(value);

            //作成時間をセット
            mCreatedTime = args.getLong(ARGS_CREATEDTIME, 0);

            //開始予定時刻をセット
            String time = args.getString(ARGS_TIME);
            mStartTime.setText(time);

            // 終了予定時刻をセット
            String endTime = args.getString(ARGS_ENDTIME);
            mEndTime.setText(endTime);

            //日付をセット
            String date = args.getString(ARGS_DATE);
            mDateInput.setText(date);
        }

        return rootView;

    }


    // 日付入力
    //  時刻入力
    @Override
    public void onStart() {
        super.onStart();

        // 日付テキスト
        EditText editDate = (EditText) getActivity().findViewById(R.id.editDate);
        // 時刻テキスト
        EditText editStartTime = (EditText) getActivity().findViewById(editstartTime);
        EditText editEndTime = (EditText) getActivity().findViewById(editendTime);
        // キーボードを非表示
        editDate.setKeyListener(null);
        editStartTime.setKeyListener(null);
        editEndTime.setKeyListener(null);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment dateDialog = new DatePickerDialogFragment();
                dateDialog.show(getFragmentManager(), "dialog");
            }
        });

        editStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timeDialog = new TimePickerDialogFragment();
                timeDialog.show(getFragmentManager(), "dialog");
            }
        });

        editEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timeDialog = new TimePickerDialogFragment2();
                timeDialog.show(getFragmentManager(), "dialog");
            }
        });
    }

    // チェックボタンを表示させる
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem menuItem = menu.findItem(R.id.actionok);
        if (menuItem == null) {
            inflater.inflate(R.menu.menu_sub, menu);
            menu.findItem(R.id.actionok).setVisible(true);
/*            mMenuAdd = menu.add(Menu.NONE, MENU_ADD, Menu.NONE, "");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mMenuAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            }
*/
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    // チェックボタンを押したときの処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionok) {
            //Scheduleリストを追加
            String value = mEtInput.getText().toString();
            String time = mStartTime.getText().toString();
            String endTime = mEndTime.getText().toString();
            String date = mDateInput.getText().toString();
            if (!TextUtils.isEmpty(value) && mIsTextEdited) {
                Intent resultData = new Intent();
                resultData.putExtra(ARGS_CATEGORY, mCategoryIcon);
                resultData.putExtra(ARGS_TIME, time);
                resultData.putExtra(ARGS_ENDTIME, endTime);
                resultData.putExtra(ARGS_VALUE, value);
                resultData.putExtra(ARGS_DATE, date);
                Toast.makeText(getActivity(), "追加しました", Toast.LENGTH_SHORT).show();
                if (mCreatedTime == 0) {
                    //作成時間がない場合は新規データとして作成時間を生成
                    resultData.putExtra(ARGS_CREATEDTIME, System.currentTimeMillis());
                } else {
                    //作成時間がある場合は既存のデータを更新
                    resultData.putExtra(ARGS_CREATEDTIME, mCreatedTime);
                }
                //Broadcastを送信
                resultData.setAction(ScheduleMainFragment.ACTION_CREATE_SCHEDULE);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(resultData);

                // 1つ前のFragmentに戻る
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ScheduleMainFragment fragment = new ScheduleMainFragment();
                ft.replace(R.id.content, fragment);
                ft.commit();

            } else {
                Toast.makeText(getActivity(), "入力してください", Toast.LENGTH_SHORT).show();
                mEtInput.setError("タイトルを入力してください");
                mDateInput.setError("日付を入力してください");
                mStartTime.setError("開始予定時刻を入力してください");
            }
            //ソフトウェアキーボードを閉じる
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(mEtInput.getWindowToken(), 0);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.color_white) {
            mCategoryIcon = AddSchedule.Category.NONE;
        } else if (viewId == R.id.category_tourism) {
            mCategoryIcon = AddSchedule.Category.TOURISM;
        } else if (viewId == R.id.category_move) {
            mCategoryIcon = AddSchedule.Category.MOVE;
        } else if (viewId == R.id.category_lunch) {
            mCategoryIcon = AddSchedule.Category.LUNCH;
        } else if (viewId == R.id.category_shopping) {
            mCategoryIcon = AddSchedule.Category.SHOPPING;
        } else if (viewId == R.id.category_dormitory){
            mCategoryIcon = AddSchedule.Category.DORMITORY;
        } else if (viewId == R.id.category_experience){
            mCategoryIcon = AddSchedule.Category.EXPERIENCE;
        }
    }
}
