package com.sui10.suishi.module.course.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sui10.commonlib.log.LogManager;
import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.widget.BaseFragment;
import com.sui10.commonlib.utils.DensityUtils;
import com.sui10.suishi.R;
import com.sui10.suishi.common.ui.JumpManager;
import com.sui10.suishi.common.ui.adapter.BaseAdapter;
import com.sui10.suishi.module.course.adapter.ProCourseListAdapter;
import com.sui10.suishi.module.course.bean.CourseBean;
import com.sui10.suishi.module.course.mvp.IProfessionalCourseView;
import com.sui10.suishi.module.course.mvp.ProfessionalCoursePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfessionalCourseFragment extends BaseFragment implements IProfessionalCourseView {

    //TODO...上拉加载，下拉刷新
    private static final String TAG = "ProfessionalCourseFragment";

    private LinearLayoutManager mLayoutManager;
    private ProCourseListAdapter mProCourseAdater;

    @BindView(R.id.profession_course_rv)
    RecyclerView mProCourseRv;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_professional_course;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public BasePresenter createPresenter() {
        return new ProfessionalCoursePresenter();
    }

    @Override
    public void initViewsAndEvents() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mProCourseRv.setLayoutManager(mLayoutManager);
        mProCourseAdater = new ProCourseListAdapter();
        mProCourseRv.setAdapter(mProCourseAdater);
        //TODO... 第一个item间距单独处理
//        mProCourseRv.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                outRect.top = DensityUtils.dip2px(getActivity(),39);
//            }
//        });
        mProCourseAdater.setItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object item, int position) {
                onItemCliked((CourseBean)item);
            }
        });
        initData();
    }

    private void initData(){
        ((ProfessionalCoursePresenter)mPresenter).getProfessionalCourse();
    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @OnClick(R.id.learn_course_tv)
    public void onLearnMbaCourseClicked(){

    }

    @Override
    public void onProCourseListGetSucess(List<CourseBean> courseBeanList) {
        mProCourseAdater.setData(courseBeanList);
    }

    private void onItemCliked(CourseBean bean){
        JumpManager.gotoProCourseDetailActivity(getActivity(),bean);
    }
}
