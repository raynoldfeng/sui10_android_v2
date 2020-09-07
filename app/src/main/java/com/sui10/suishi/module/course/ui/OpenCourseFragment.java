package com.sui10.suishi.module.course.ui;

import android.view.View;

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
import com.sui10.suishi.module.course.adapter.ProCourseDetailPagerAdapter;

import butterknife.BindView;

public class OpenCourseFragment extends BaseFragment {
    private static String TAG = "OpenCourseFragment";

    @BindView(R.id.layout_open_course)
    StickyNavLayout mStickyNavLayout;
    @BindView(R.id.swipe_view)
    SwipeTabView mSwipeTabView;
    @BindView(R.id.scroll_content)
    SwipeViewPage mSwipeViewPage;
    @BindView(R.id.common_tool_bar)
    CustomToolBar mTopBar;

    private float mAlpha=1;

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
        return null;
    }

    @Override
    public void initViewsAndEvents() {
        initView();
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
        LogManager.d("#####","onDestoryView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogManager.d("#####","onDestroy");
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
//        mFragmentManager=getSupportFragmentManager();
//        mPagerAdapter = new ProCourseDetailPagerAdapter(this,mFragmentManager);
//        mSwipeTabView.setTabIndicatorVisible(true);
//        mSwipeTabView.setTabArrays(mTabTitles);
//        mSwipeTabView.setIndicatorWidth(DensityUtils.dip2px(this,40));
//        mSwipeTabView.setOnTabSelectedListener(new SwipeTabView.OnTabSelectedListener(){
//            @Override
//            public void onTabSelected(int position) {
//                mSwipeViewPage.setCurrentItem(position);
//            }
//        });
//        mSwipeViewPage.setOffscreenPageLimit(2);
//        mSwipeViewPage.setAdapter(mPagerAdapter);
//        mSwipeViewPage.setOnPageChangeListener(this);
//        mPagerAdapter.setData(getChannelPageFragClassify());
    }

    private void initTopBar(){
        setTitle(R.string.tab_open_course_text);
        setTopActionBar2Image(R.drawable.ic_search_black);
        mTopBar.getTopLeftImage().setVisibility(View.GONE);
        mTopBar.getActionBtn2().setVisibility(View.VISIBLE);
        mTopBar.getActionBtn2().setAlpha(0.0f);
        mTopBar.getTitleTextView().setAlpha(0);
    }
}
