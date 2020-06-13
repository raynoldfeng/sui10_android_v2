package com.sui10.suishi.common.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sui10.suishi.module.login.ui.LoginActivity;
import com.sui10.suishi.module.course.ui.OpenCourseFragment;
import com.sui10.suishi.module.main.ui.MainActivity;

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
}
