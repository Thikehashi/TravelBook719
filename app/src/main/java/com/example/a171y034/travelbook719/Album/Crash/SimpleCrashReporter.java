package com.example.a171y034.travelbook719.Album.Crash;

import com.clock.utils.crash.CrashExceptionHandler;

/**
 * リモートサーバーへのカスタムバックホールフラッシュログ
 * <p/>
 */
public class SimpleCrashReporter implements CrashExceptionHandler.CrashExceptionRemoteReport {

    @Override
    public void onCrash(Throwable ex) {
        //次に、フラッシュバック・ログをサーバーに戻す機能をここに追加する必要があります。
    }

}
