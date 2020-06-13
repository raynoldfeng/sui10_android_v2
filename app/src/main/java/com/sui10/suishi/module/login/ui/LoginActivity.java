package com.sui10.suishi.module.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.sui10.commonlib.log.LogManager;
import com.sui10.commonlib.thirdparty.login.ThirdLoginCallback;
import com.sui10.commonlib.thirdparty.login.ThirdLoginType;
import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.widget.BaseActivity;
import com.sui10.suishi.R;
import com.sui10.suishi.base.ui.JumpManager;
import com.sui10.suishi.module.login.thirdparty.SuishiThirdLogin;
import com.sui10.suishi.module.usersystem.models.AccountModel;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity{
    public static final String TAG = "loginActivity";

    private SuishiThirdLogin mSuishiThirdLogin;

    @BindView(R.id.btn_login)
    TextView mLoginBtnTv;

    @BindView(R.id.btn_one)
    Button mBtnOne;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mSuishiThirdLogin != null)
            mSuishiThirdLogin.handleActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViewAndEvent();
        //addFragment(R.id.ln_root,new OpenCourseFragment(),OpenCourseFragment.class.getCanonicalName(),true);
       // addFragment(R.id.ln_root,new OpenCourseFragment(),OpenCourseFragment.class.getCanonicalName(),true);
    }

    @OnClick(R.id.btn_login)
    public void onLogin() {
        //JumpManager.gotoOpenCourseFragment(this,"home");
        JumpManager.gotoMainActivity(this);
        finish();
    }

    private void initViewAndEvent(){
      }

    private void httpGetTest(){
        AccountModel.loginBySmsCode("86","13676021121","9225")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>() {
                    @Override
                    public void accept(JsonObject obj) throws Exception {
                        LogManager.e(TAG, "login rsp :" + obj.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogManager.e(TAG, "login failed :" + throwable.toString());
                    }
                });

    }

    private void httpPostTest(){
        AccountModel.loginBySmsCode("86","13676021121","9225")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>() {
                    @Override
                    public void accept(JsonObject obj) throws Exception {
                        LogManager.e(TAG, "login rsp :" + obj.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogManager.e(TAG, "login failed :" + throwable.toString());
                    }
                });

    }

    private void thirdLoginTest(){
        mSuishiThirdLogin = new SuishiThirdLogin(this);
        if(mSuishiThirdLogin!=null)
            mSuishiThirdLogin.login(ThirdLoginType.WECHAT, new ThirdLoginCallback() {
                @Override
                public void success(String uId, String nick, String avatar, String accessToken) {
                    LogManager.d(TAG,"success");
                }

                @Override
                public void failure() {
                    LogManager.d(TAG,"failure");

                }

                @Override
                public void cancle() {
                    LogManager.d(TAG,"cancle");
                }
            });
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
