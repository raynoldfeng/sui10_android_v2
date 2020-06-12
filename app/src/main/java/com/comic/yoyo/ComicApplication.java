package com.comic.yoyo;

import com.comic.commonlib.base.CommonApplication;
import com.comic.commonlib.log.LogManager;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

public class ComicApplication  extends CommonApplication {
    private final String TAG = "ComicApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        catchRxJavaUnCatchException();
    }

    @Override
    public void onClearMemory() {

    }

    private void catchRxJavaUnCatchException() {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogManager.i(TAG, throwable.getMessage());
            }
        });
    }
}
