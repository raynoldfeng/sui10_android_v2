package com.sui10.suishi.module.usersystem.ui;

import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.base.BaseFragment;
import com.sui10.suishi.R;

public class MeFragment extends BaseFragment {

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
    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }
}
