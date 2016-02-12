package me.zipstream.mix.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class ImageLoaderProxy {

    private static final int MAX_MEMORY_CACHE = 1024 * 1024 * 10;
    private static final int MAX_DISK_CACHE = 1024 * 1024 * 50;

    private static ImageLoader sImageLoader;

    public static ImageLoader getImageLoader() {
        if (sImageLoader == null) {
            synchronized (ImageLoaderProxy.class) {
                sImageLoader = ImageLoader.getInstance();
            }
        }

        return sImageLoader;
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheSize(MAX_DISK_CACHE)
                .memoryCacheSize(MAX_MEMORY_CACHE)
                .memoryCache(new LruMemoryCache(MAX_MEMORY_CACHE))
                .build();

        getImageLoader().init(configuration);
    }

    public static DisplayImageOptions getOptions4PictureList(int loadingResource) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .showImageOnLoading(loadingResource)
                .showImageForEmptyUri(loadingResource)
                .showImageOnFail(loadingResource)
                .build();

        return options;
    }
}
