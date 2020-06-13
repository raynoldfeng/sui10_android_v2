package com.sui10.commonlib.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ProcessUtils {

    public static String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> mRunningAppProcesses;
        if(mActivityManager != null && (mRunningAppProcesses = mActivityManager.getRunningAppProcesses()) != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : mRunningAppProcesses) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        }
        return null;
    }

    public static boolean isMainProcess(Context context){
        //获取不到进程名 默认认为是主进程
        return getCurrentProcessName(context) == null || context.getPackageName().equals(getCurrentProcessName(context));
    }

    public static boolean isToolsProcess(Context context){
        //获取Tools进程名称
        String processName = context.getPackageName()+":tools";
        String currentProcessName = getCurrentProcessName(context);
        return processName.equals(currentProcessName);
    }

    @NonNull
    public static List<String> getInstalledPackages(PackageManager pm){
        List<PackageInfo> installedPackages;
        List<String> installedPackageNames = new ArrayList<>();
        if(pm != null && (installedPackages = pm.getInstalledPackages(0)) != null){
            for(PackageInfo info : installedPackages){
                if(info == null || info.applicationInfo == null) continue;
                if((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
                    installedPackageNames.add(info.packageName);
            }
        }
        return installedPackageNames;
    }
}
