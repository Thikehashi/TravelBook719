package com.example.a171y034.travelbook719;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.a171y034.travelbook719.Album.UI.PhotoMainFragment;
import com.example.a171y034.travelbook719.Belongings.Add.AddBelongings;
import com.example.a171y034.travelbook719.Belongings.Fragment.BelongingsFormFragment;
import com.example.a171y034.travelbook719.Belongings.Fragment.BelongingsMainFragment;
import com.example.a171y034.travelbook719.Memo.Add.AddMemo;
import com.example.a171y034.travelbook719.Memo.Fragment.MemoFormFragment;
import com.example.a171y034.travelbook719.Memo.Fragment.MemoMainFragment;
import com.example.a171y034.travelbook719.Schedule.Add.AddSchedule;
import com.example.a171y034.travelbook719.Schedule.Fragment.ScheduleFormFragment;
import com.example.a171y034.travelbook719.Schedule.Fragment.ScheduleMainFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private List<AddSchedule> mAddListSchedule;
    private List<AddMemo> mAddListMemo;
    private List<AddBelongings> mAddListBelongings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // データ作成
        mAddListSchedule = AddSchedule.Item();
        mAddListMemo = AddMemo.Item();
        mAddListBelongings = AddBelongings.Item();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        // BottomNavigationBarでのページ選択、画面遷移
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_schedule:
                                selectedFragment =  ScheduleMainFragment.newInstance();
                                break;
                            case R.id.navigation_belongings:
                                selectedFragment = BelongingsMainFragment.newInstance();
                                break;
                            case R.id.navigation_photo:
                               selectedFragment = PhotoMainFragment.newInstance();
                                break;
                            case R.id.navigation_memo:
                                selectedFragment = MemoMainFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
        // 起動から表示
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, ScheduleMainFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            //フォーム画面を開いている場合は画面を閉じる
            getSupportFragmentManager().popBackStack();
        } else {
            //リスト画面の場合は通常のバックキー処理(アプリを終了)
            super.onBackPressed();
        }
    }

    /*    /**
     * Scheduleフォーム画面を表示
     *スケジュール画面
     * @param item Scheduleリストデータ
     */
    public void showScheduleForm(AddSchedule item) {
        String tag = ScheduleFormFragment.TAG;
        ScheduleFormFragment fragment;
        if (item == null) {
            fragment = ScheduleFormFragment.newInstance();
        } else {
            fragment = ScheduleFormFragment.newInstance(item.getCategory(), item.getStartTime(), item.getEndTime(), item.getDate(), item.getValue(), item.getCreatedTime());
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, tag).addToBackStack(tag).commit();
    }

        /*    /**
     * Memoフォーム画面を表示
     *メモ画面
     * @param item Memoリストデータ
     */
        public void showMemoForm(AddMemo item) {
            String tag = MemoFormFragment.TAG;
            MemoFormFragment fragment;
            if (item == null) {
                fragment = MemoFormFragment.newInstance();
            } else {
                fragment = MemoFormFragment.newInstance(item.getColorLabel(),
                        item.getValue(), item.getCreatedTime());
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, tag).addToBackStack(tag).commit();
        }

    /*
    * Belongingsフォーム画面を表示
    *持ち物リスト画面
    * @param item Belongingsリストデータ
    */
        public void showBelongingsForm(AddBelongings item) {
            String tag = BelongingsFormFragment.TAG;
            BelongingsFormFragment fragment;
            if (item == null) {
                fragment = BelongingsFormFragment.newInstance();
            } else {
                fragment = BelongingsFormFragment.newInstance(item.getValue(), item.getCreatedTime());
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, tag).addToBackStack(tag).commit();
        }

    public List<AddSchedule> getAddList() {
        return mAddListSchedule;
    }
    public List<AddMemo> getAddListMemo() {return mAddListMemo;}
    public List<AddBelongings> getAddListBelongings() {return mAddListBelongings;}

    // Actionバーにmenu_mainの表示
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    // Actionbarのメニューがクリックされたら移動
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_setting) {   // ここでアプリ設定ボタンが押されたときに、ページの遷移を行う
            Intent action_menu = new Intent(getApplication(), SettingActivity.class);
            startActivity(action_menu);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
