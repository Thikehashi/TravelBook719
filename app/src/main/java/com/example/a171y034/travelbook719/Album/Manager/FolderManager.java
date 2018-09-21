package com.example.a171y034.travelbook719.Album.Manager;

import android.os.Environment;

import java.io.File;

/**
 * 目录管理器 ディレクトリマネージャ
 * <p/>
 */
public class FolderManager {

    /**
     * SDカード上のアプリケーションのホームディレクトリ名
     */
    private final static String APP_FOLDER_NAME = "album";
    /**
     * フラッシュバック・ログ・ディレクトリ名を格納する
     */
    private final static String CRASH_LOG_FOLDER_NAME = "crash";

    private FolderManager() {
    }

    /**
     * アプリのホームディレクトリをsdカードで取得する
     *
     * @return 成功した場合はディレクトリに戻り、失敗した場合はnullを返します。
     */
    public static File getAppFolder() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File appFolder = new File(Environment.getExternalStorageDirectory(), APP_FOLDER_NAME);
            return createOnNotFound(appFolder);

        } else {
            return null;
        }
    }

    /**
     * フラッシュバック・ログ・ストレージ・ディレクトリを取得する
     *
     * @return
     */
    public static File getCrashLogFolder() {
        File appFolder = getAppFolder();
        if (appFolder != null) {

            File crashLogFolder = new File(appFolder, CRASH_LOG_FOLDER_NAME);
            return createOnNotFound(crashLogFolder);
        } else {
            return null;
        }
    }

    /**
     * ディレクトリの作成
     *
     * @param folder
     * @return 作成が成功した場合はディレクトリを返し、失敗した場合はnullを返します。
     */
    private static File createOnNotFound(File folder) {
        if (folder == null) {
            return null;
        }

        if (!folder.exists()) {
            folder.mkdirs();
        }

        if (folder.exists()) {
            return folder;
        } else {
            return null;
        }
    }
}
