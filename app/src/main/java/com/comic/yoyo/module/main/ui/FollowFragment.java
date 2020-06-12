package com.comic.yoyo.module.main.ui;

import com.comic.commonlib.ui.presenter.BasePresenter;
import com.comic.commonlib.ui.view.widget.BaseFragment;
import com.comic.yoyo.R;

public class FollowFragment extends BaseFragment {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_follow;
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
        setTitle(R.string.tab_fun_text);
    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }
}
