package com.sui10.suishi;

import com.sui10.commonlib.base.CommonApplication;
import com.sui10.commonlib.log.LogManager;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

public class SuishiApplication  extends CommonApplication {
    private final String TAG = "SuishiApplication";

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
