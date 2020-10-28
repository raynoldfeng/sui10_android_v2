package com.sui10.commonlib.utils;


import android.text.TextUtils;

import java.util.UUID;

public class LoginRandomCodeUtils {
    public static String mRandomCode;


    //获取随机码，此随机码作为请求接口用
    public static String GetRandomCode() {
        if (TextUtils.isEmpty(mRandomCode)) {
            mRandomCode = UUID.randomUUID().toString();
        }
        return mRandomCode;
    }
}
