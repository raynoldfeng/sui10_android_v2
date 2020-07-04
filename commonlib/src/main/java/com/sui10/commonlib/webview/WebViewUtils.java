package com.sui10.commonlib.webview;

import android.app.Application;

import com.sui10.commonlib.log.LogManager;
import com.tencent.smtt.sdk.QbSdk;

public class WebViewUtils {

    private static final String TAG = "WebViewUtils";

    public static void initTbs(Application application) {
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                LogManager.i(TAG, "x5 onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                LogManager.i(TAG, "x5 onViewInitFinished");
            }
        };
        QbSdk.initX5Environment(application.getApplicationContext(), cb);
    }
}
