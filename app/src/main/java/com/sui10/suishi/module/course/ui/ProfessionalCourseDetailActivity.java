package com.sui10.suishi.module.course.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.utils.StatusBarUtils;
import com.sui10.commonlib.ui.utils.ToastUtils;
import com.sui10.commonlib.ui.utils.ToolBarUtils;
import com.sui10.commonlib.ui.view.base.BaseActivity;
import com.sui10.commonlib.ui.view.base.CustomToolBar;
import com.sui10.commonlib.ui.view.widget.scroll.StickyNavLayout;
import com.sui10.commonlib.ui.view.widget.viewpage.SwipeTabView;
import com.sui10.commonlib.ui.view.widget.viewpage.SwipeViewPage;
import com.sui10.commonlib.utils.DensityUtils;
import com.sui10.commonlib.utils.ResourceUtils;
import com.sui10.suishi.R;
import com.sui10.suishi.common.ui.JumpManager;
import com.sui10.suishi.common.ui.entity.FragClassifyEntity;
import com.sui10.suishi.common.utils.ImageLoadUtils;
import com.sui10.suishi.manager.UserManager;
import com.sui10.suishi.module.course.adapter.ProCourseDetailPagerAdapter;
import com.sui10.suishi.module.course.bean.CourseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfessionalCourseDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    public static final String COURSE_BEAN_KEY = "course_bean";
    private static final int FRAG_ID_COURSE_INTRO = 1;
    private static final int FRAG_ID_COURSE_CATALOG = FRAG_ID_COURSE_INTRO+1;
    private static final int FRAG_ID_COURSE_PRACTICE = FRAG_ID_COURSE_INTRO+2;

    private CourseBean mCourseBean;
    private String[] mTabTitles = new String[]{"课程简介","课程目录","课后练习"};
    private ProCourseDetailPagerAdapter mPagerAdapter;
    private FragmentManager mFragmentManager;
    private float mAlpha=1;
    private Drawable mTopBgDraw;

    @BindView(R.id.common_tool_bar)
    CustomToolBar mTopBar;
    @BindView(R.id.course_price_tv)
    TextView mCoursePriceTv;
    @BindView(R.id.layout_pro_course_detail)
    StickyNavLayout mStickyNavLayout;
    @BindView(R.id.swipe_view)
    SwipeTabView mSwipeTabView;
    @BindView(R.id.scroll_content)
    SwipeViewPage mSwipeViewPage;
    @BindView(R.id.course_cover_iv)
    ImageView mCourseCoverIv;
    @BindView(R.id.course_name_tv)
    TextView mCourseNameTv;
    @BindView(R.id.learend_people_cnt_tv)
    TextView mLearendPeopleCntTv;
    @BindView(R.id.course_bg_iv)
    ImageView mCourseBgIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();
        setContentView(R.layout.activity_professional_course_detail);
        initView();
        initData();
    }

    private void parseIntent(){
        Intent intent = getIntent();
        mCourseBean = (CourseBean) intent.getSerializableExtra(COURSE_BEAN_KEY);
    }

    private void initView(){
        initTopBar();
        mStickyNavLayout.setScrollListener(new StickyNavLayout.StickyScrollListener() {
            @Override
            public void scrollTo(int x, int y) {
                int maxheight=mStickyNavLayout.getTopViewHeight();
                if(y<0){
                    y=0;
                }
                if(y>maxheight){
                    y=maxheight;
                }
                //向上滑动，Y递增
                mAlpha = 1 - (float) y / maxheight;
                float alphaReverse=1-mAlpha;

                if(null != mTopBgDraw){
                    int bgAlpha= (int) (alphaReverse*255);
                    mTopBgDraw.mutate().setAlpha(bgAlpha);
                    mTopBar.setBackground(mTopBgDraw);
                }
                mTopBar.getTopLeftTextView().setAlpha(alphaReverse);
            }

            @Override
            public void onScrollStateChange(boolean isEnd, int direction) {
//                updateScrollState(isEnd);
            }

            @Override
            public void onStopScroll() {

            }
        });
        mFragmentManager=getSupportFragmentManager();
        mPagerAdapter = new ProCourseDetailPagerAdapter(this,mFragmentManager);
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
        mPagerAdapter.setData(getChannelPageFragClassify());
    }

    private void initTopBar(){
        mTopBar.getTitleTextView().setVisibility(View.GONE);
        mTopBar.getTopLeftImage().setVisibility(View.VISIBLE);
        //mTopBar.getTopLeftImage().setImageResource(R.drawable.ic_back_white);
        mTopBar.getTopLeftTextView().setVisibility(View.VISIBLE);
        mTopBar.getTopLeftTextView().setAlpha(0);
    }

    public static List<FragClassifyEntity> getChannelPageFragClassify() {
        List<FragClassifyEntity> list = new ArrayList<>();
        Bundle bundle=new Bundle();

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
        classifyPractice.setId(FRAG_ID_COURSE_PRACTICE);
        classifyPractice.setFragClass(CoursePracticeFragment.class);
        classifyPractice.setBundle(bundle);
        list.add(classifyPractice);

        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    private void initData(){
        if(mCourseBean != null){
            mCoursePriceTv.setText(String.valueOf(mCourseBean.getPrice()));
            mCourseNameTv.setText(mCourseBean.getName());
            mLearendPeopleCntTv.setText(mCourseBean.getWatch()+"人在学");
            ImageLoadUtils.setRoundImgUrlWithRefererHeader(mCourseBean.getCover(),mCourseCoverIv,4);
            ImageLoadUtils.setImgSrcUrlWithRefererHeader(mCourseBean.getCover(),mCourseBgIv);
            mTopBar.getTopLeftTextView().setText(mCourseBean.getName());
            createTopBg();
        }
    }

    private void createTopBg(){
        Bitmap srcBitmap = mCourseBgIv.getDrawingCache();
        if(null != srcBitmap){
            //生成顶部栏的背景
            int desWidth = DensityUtils.getDisplayWidth(this);
            int desHeight = ToolBarUtils.getTopbarHeight(this);

            int height = srcBitmap.getWidth() * desHeight / desWidth;
            Bitmap bgBitmap = Bitmap.createBitmap(srcBitmap.getWidth(),
                    height, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bgBitmap);
            canvas.drawBitmap(srcBitmap, 0, 0, null);

            mTopBgDraw = new BitmapDrawable(bgBitmap);
        }
    }


    @OnClick(R.id.study_now_tv)
    void onStudyNowClicked(){
        if(!UserManager.getInstance().isLogin()){
            ToastUtils.showShort(R.string.please_login_first);
            JumpManager.gotoLoginActivity(this);
        }else {
            //TODO ... 跳至支付页面
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

//    private void updateScrollState(boolean isEnd){
//        if(null != mFragmentManager){
//            List<Fragment> fragments= mFragmentManager.getFragments();
//            if(null != fragments){
//                for(Fragment fragment : fragments){
//                    if(fragment instanceof ChannelBaseFragment){
//                        ((ChannelBaseFragment) fragment).onHeadScrollStateChange(isEnd);
//                    }
//                }
//            }
//        }
//    }

}
