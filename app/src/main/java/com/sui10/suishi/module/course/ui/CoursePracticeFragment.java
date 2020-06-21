package com.sui10.suishi.module.course.ui;

import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.base.BaseFragment;
import com.sui10.suishi.R;

import butterknife.BindView;

public class CoursePracticeFragment extends BaseFragment {
//    @BindView(R.id.empty_tv)
//    TextView mEmptyTv;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_practice;
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
