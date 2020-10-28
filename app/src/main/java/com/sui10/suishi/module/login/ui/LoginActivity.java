package com.sui10.suishi.module.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.sui10.commonlib.base.constants.NetConstant;
import com.sui10.commonlib.eventbus.EventBusManager;
import com.sui10.commonlib.log.LogManager;
import com.sui10.commonlib.network.manager.NetworkManager;
import com.sui10.commonlib.thirdparty.login.ThirdLoginCallback;
import com.sui10.commonlib.thirdparty.login.ThirdLoginType;
import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.utils.StatusBarUtils;
import com.sui10.commonlib.ui.utils.ToastUtils;
import com.sui10.commonlib.ui.view.base.BaseActivity;
import com.sui10.commonlib.utils.MobileNumberUtil;
import com.sui10.commonlib.utils.ResourceUtils;
import com.sui10.suishi.R;
import com.sui10.suishi.common.net.models.LoginModels;
import com.sui10.suishi.common.ui.JumpManager;
import com.sui10.suishi.common.ui.rsp.CommonRsp;
import com.sui10.suishi.manager.UserManager;
import com.sui10.suishi.module.login.bean.AccountInfo;
import com.sui10.suishi.module.login.event.LoginStatusEvent;
import com.sui10.suishi.module.login.mvp.ILoginView;
import com.sui10.suishi.module.login.mvp.LoginPresenter;
import com.sui10.suishi.module.login.thirdparty.SuishiThirdLogin;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements ILoginView {
    public static final String TAG = "loginActivity";

    private SuishiThirdLogin mSuishiThirdLogin;

    @BindView(R.id.btn_login)
    TextView mLoginBtnTv;
    @BindView(R.id.btn_one)
    Button mBtnOne;
    @BindView(R.id.et_mobile)
    EditText mMobileEt;
    @BindView(R.id.hint_tv)
    TextView mLoginHintTv;
    @BindView(R.id.verification_code_rl)
    RelativeLayout mVerificationCodeRl;
    @BindView(R.id.reget_verfication_code_tv)
    TextView mRegetVerficationCodeTv;
    @BindView(R.id.verification_code_et)
    EditText mVerificationCodeEt;


    private boolean mIsSmsCodeGetted = false;


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
        if(!NetworkManager.isNetworkAvailable(this)){
            ToastUtils.showShort(R.string.check_network);
            return;
        }

        String mobile=mMobileEt.getText().toString().trim();

        if(!MobileNumberUtil.judgePhoneNums(mobile)){
            mLoginHintTv.setText(getString(R.string.mobile_phone_invalid));
            mLoginHintTv.setVisibility(View.VISIBLE);
            return;
        }
        mLoginHintTv.setVisibility(View.INVISIBLE);

        if(mPresenter!=null){
            LoginPresenter presenter =  (LoginPresenter)mPresenter;
            if(!mIsSmsCodeGetted)
                presenter.getSmsCode(mobile);
            else {
                String verifyCode = mVerificationCodeEt.getText().toString().trim();
                if(TextUtils.isEmpty(verifyCode)) {
                    ToastUtils.showShort(R.string.verify_code_is_null);
                }else {
                    presenter.loginBySmsCode(mobile,verifyCode);
                }
            }
        }
    }

    private void initViewAndEvent(){
        StatusBarUtils.darkMode(this);
        mMobileEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mLoginHintTv.setVisibility(View.INVISIBLE);
                String mobile=s.toString();
                if(TextUtils.isEmpty(mobile)||s.toString().length()<11){
                    setLoginBtnEnable(false);
                }else{
                    setLoginBtnEnable(true);
                }
            }
        });

        mVerificationCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable text) {
                String verificationCode = text.toString().trim();
                if(TextUtils.isEmpty(verificationCode) || verificationCode.length() < 4 ){
                    setLoginBtnEnable(false);
                }else {
                    setLoginBtnEnable(true);
                }
            }
        });
        setLoginBtnEnable(false);
    }

    private void setLoginBtnEnable(boolean enable){
        mLoginBtnTv.setEnabled(enable);
        if(enable){
            mLoginBtnTv.setTextColor(ResourceUtils.getColor(R.color.color_FF805012));
            mLoginBtnTv.setBackgroundResource(R.drawable.bg_common_btn);
        }else{
            mLoginBtnTv.setTextColor(ResourceUtils.getColor(R.color.white));
            mLoginBtnTv.setBackgroundResource(R.drawable.bg_common_btn_enable);
        }
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
        return new LoginPresenter();
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

    @Override
    public void onGetSmsCodeRsp(CommonRsp commonRsp) {
        if(commonRsp.getCode() == NetConstant.RSP_CODE.OK){
            mIsSmsCodeGetted = true;
            mVerificationCodeRl.setVisibility(View.VISIBLE);
            mRegetVerficationCodeTv.setEnabled(false);
            mLoginBtnTv.setText(R.string.login);
            setLoginBtnEnable(false);
            mLoginBtnTv.setBackground(ResourceUtils.getDrawable(this,R.drawable.bg_common_btn_enable));
        }else {
            mLoginHintTv.setVisibility(View.VISIBLE);
            mLoginHintTv.setText(commonRsp.getMessage());
        }
    }

    @Override
    public void onLoginSucess(String token, AccountInfo accountInfo) {
        UserManager.getInstance().setAccountInfo(accountInfo);
        UserManager.getInstance().setToken(token);
        EventBusManager.postSticky(new LoginStatusEvent(LoginStatusEvent.LOGIN_STATUS.LOGIN_SUCESS));
        finish();
    }
}
