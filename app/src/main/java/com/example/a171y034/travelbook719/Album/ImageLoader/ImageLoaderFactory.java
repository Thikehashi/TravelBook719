package com.example.a171y034.travelbook719.Album.ImageLoader;

/**
 * ImageLoader工厂类
 ファクトリクラス
 * <p/>
 * Created by Clock on 2016/1/18.
 */
public class ImageLoaderFactory {

    private static volatile ImageLoaderWrapper sInstance;

    private ImageLoaderFactory() {

    }

    /**
     * 获取图片加载器
     *イメージローダーを取得する
     * @return
     */
    public static ImageLoaderWrapper getLoader() {
        if (sInstance == null) {
            synchronized (ImageLoaderFactory.class) {
                if (sInstance == null) {
                    sInstance = new UniversalAndroidImageLoader();//<link>https://github.com/nostra13/Android-Universal-Image-Loader</link>
                }
            }
        }
        return sInstance;
    }
}
