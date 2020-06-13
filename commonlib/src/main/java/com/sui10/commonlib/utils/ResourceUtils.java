package com.sui10.commonlib.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import com.sui10.commonlib.base.CommonApplication;

public class ResourceUtils {
    public static String getString(int resId) {
        return getString(CommonApplication.getContext(), resId);
    }

    public static int getColor(int colorId) {
        return CommonApplication.getContext().getResources().getColor(colorId);
    }

    public static ColorStateList getColoStateList(int resId) {
        return CommonApplication.getContext().getResources().getColorStateList(resId);
    }

    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    public static String getMetaValue(Context context, String key) {
        return getMetaValue(context, key, "");
    }

    public static float getDimen(Context context, int resId) {
        return context.getResources().getDimension(resId);
    }

    public static synchronized String getMetaValue(Context context, String key, String defaultValue) {
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName()
                    , PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return defaultValue;
        }
        Bundle bundle = appInfo.metaData;
        if (bundle == null) {
            return defaultValue;
        }
        String value = bundle.getString(key, "");
        if (!(value == null || value.length() == 0)) {
            return value;
        }
        int valueInt = bundle.getInt(key, 0);
        return valueInt == 0 ? defaultValue : String.valueOf(valueInt);
    }

    public static String getStringByName(Context context, String name) {
        int id = context.getResources().getIdentifier(name, "string",
                context.getPackageName());
        if (id > 0) {
            return context.getString(id);
        }
        return "";
    }

    public static int getDrawableIdByName(Context context, String name) {
        int id = context.getResources().getIdentifier(name, "drawable",
                context.getPackageName());
        return id;
    }

    public static int getXmlIdByName(Context context, String name) {
        int id = context.getResources().getIdentifier(name, "xml",
                context.getPackageName());
        return id;
    }

    public static int getIdIdByName(Context context, String name) {
        int id = context.getResources().getIdentifier(name, "id",
                context.getPackageName());
        return id;
    }

    public static Drawable getDrawable(Context context, int id){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id, context.getTheme());
        } else {
            return context.getResources().getDrawable(id);
        }
    }

}
