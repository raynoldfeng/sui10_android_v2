package com.sui10.suishi.module.course.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.base.BaseFragment;
import com.sui10.suishi.R;

import butterknife.BindView;

public class CoursePracticeFragment extends BaseFragment {

    private LinearLayoutManager mLayoutManager;
//    @BindView(R.id.empty_tv)
//    TextView mEmptyTv;

    @BindView(R.id.practice_data_rv)
    RecyclerView mPracticDataRv;

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
        mLayoutManager=new LinearLayoutManager(getContext());
        mPracticDataRv.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }
}
