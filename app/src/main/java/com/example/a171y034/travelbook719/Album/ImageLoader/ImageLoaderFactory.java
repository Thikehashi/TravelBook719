package com.example.a171y034.travelbook719.Album.ImageLoader;

/**
 * ImageLoaderファクトリクラス
 * <p/>
 */
public class ImageLoaderFactory {

    private static volatile ImageLoaderWrapper sInstance;

    private ImageLoaderFactory() {

    }

    /**
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
