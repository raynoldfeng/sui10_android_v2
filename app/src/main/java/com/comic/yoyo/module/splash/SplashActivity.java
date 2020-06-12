package com.comic.yoyo.module.splash;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.comic.commonlib.network.manager.NetworkManager;
import com.comic.commonlib.ui.presenter.BasePresenter;
import com.comic.commonlib.ui.view.widget.BaseActivity;
import com.comic.commonlib.utils.SharedPreferenceUtils;
import com.comic.yoyo.R;
import com.comic.yoyo.base.constant.SplashConstant;
import com.comic.yoyo.base.ui.JumpManager;
import com.comic.yoyo.module.login.ui.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

//TODO... 权限申请及处理

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    @BindView(R.id.privacy_policy_summary_rl)
    public RelativeLayout mPrivacyPolicySummaryRl;
    @BindView(R.id.confirm_btn)
    public Button mConfirmBtn;
    @BindView(R.id.cacel_btn)
    public Button mCancelBtn;
    @BindView(R.id.policy_content)
    public TextView mPolicyContentTv;
    @BindView(R.id.splash_anim_lav)
    LottieAnimationView mAnimationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAnimationView.addAnimatorListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tryGoToTargetPage();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        tryShowPrivacyPolicySummary();
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
            JumpManager.gotoMainActivity(this);
        }
        finish();
    }

    //自动登录
    private void goToTargetPage()
    {
        JumpManager.gotoMainActivity(this);
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

    private void startSplashAnimation()
    {
        mAnimationView.playAnimation();
    }

    @OnClick(R.id.confirm_btn)
    public void onConfirm() {
        mPrivacyPolicySummaryRl.setVisibility(View.GONE);
        SharedPreferenceUtils.WriteBooleanPreferences(SplashConstant.Policy.PRIVACY_POLICY_SUMMARY,
                SplashConstant.Policy.PRIVACY_POLICY_SUMMARY_SHOWED,true);
        startSplashAnimation();
    }

    private void tryShowPrivacyPolicySummary() {
        boolean showed = SharedPreferenceUtils.ReadBooleanPreferences(SplashConstant.Policy.PRIVACY_POLICY_SUMMARY,
                SplashConstant.Policy.PRIVACY_POLICY_SUMMARY_SHOWED,false);

        if(!showed){
            initPrivacyPolicySummary(false);
        } else {
            mPrivacyPolicySummaryRl.setVisibility(View.GONE);
            startSplashAnimation();
        }
    }

    private void initPrivacyPolicySummary(boolean showOneBtn){
        if(showOneBtn){
            mCancelBtn.setVisibility(View.GONE);
            mConfirmBtn.setText(getString(R.string.i_know));
        }
        mPrivacyPolicySummaryRl.setVisibility(View.VISIBLE);
        mPolicyContentTv.setMovementMethod(LinkMovementMethod.getInstance());
        String policyStr=getString(R.string.protection_guidelines_content);
        SpannableString sp=new SpannableString(policyStr);
        ClickableSpan privacyClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //跳转隐私协议
               // JumpManager.gotoPrivacyWebView(SplashActivity.this);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.color_FFEBB25D));
                ds.setUnderlineText(true);
            }
        };
        String policyDigest = getString(R.string.privacy_digest);
        int linkStart = policyStr.indexOf(policyDigest);
        sp.setSpan(privacyClickableSpan, linkStart, linkStart + policyDigest.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan serviceClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //跳转隐私协议
                //JumpManager.gotoServiceWebView(SplashActivity.this);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.color_FFEBB25D));
                ds.setUnderlineText(true);
            }
        };
        String serviceDigest = getString(R.string.service_digest);
        linkStart = policyStr.indexOf(serviceDigest);
        sp.setSpan(serviceClickableSpan, linkStart, linkStart + serviceDigest.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mPolicyContentTv.setText(sp);
    }

}
