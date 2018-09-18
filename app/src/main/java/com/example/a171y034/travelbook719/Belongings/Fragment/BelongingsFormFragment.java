package com.example.a171y034.travelbook719.Belongings.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.a171y034.travelbook719.Belongings.Add.AddBelongings;
import com.example.a171y034.travelbook719.R;

/**
 * Created by 171y034 on 2018/07/24.
 */
public class BelongingsFormFragment extends Fragment {

    public static final String TAG = BelongingsFormFragment.class.getSimpleName();

    public static final String ARGS_COLORLABEL = "key-colorlabel";

    public static final String ARGS_VALUE = "key-value";

    public static final String ARGS_CREATEDTIME = "key-createdtime";

    private int mColorLabel = AddBelongings.ColorLabel.NONE;

    private long mCreatedTime = 0;

    private EditText mEtInput;

    private boolean mIsTextEdited = false;

    public static BelongingsFormFragment newInstance() {
        return new BelongingsFormFragment();
    }

    public static BelongingsFormFragment newInstance(String value, long createdTime) {
        BelongingsFormFragment fragment = new BelongingsFormFragment();
        Bundle args = new Bundle();
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

        View rootView = inflater.inflate(R.layout.fragment_belongings_add, container, false);

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

        //編集データを受け取っていたらセット
        Bundle args = getArguments();
        if (args != null) {

            //値をセット
            String value = args.getString(ARGS_VALUE);
            mEtInput.setText(value);

            //作成時間をセット
            mCreatedTime = args.getLong(ARGS_CREATEDTIME, 0);
        }
        return rootView;
    }

    // チェックボタン(追加ボタン)を表示させる
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem menuItem = menu.findItem(R.id.actionok);
        if (menuItem == null) {
            inflater.inflate(R.menu.menu_sub, menu);
            menu.findItem(R.id.actionok).setVisible(true);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    // チェックボタンを押したときの処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionok) {
            //持ち物リストを追加
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
                resultData.setAction(BelongingsMainFragment.ACTION_CREATE_BELONGINGS);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(resultData);

                // ひとつ前のFragmentに戻る
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                BelongingsMainFragment fragment = new BelongingsMainFragment();
                ft.replace(R.id.content, fragment);
                ft.commit();

            } else {
                mEtInput.setError("持ち物を入力してください");
            }

            //ソフトウェアキーボードを閉じる
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(mEtInput.getWindowToken(), 0);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
