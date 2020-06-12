package com.comic.yoyo.module.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.comic.commonlib.network.manager.NetworkManager;
import com.comic.commonlib.ui.presenter.BasePresenter;
import com.comic.commonlib.ui.view.widget.BaseActivity;
import com.comic.yoyo.base.ui.JumpManager;
import com.comic.yoyo.module.login.ui.LoginActivity;

//TODO... 权限申请及处理

public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tryGoToTargetPage();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    private void tryGoToTargetPage()
    {
        if(!NetworkManager.isNetworkAvailable(this))
        {
            goToTargetPage();
        }
        else
        {
            JumpManager.gotoLoginActivity(this);
        }
        finish();
    }

    private void goToTargetPage()
    {

    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadData() {
    }


    @Override
    protected void onNetworkConnected(int type) {
    }

    @Override
    protected void onNetworkDisConnected() {
    }


}
