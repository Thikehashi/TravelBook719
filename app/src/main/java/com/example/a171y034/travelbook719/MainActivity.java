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

import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private List<Add> mAddList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_schedule:
                                showScheduleList();
                                //selectedFragment =  ScheduleMainFragment.newInstance();
                                break;
                            case R.id.navigation_list:
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

        showScheduleList();
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

    /**
     * TODOリスト一覧を表示
     */
    public void showScheduleList() {
        String tag = ScheduleMainFragment.TAG;
        getSupportFragmentManager().beginTransaction().replace(R.id.content,
                ScheduleMainFragment.newInstance(), tag).commit();
    }

/*    public void showBelongingsList(){
        String tag = BelongingsMainFragment.TAG;
        getSupportFragmentManager().beginTransaction().replace(R.id.content,
                BelongingsMainFragment.newInstance(), tag).commit();
    }
*/
    /**
     * Scheduleフォーム画面を表示
     *
     * @param item Scheduleリストデータ
     */
    public void showScheduleForm(Add item) {
        String tag = ScheduleFormFragment.TAG;
        ScheduleFormFragment fragment;
        if (item == null) {
            fragment = ScheduleFormFragment.newInstance();
        } else {
            fragment = ScheduleFormFragment.newInstance(item.getColorLabel(),
                    item.getValue(), item.getCreatedTime());
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, tag).addToBackStack(tag).commit();
    }

    public List<Add> getAddList() {
        return mAddList;
    }

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
