package com.sui10.suishi.module.course.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.sui10.commonlib.log.LogManager;
import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.utils.StatusBarUtils;
import com.sui10.commonlib.ui.utils.ToolBarUtils;
import com.sui10.commonlib.ui.view.base.BaseFragment;
import com.sui10.commonlib.ui.view.base.CustomToolBar;
import com.sui10.commonlib.ui.view.widget.scroll.StickyNavLayout;
import com.sui10.commonlib.ui.view.widget.viewpage.SwipeTabView;
import com.sui10.commonlib.ui.view.widget.viewpage.SwipeViewPage;
import com.sui10.commonlib.utils.DensityUtils;
import com.sui10.commonlib.utils.ResourceUtils;
import com.sui10.suishi.R;
import com.sui10.suishi.common.ui.adapter.BaseAdapter;
import com.sui10.suishi.common.ui.entity.FragClassifyEntity;
import com.sui10.suishi.module.course.adapter.OpenCoursesPagerAdapter;
import com.sui10.suishi.module.course.adapter.OpenCourseTagsAdapter;
import com.sui10.suishi.module.course.adapter.ProCourseDetailPagerAdapter;
import com.sui10.suishi.module.course.bean.CourseBean;
import com.sui10.suishi.module.course.bean.OpenCourseTagsBean;
import com.sui10.suishi.module.course.mvp.IOpenCourseView;
import com.sui10.suishi.module.course.mvp.IProfessionalCourseView;
import com.sui10.suishi.module.course.mvp.OpenCoursePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OpenCourseFragment extends BaseFragment implements IOpenCourseView, ViewPager.OnPageChangeListener{
    private static String TAG = "OpenCourseFragment";

    @BindView(R.id.layout_open_course)
    StickyNavLayout mStickyNavLayout;
    @BindView(R.id.scroll_content)
    SwipeViewPage mSwipeViewPage;
    @BindView(R.id.common_tool_bar)
    CustomToolBar mTopBar;
    @BindView(R.id.tag_list_rv)
    RecyclerView mTagsRv;

    private float mAlpha=1;
    private FragmentManager mFragmentManager;
    private OpenCoursesPagerAdapter mPagerAdapter;
    private OpenCourseTagsAdapter mTagsAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_open_course;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public BasePresenter createPresenter() {
        return new OpenCoursePresenter();
    }

    @Override
    public void initViewsAndEvents() {
        initView();
        initData();
    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView(){
        initTopBar();
        mStickyNavLayout.setScrollListener(new StickyNavLayout.StickyScrollListener() {
            @Override
            public void scrollTo(int x, int y) {
                int maxheight=mStickyNavLayout.getTopViewHeight();
                if(y<=0){
                    y=0;
                }
                if(y>=maxheight){
                    y=maxheight;
                }
                //向上滑动，Y递增
                mTopBar.setBackgroundColor(ResourceUtils.getColor(R.color.white));
                mAlpha = 1 - (float) y / maxheight;
                float alphaReverse=1-mAlpha;

                mTopBar.getBackground().setAlpha((int)(255*alphaReverse));
                mTopBar.getActionBtn2().setAlpha(alphaReverse);
                mTopBar.getTitleTextView().setAlpha(alphaReverse);
            }

            @Override
            public void onScrollStateChange(boolean isEnd, int direction) {
//                updateScrollState(isEnd);
            }

            @Override
            public void onStopScroll() {
            }
        });
        mTagsAdapter = new OpenCourseTagsAdapter();
        mTagsAdapter.setItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object item, int position) {
                mSwipeViewPage.setCurrentItem(position);
                mTagsAdapter.setCurTag(position);
            }
        });
        mLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        mTagsRv.setLayoutManager(mLayoutManager);
        mTagsRv.setAdapter(mTagsAdapter);
        mTagsRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = DensityUtils.dip2px(getActivity(),30);
            }
        });

        mFragmentManager = getFragmentManager();
        mPagerAdapter = new OpenCoursesPagerAdapter(getActivity(),mFragmentManager);
        mSwipeViewPage.setOffscreenPageLimit(2);
        mSwipeViewPage.setAdapter(mPagerAdapter);
        mSwipeViewPage.setOnPageChangeListener(this);
    }

    private void initTopBar(){
        setTitle(R.string.tab_open_course_text);
        setTopActionBar2Image(R.drawable.ic_search_black);
        mTopBar.getTopLeftImage().setVisibility(View.GONE);
        mTopBar.getActionBtn2().setVisibility(View.VISIBLE);
        mTopBar.getActionBtn2().setAlpha(0.0f);
        mTopBar.getTitleTextView().setAlpha(0);
    }

    private void initData(){
        if(mPresenter != null)
            ((OpenCoursePresenter)mPresenter).reqCourseTags();
    }

    private void updateCourseTagsView(List<String> tagsName, List<FragClassifyEntity> fragClassifyEntities){
//        mSwipeTabView.setTabIndicatorVisible(true);
//        mSwipeTabView.setTabArrays(tagsName);
//        mSwipeTabView.setIndicatorWidth(DensityUtils.dip2px(getActivity(),20));
//        mSwipeTabView.setOnTabSelectedListener(new SwipeTabView.OnTabSelectedListener(){
//            @Override
//            public void onTabSelected(int position) {
//               mSwipeViewPage.setCurrentItem(position);
//            }
//        });
        mTagsAdapter.setData(tagsName);
        mPagerAdapter.setData(fragClassifyEntities);
    }

    @Override
    public void onCourseTagsReqSuccess(List<OpenCourseTagsBean> beans) {
        if(beans != null && !beans.isEmpty()){
            List<FragClassifyEntity> fragClassifyEntities = new ArrayList<>();
            List<String> tagsName = new ArrayList<>();
            for(int i= 0; i<beans.size();i++){
                tagsName.add(beans.get(i).getName());
                Bundle bundle=new Bundle();
                bundle.putInt(PerTagOpenCourseFragment.OPEN_COURSE_TAG_ID,beans.get(i).getId());
                FragClassifyEntity classifyEntity= new FragClassifyEntity();
                classifyEntity.setFragClass(PerTagOpenCourseFragment.class);
                classifyEntity.setBundle(bundle);
                fragClassifyEntities.add(classifyEntity);
            }
            updateCourseTagsView(tagsName,fragClassifyEntities);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //mSwipeTabView.updateIndicatorByCoordinate(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if (position < 0 ) {
            return;
        }
        mTagsAdapter.setCurTag(position);
        mTagsRv.scrollToPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
