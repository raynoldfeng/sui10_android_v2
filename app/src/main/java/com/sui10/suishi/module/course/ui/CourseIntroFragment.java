package com.sui10.suishi.module.course.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.base.BaseFragment;
import com.sui10.commonlib.utils.DensityUtils;
import com.sui10.commonlib.utils.ResourceUtils;
import com.sui10.suishi.R;
import com.sui10.suishi.module.course.adapter.CourseImgIntroAdapter;
import com.sui10.suishi.module.course.bean.CourseBean;

import java.util.List;

import butterknife.BindView;

public class CourseIntroFragment extends BaseFragment {
    private LinearLayoutManager mLayoutManager;
    private CourseImgIntroAdapter mAdapter;
    private CourseBean mCourseBean;

    @BindView(R.id.course_intro_data_rv)
    RecyclerView mCourseIntroRv;
    @BindView(R.id.text_intro)
    TextView mTextIntro;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_intro;
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
        mCourseIntroRv.setLayoutManager(mLayoutManager);
        mCourseIntroRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = DensityUtils.dip2px(getActivity(),16);
            }
        });

        mAdapter = new CourseImgIntroAdapter();
        mCourseIntroRv.setAdapter(mAdapter);
        initData();
    }

    private void initData(){
        Bundle bundle = getArguments();
        if(bundle != null){
            mCourseBean = (CourseBean)bundle.getSerializable(ProfessionalCourseDetailActivity.COURSE_BEAN_KEY);
            List<String> imgIntros = mCourseBean.getImgIntro();
            String textIntro = mCourseBean.getDesc();
            if(imgIntros != null && !imgIntros.isEmpty()){
                mAdapter.setData(imgIntros);
            }else if(!TextUtils.isEmpty(textIntro)){
                mTextIntro.setText(textIntro);
                mTextIntro.setVisibility(View.VISIBLE);
            } else {
                showNoData(ResourceUtils.getString(R.string.prepareing));
            }
        }
    }


    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }
}
