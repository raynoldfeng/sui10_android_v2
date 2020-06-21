package com.sui10.suishi.module.course.ui;

import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.base.BaseFragment;
import com.sui10.suishi.R;

public class CourseCatalogFragment extends BaseFragment {
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_catalog;
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

    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }
}
