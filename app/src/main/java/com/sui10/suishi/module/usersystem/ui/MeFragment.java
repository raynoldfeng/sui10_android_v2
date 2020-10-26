package com.sui10.suishi.module.usersystem.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.utils.ToastUtils;
import com.sui10.commonlib.ui.view.base.BaseFragment;
import com.sui10.suishi.R;
import com.sui10.suishi.common.ui.JumpManager;
import com.sui10.suishi.manager.UserManager;

import butterknife.BindView;
import butterknife.OnClick;

public class MeFragment extends BaseFragment {

    @BindView(R.id.menu_profile_desc_tv)
    TextView mDescTv;
    @BindView(R.id.menu_profile_nick_tv)
    TextView mNickTv;
    @BindView(R.id.menu_profile_avatar_iv)
    ImageView mAvatarTv;
    @BindView(R.id.not_login_tips_tv)
    TextView mNotLoginTipsTv;


    @BindView(R.id.menu_item_user_info)
    RelativeLayout mUserInfoRl;
    @BindView(R.id.menu_item_my_course)
    RelativeLayout mMyCourseRl;
    @BindView(R.id.menu_item_setting)
    RelativeLayout mSettingRl;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_me;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initViewsAndEvents() {
        setTitle(R.string.tab_mine_text);
        if(!UserManager.getInstance().isLogin()){
            mNotLoginTipsTv.setVisibility(View.VISIBLE);
        }else {
            mNotLoginTipsTv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @OnClick(R.id.login_btn)
    public void onLoginBtnClicked() {
        JumpManager.gotoLoginActivity(getActivity());
    }

    @OnClick(R.id.menu_item_user_info)
    public void onMenuUserInfoClicked() {
        checkLoginState();

    }

    @OnClick(R.id.menu_item_my_course)
    public void onMenuMyCourseClicked() {
        checkLoginState();
    }

    @OnClick(R.id.menu_item_setting)
    public void onMenuSettingClicked() {

    }

    private boolean checkLoginState(){
        if(!UserManager.getInstance().isLogin()) {
            ToastUtils.showShort(R.string.please_login_first);
            JumpManager.gotoLoginActivity(getActivity());
            return false;
        }
        return true;
    }


}
