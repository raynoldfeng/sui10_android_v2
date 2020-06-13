package com.sui10.commonlib.utils;

import android.os.Handler;
import android.os.Looper;

public class ThreadUtils {

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static void runOnOtherThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static boolean runOnOtherThread(final Runnable runnable, long delay) {
        return runAsyncOnCurrentThread(new Runnable() {
            @Override
            public void run() {
                runOnOtherThread(runnable);
            }
        }, delay);
    }

    public static boolean runOnMainThread(Runnable runnable) {
        return new Handler(Looper.getMainLooper()).post(runnable);
    }

    public static boolean runOnMainThread(Runnable runnable, long delay) {
        return new Handler(Looper.getMainLooper()).postDelayed(runnable, delay);
    }

    public static boolean runAsyncOnCurrentThread(Runnable runnable) {
        return new Handler().post(runnable);
    }

    public static boolean runAsyncOnCurrentThread(Runnable runnable, long delay) {
        return new Handler().postDelayed(runnable, delay);
    }

    public static void confirmLooperPrepared() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
    }
}