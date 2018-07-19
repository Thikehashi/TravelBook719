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

public class MainActivity extends AppCompatActivity  {


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
                                selectedFragment = ScheduleMainFragment.newInstance();
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
