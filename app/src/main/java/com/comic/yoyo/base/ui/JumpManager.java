package com.comic.yoyo.base.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.comic.yoyo.module.login.ui.LoginActivity;
import com.comic.yoyo.module.main.ui.HomeFragment;
import com.comic.yoyo.module.main.ui.MainActivity;

public class JumpManager {

    public static void gotoLoginActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void gotoMainActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void gotoHomeFragment(Context context, String title)
    {
        Bundle bundle = new Bundle();
        bundle.putString(HomeFragment.KEY_FRAG_TITLE, title);
        FragContainerActivity.start(context, HomeFragment.class, title, bundle);
    }
}
