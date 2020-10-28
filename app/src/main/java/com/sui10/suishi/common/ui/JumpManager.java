package com.sui10.suishi.common.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sui10.commonlib.webview.WebViewActivity;
import com.sui10.suishi.common.ui.widget.FragContainerActivity;
import com.sui10.suishi.module.course.bean.CourseBean;
import com.sui10.suishi.module.course.ui.OpenCourseDetailActivity;
import com.sui10.suishi.module.course.ui.ProfessionalCourseDetailActivity;
import com.sui10.suishi.module.login.ui.LoginActivity;
import com.sui10.suishi.module.course.ui.OpenCourseFragment;
import com.sui10.suishi.module.main.ui.MainActivity;
import com.sui10.suishi.module.usersystem.ui.AccountInfoSettingFragment;

public class JumpManager {

    public static void gotoLoginActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void gotoMainActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void gotoOpenCourseFragment(Context context, String title)
    {
        Bundle bundle = new Bundle();
        bundle.putString(OpenCourseFragment.KEY_FRAG_TITLE, title);
        FragContainerActivity.start(context, OpenCourseFragment.class, title, bundle);
    }

    /*
     * 专业课详情页
     */
    public static void gotoProCourseDetailActivity(Context context, CourseBean bean){
        Intent intent = new Intent(context, ProfessionalCourseDetailActivity.class);
        intent.putExtra(ProfessionalCourseDetailActivity.COURSE_BEAN_KEY, bean);
        context.startActivity(intent);
    }

    /*
    * 公开课详情页
     */
    public static void gotoOpenCourseDetailActivity(Context context, CourseBean bean){
        Intent intent = new Intent(context, OpenCourseDetailActivity.class);
        intent.putExtra(OpenCourseDetailActivity.COURSE_BEAN_KEY, bean);
        context.startActivity(intent);
    }

    /*
     * 个人信息设置页
     */
    public static void gotoAccountInfoSettingFragment(Context context)
    {
        FragContainerActivity.start(context, AccountInfoSettingFragment.class,null);
    }

    public static void gotoWebViewActivity(Context context ,String url ,String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }
}
