package com.example.a171y034.travelbook719.Album;

import android.app.Application;
import android.util.Log;

import com.example.a171y034.travelbook719.Album.Crash.SimpleCrashReporter;
import com.example.a171y034.travelbook719.Album.ImageLoader.UniversalAndroidImageLoader;
import com.example.a171y034.travelbook719.Album.Manager.FolderManager;
import com.clock.utils.crash.CrashExceptionHandler;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Clock on 2016/1/17.
 */
public class AlbumApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //ローカルに生成されたフラッシュバックのログファイルを設定するために使用
        // それをしないとサドパーティのSDKはクラッシュログを報告できない
        configCollectCrashInfo();

        initBuglyConfig();

        UniversalAndroidImageLoader.init(getApplicationContext());

    }

    /**
     * グローバル設定の生成と初期化を行う
     */
    private void configCollectCrashInfo() {
        CrashExceptionHandler crashExceptionHandler = new CrashExceptionHandler(this, FolderManager.getCrashLogFolder());
        CrashExceptionHandler.CrashExceptionRemoteReport remoteReport = new SimpleCrashReporter();
        crashExceptionHandler.configRemoteReport(remoteReport); //エラーログをリモートサーバーに戻す
        Thread.setDefaultUncaughtExceptionHandler(crashExceptionHandler);
    }

    /**
     * バグの設定を初期化する
     */
    private void initBuglyConfig() {
        CrashReport.initCrashReport(getApplicationContext(), "900019014", false);
        String buglyVersion = CrashReport.getBuglyVersion(getApplicationContext());
        Log.i("Bugly", "current bugly version: " + buglyVersion);
    }
}
