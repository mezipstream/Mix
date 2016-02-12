package me.zipstream.mix.base;

import android.app.Application;
import android.content.Context;

import me.zipstream.mix.util.ImageLoaderProxy;

public class MyApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = this;
        ImageLoaderProxy.initImageLoader(this);
    }

    public static Context getContext() {
        return sContext;
    }
}
