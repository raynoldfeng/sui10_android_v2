package com.sui10.commonlib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.sui10.commonlib.base.CommonApplication;

public class SharedPreferenceUtils {

    private static Context getAppContext() {
        return CommonApplication.getContext();
    }

    public static void WriteStringPreferences(String name, String key, String value) {
        SharedPreferences preferences = getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    public static void WriteIntPreferences(String name, String key, int value) {
        SharedPreferences preferences = getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        preferences.edit().putInt(key, value).commit();
    }

    public static void WriteLongPreferences(String name, String key, long value) {
        SharedPreferences preferences = getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        preferences.edit().putLong(key, value).commit();
    }

    public static void removePreferences(String name, String key) {
        SharedPreferences preferences = getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        preferences.edit().remove(key).commit();
    }

    public static void removePreferences(String name) {
        SharedPreferences preferences = getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }

    public static void WriteBooleanPreferences(String name, String key, Boolean value) {
        SharedPreferences preferences = getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).commit();
    }

    public static String ReadStringPreferences(String name, String key, String defValue) {
        SharedPreferences preferences = getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getString(key, defValue);
    }

    public static int ReadIntPreferences(String name, String key, int defValue) {
        SharedPreferences preferences = getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getInt(key, defValue);
    }

    public static long ReadLongPreferences(String name, String key, long defValue) {
        SharedPreferences preferences = getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getLong(key, defValue);
    }

    public static boolean ReadBooleanPreferences(String name, String key, Boolean defValue) {
        SharedPreferences preferences = getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defValue);
    }
}

