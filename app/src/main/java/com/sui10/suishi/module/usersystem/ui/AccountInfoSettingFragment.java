package com.sui10.suishi.module.usersystem.ui;

import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.base.BaseFragment;
import com.sui10.suishi.R;

public class AccountInfoSettingFragment extends BaseFragment {


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_account_setting;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initViewsAndEvents() {
        setTitle(R.string.main_menu_user_info);
    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }
}
