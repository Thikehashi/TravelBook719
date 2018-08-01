package com.example.a171y034.travelbook719;

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

/**
 * Created by 171y034 on 2018/07/20.
 */

public class ScheduleFormFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = ScheduleFormFragment.class.getSimpleName();

    private static final int MENU_ADD = 1;

    public static final String ARGS_COLORLABEL = "key-colorlabel";

    public static final String ARGS_VALUE = "key-value";

    public static final String ARGS_CREATEDTIME = "key-createdtime";

    private int mColorLabel = AddSchedule.ColorLabel.NONE;

    private long mCreatedTime = 0;

    private EditText mEtInput;
    private EditText mTimeInput;

    private boolean mIsTextEdited = false;

    private MenuItem mMenuAdd;

    public static ScheduleFormFragment newInstance() {
        return new ScheduleFormFragment();
    }

    public static ScheduleFormFragment newInstance(int colorLabel, String value, long createdTime) {
        ScheduleFormFragment fragment = new ScheduleFormFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_COLORLABEL, colorLabel);
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

        //カラーラベルのインスタンスを取得
        rootView.findViewById(R.id.color_none).setOnClickListener((View.OnClickListener) this);
        rootView.findViewById(R.id.color_amber).setOnClickListener((View.OnClickListener) this);
        rootView.findViewById(R.id.color_green).setOnClickListener((View.OnClickListener) this);
        rootView.findViewById(R.id.color_indigo).setOnClickListener((View.OnClickListener) this);
        rootView.findViewById(R.id.color_pink).setOnClickListener((View.OnClickListener) this);

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
        mTimeInput = (EditText) rootView.findViewById(R.id.editDate);
        mTimeInput.addTextChangedListener(new TextWatcher() {
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

        //編集データを受け取っていたらセット
        Bundle args = getArguments();
        if (args != null) {
            //カラーラベルをセット
            mColorLabel = args.getInt(ARGS_COLORLABEL, AddSchedule.ColorLabel.NONE);
            mEtInput.setTextColor(getColorResource(mColorLabel));

            //値をセット
            String value = args.getString(ARGS_VALUE);
            mEtInput.setText(value);

            //作成時間をセット
            mCreatedTime = args.getLong(ARGS_CREATEDTIME, 0);
        }

        return rootView;

    }

    // 日付入力
    //  時刻入力
    @Override
    public void onStart(){
        super.onStart();

        // 日付テキスト
        EditText editText = (EditText)getActivity().findViewById(R.id.editDate);
        // 時刻テキスト
        EditText editTime = (EditText)getActivity().findViewById(R.id.editTime);
        // キーボードを非表示
        editText.setKeyListener(null);
        editTime.setKeyListener(null);

        editText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    DialogFragment dateDialog = new DatePickerDialogFragment();
                    dateDialog.show(getFragmentManager(), "dialog");

            }
        });

        editTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DialogFragment timeDialog = new TimePickerDialogFragment();
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
            if (!TextUtils.isEmpty(value) && mIsTextEdited) {
                Intent resultData = new Intent();
                resultData.putExtra(ARGS_COLORLABEL, mColorLabel);
                resultData.putExtra(ARGS_VALUE, value);
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
        if (viewId == R.id.color_none) {
            mColorLabel = AddSchedule.ColorLabel.NONE;
        } else if (viewId == R.id.color_amber) {
            mColorLabel = AddSchedule.ColorLabel.AMBER;
        } else if (viewId == R.id.color_pink) {
            mColorLabel = AddSchedule.ColorLabel.PINK;
        } else if (viewId == R.id.color_indigo) {
            mColorLabel = AddSchedule.ColorLabel.INDIGO;
        } else if (viewId == R.id.color_green) {
            mColorLabel = AddSchedule.ColorLabel.GREEN;
        }
        mEtInput.setTextColor(getColorResource(mColorLabel));
    }

    /**
     * カラーラベルに応じたカラーリソースを返却.
     *
     * @param color : カラー
     */
    private int getColorResource(int color) {
        int resId = AddSchedule.ColorLabel.NONE;
        if (color == AddSchedule.ColorLabel.NONE) {
            resId = getResources().getColor(R.color.material_grey_500);
        } else if (color == AddSchedule.ColorLabel.AMBER) {
            resId = getResources().getColor(R.color.material_amber_500);
        } else if (color == AddSchedule.ColorLabel.PINK) {
            resId = getResources().getColor(R.color.material_pink_500);
        } else if (color == AddSchedule.ColorLabel.INDIGO) {
            resId = getResources().getColor(R.color.material_indigo_500);
        } else if (color == AddSchedule.ColorLabel.GREEN) {
            resId = getResources().getColor(R.color.material_green_500);
        }
        return resId;
    }
}
