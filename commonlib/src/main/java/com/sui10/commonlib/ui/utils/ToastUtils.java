package com.sui10.commonlib.ui.utils;

import android.content.Context;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sui10.commonlib.R;
import com.sui10.commonlib.base.CommonApplication;
import com.sui10.commonlib.utils.ResourceUtils;
import com.sui10.commonlib.utils.ThreadUtils;

/**
 * toast 工具类
 * very short: 1.5s
 * short: 2s
 * medium: 2.75s
 * long: 3.5s
 */
public class ToastUtils {
    public static void customShow(final int layoutResId, final int txtViewResId, final String msg) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                int duration = Toast.LENGTH_LONG;
                Toast toast  = makeCustomToast(layoutResId, txtViewResId, msg, duration);
                toast.show();
            }
        });
    }

    public static void customShow(final SpannableString spannableString) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                int duration = Toast.LENGTH_LONG;
                Toast toast  = makeCustomToast(spannableString, duration);
                toast.show();
            }
        });
    }

    public static void show(final String msg, final int millisecond) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                int duration;
                if (millisecond != Toast.LENGTH_SHORT && millisecond != Toast.LENGTH_LONG) {
                    if (millisecond > 2000) {
                        duration = Toast.LENGTH_LONG;
                    } else {
                        duration = Toast.LENGTH_SHORT;
                    }
                } else {
                    duration = millisecond;
                }
                // Toast toast = Toast.makeText(CommonApplication.getContext(), msg, duration);
                Toast toast = makeCustomToast(R.layout.common_toast_layout,R.id.tv_toast,msg,duration);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        }, millisecond);
    }

    public static void show(int resId, int millisecond) {
        String msg = ResourceUtils.getString(CommonApplication.getContext(), resId);
        show(msg, millisecond);
    }

    public static void showVeryShort(int resId) {
        showShort(resId);
    }

    public static void showVeryShort(String msg) {
        showShort(msg);
    }

    public static void showShort(int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    public static void showShort(String msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    public static void showMedium(int resId) {
        showShort(resId);
    }

    public static void showMedium(String msg) {
        showShort(msg);
    }

    public static void showLong(int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    public static void showLong(String msg) {
        show(msg, Toast.LENGTH_LONG);
    }


    private static Toast makeCustomToast(int customResId, int msgResId, CharSequence text, int duration) {
        Toast result = new Toast(CommonApplication.getContext());
        LayoutInflater inflate = (LayoutInflater)
                CommonApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(customResId, null);
        TextView tv = (TextView) v.findViewById(msgResId);
        tv.setText(text);

        result.setView(v);
        result.setDuration(duration);

        return result;
    }

    private static Toast makeCustomToast(SpannableString spannableString, int duration) {
        Toast result = new Toast(CommonApplication.getContext());
        LayoutInflater inflate = (LayoutInflater) CommonApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.common_toast_layout, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_toast);
        tv.setText(spannableString);
        result.setView(v);
        result.setDuration(duration);
        return result;
    }
}
