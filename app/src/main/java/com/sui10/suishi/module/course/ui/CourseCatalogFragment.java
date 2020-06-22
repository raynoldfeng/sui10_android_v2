package com.sui10.suishi.module.course.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.base.BaseFragment;
import com.sui10.suishi.R;

import butterknife.BindView;

public class CourseCatalogFragment extends BaseFragment {

    private LinearLayoutManager mLayoutManager;

    @BindView(R.id.course_catalog_data_rv)
    RecyclerView mCourseCatalogRv;

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
        mLayoutManager=new LinearLayoutManager(getContext());
        mCourseCatalogRv.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }
}
