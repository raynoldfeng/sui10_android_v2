package com.comic.commonlib.base;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;

import com.comic.commonlib.config.BuildHelper;
import com.comic.commonlib.utils.ProcessUtils;

public abstract class CommonApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        BuildHelper.init(this);
        mContext = this;
    }

    @Override
    public void onLowMemory() {
       //TODO..内存不足时清理图片缓存
        super.onLowMemory();
    }

    @Override
    protected void attachBaseContext(Context context){
        super.attachBaseContext(context);
        mContext = context;
    }

    @Override
    public void onTrimMemory(int level) {
        //TODO..内存不足时清理图片缓存
        super.onTrimMemory(level);
    }

    public abstract void  onClearMemory();

    public static Context getContext() {
        return mContext;
    }
}
