package com.example.a171y034.travelbook719.Album.Crash;

import com.clock.utils.crash.CrashExceptionHandler;

/**
 * 自定义的回传闪退日志到远程服务器 リモートサーバーへのカスタムバックホールフラッシュログ
 * <p/>
 * Created by Clock on 2016/1/27.
 */
public class SimpleCrashReporter implements CrashExceptionHandler.CrashExceptionRemoteReport {

    @Override
    public void onCrash(Throwable ex) {
        //接下来要在此处加入将闪退日志回传到服务器的功能

        //次に、フラッシュバック・ログをサーバーに戻す機能をここに追加する必要があります。
    }

}
