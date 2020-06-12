package com.comic.commonlib.utils;

import android.app.Activity;
import android.os.Build;

public class CommonViewUtils {
    /**
     * 判断当前Activity是否销毁
     *
     * @param activity
     * @return
     */
    public static boolean isValidActivity(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity == null || activity.isDestroyed() || activity.isFinishing();
        } else {
            return activity == null || activity.isFinishing();
        }
    }
}
