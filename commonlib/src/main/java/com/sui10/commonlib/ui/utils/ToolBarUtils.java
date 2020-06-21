package com.sui10.commonlib.ui.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sui10.commonlib.R;
import com.sui10.commonlib.ui.utils.StatusBarUtils;

public class ToolBarUtils {

    /**
     * 统一页面顶部自定义toolbar的高度，调整沉浸模式下顶部间距
     * @param view
     * @param context
     */
    @TargetApi(21)
    public static void adjustStatusBar(View view, Context context) {
        if (view == null) {
            return;
        }
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            if (sdkInt >= 21) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            if (view.getParent() == null) {
                return;
            }
            ViewParent viewParent = view.getParent();
            View statusBar = view.findViewById(R.id.common_status_bar);
            if (statusBar != null) {
                ViewGroup.LayoutParams params = statusBar.getLayoutParams();
                params.height = com.sui10.commonlib.ui.utils.StatusBarUtils.getStatusBarHeight2(context);
            }
            if (viewParent instanceof RelativeLayout) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.height = (int) (context.getResources().getDimension(
                        R.dimen.common_title_bar_height) + com.sui10.commonlib.ui.utils.StatusBarUtils.getStatusBarHeight2(context));
                view.setLayoutParams(params);
            } else if (viewParent instanceof LinearLayout) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.height = (int) (context.getResources().getDimension(
                        R.dimen.common_title_bar_height) + com.sui10.commonlib.ui.utils.StatusBarUtils.getStatusBarHeight2(context));
                view.setLayoutParams(params);
            } else if (viewParent instanceof FrameLayout) {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.height = (int) (context.getResources().getDimension(
                        R.dimen.common_title_bar_height) + StatusBarUtils.getStatusBarHeight2(context));
                view.setLayoutParams(params);
            }
        }
    }

    public static int getTopbarHeight(Context context){
        int sdkInt = Build.VERSION.SDK_INT;
        int height=(int) (context.getResources().getDimension(R.dimen.common_title_bar_height));
        if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            return height+StatusBarUtils.getStatusBarHeight2(context);
        }
        return height;
    }
}
