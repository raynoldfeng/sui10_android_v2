package com.comic.commonlib.config;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.comic.commonlib.BuildConfig;
import com.comic.commonlib.log.LogManager;

//TODO..... 待完善
//如果是packng打包，从名字里获取渠道信息
public class BuildHelper {
    private static String TAG ="BuildHelper";
    private static final String SNAPSHOT_TAG = "-SNAPSHOT";
    private static final String OFFICIAL_CHANNEL = "official";
    private static String mVersionName = null;
    private static int mVersionCode = 0;
    private static boolean mIsDebugMode = true;

    public static void init(Context context){
        String str = "";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS);
            mVersionCode = info.versionCode;
            mVersionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mIsDebugMode = isDebugMode(context);
        LogManager.e(TAG,"info %s version code %d, version name:%s, isdebug %b",str,mVersionCode,mVersionName,mIsDebugMode);
    }

    public static boolean isLocalBuild(){
        return !isJenkinsBuild();
    }

    public static boolean isSnapshot(){
        if(mVersionName == null)
            return false;
        else
            return mVersionName.contains(SNAPSHOT_TAG);
    }

    public static boolean isDebug(){
        return mIsDebugMode;
    }

    public static boolean isRelease(){return !mIsDebugMode;}


    /**
     * @param context 上下文
     * @return 是否在Debug模式
     */
    private static boolean isDebugMode(Context context) {
        boolean debuggable = false;
        ApplicationInfo appInfo = null;
        PackageManager packageManager = context.getPackageManager();
        try {
            appInfo = packageManager.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (appInfo != null) {
            debuggable = (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) > 0;
        }
        return debuggable;
    }

    public static String getChannelName(){
        return OFFICIAL_CHANNEL;
    }

    public static boolean isJenkinsBuild(){
        return mVersionCode > 0 ;
    }

    public static int getVersionCode() {
        return mVersionCode;
    }

    public static String getVersionName() {
        return mVersionName;
    }

    public static boolean isTest(){
        return (isDebug() || isSnapshot());
    }
}
