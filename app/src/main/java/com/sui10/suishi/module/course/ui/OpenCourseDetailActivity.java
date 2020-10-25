package com.sui10.suishi.module.course.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.base.BaseActivity;
import com.sui10.commonlib.ui.view.base.CustomToolBar;
import com.sui10.commonlib.ui.view.widget.viewpage.SwipeTabView;
import com.sui10.commonlib.ui.view.widget.viewpage.SwipeViewPage;
import com.sui10.commonlib.utils.DensityUtils;
import com.sui10.commonlib.utils.ResourceUtils;
import com.sui10.suishi.R;
import com.sui10.suishi.common.ui.entity.FragClassifyEntity;
import com.sui10.suishi.module.course.adapter.ProCourseDetailPagerAdapter;
import com.sui10.suishi.module.course.bean.CourseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OpenCourseDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    public static final String COURSE_BEAN_KEY = "course_bean";
    private static final int FRAG_ID_COURSE_INTRO = 1;
    private static final int FRAG_ID_COURSE_CATALOG = FRAG_ID_COURSE_INTRO+1;
    private static final int FRAG_ID_COURSE_INTERACT = FRAG_ID_COURSE_INTRO+2;

    @BindView(R.id.swipe_view)
    SwipeTabView mSwipeTabView;
    @BindView(R.id.scroll_content)
    SwipeViewPage mSwipeViewPage;
    @BindView(R.id.common_tool_bar)
    CustomToolBar mTopBar;

    private String[] mTabTitles = new String[]{"介绍","目录","互动"};
    private ProCourseDetailPagerAdapter mPagerAdapter;
    private FragmentManager mFragmentManager;
    private CourseBean mCourseBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();
        setContentView(R.layout.activity_open_course_detail);
        initView();
        initData();
    }

    private void parseIntent(){
        Intent intent = getIntent();
        mCourseBean = (CourseBean) intent.getSerializableExtra(COURSE_BEAN_KEY);
        if(mCourseBean == null)
            finish();
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    private void initView(){
        mFragmentManager=getSupportFragmentManager();
        mPagerAdapter = new ProCourseDetailPagerAdapter(this,mFragmentManager);
        mPagerAdapter.setData(getChannelPageFragClassify());

        mSwipeTabView.setTabIndicatorVisible(true);
        mSwipeTabView.setTabArrays(mTabTitles);
        mSwipeTabView.setIndicatorWidth(DensityUtils.dip2px(this,40));
        mSwipeTabView.setOnTabSelectedListener(new SwipeTabView.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
                mSwipeViewPage.setCurrentItem(position);
            }
        });

        mSwipeViewPage.setOffscreenPageLimit(2);
        mSwipeViewPage.setAdapter(mPagerAdapter);
        mSwipeViewPage.setOnPageChangeListener(this);

        initTopBar();
    }

    private void initTopBar(){
        mTopBar.getTitleTextView().setVisibility(View.GONE);
        mTopBar.getTopLeftImage().setVisibility(View.VISIBLE);
        mTopBar.getTopLeftImage().setImageResource(R.drawable.ic_back_white);
        mTopBar.getTopLeftTextView().setTextColor(ResourceUtils.getColor(R.color.white));
        mTopBar.getTopLeftTextView().setVisibility(View.VISIBLE);
    }

    private void initData(){
        if(mCourseBean != null){
            mTopBar.getTopLeftTextView().setText(mCourseBean.getName());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mSwipeTabView.updateIndicatorByCoordinate(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if (position < 0 ) {
            return;
        }
        mSwipeTabView.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public List<FragClassifyEntity> getChannelPageFragClassify() {
        List<FragClassifyEntity> list = new ArrayList<>();
        Bundle bundle=new Bundle();
        bundle.putSerializable(COURSE_BEAN_KEY,mCourseBean);

        FragClassifyEntity classifyIntro = new FragClassifyEntity();
        classifyIntro.setId(FRAG_ID_COURSE_INTRO);
        classifyIntro.setFragClass(CourseIntroFragment.class);
        classifyIntro.setBundle(bundle);
        list.add(classifyIntro);

        FragClassifyEntity classifyCatalog= new FragClassifyEntity();
        classifyCatalog.setId(FRAG_ID_COURSE_CATALOG);
        classifyCatalog.setFragClass(CourseCatalogFragment.class);
        classifyCatalog.setBundle(bundle);
        list.add(classifyCatalog);

        FragClassifyEntity classifyPractice= new FragClassifyEntity();
        classifyPractice.setId(FRAG_ID_COURSE_INTERACT);
        classifyPractice.setFragClass(CoursePracticeFragment.class);
        classifyPractice.setBundle(bundle);
        list.add(classifyPractice);

        return list;
    }
}
