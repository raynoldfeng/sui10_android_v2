package com.sui10.commonlib.ui.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sui10.commonlib.R;
import com.sui10.commonlib.config.BuildHelper;
import com.sui10.commonlib.log.LogManager;
import com.sui10.commonlib.ui.manager.BaseViewManager;
import com.sui10.commonlib.ui.manager.RxLifeManager.RxActivityLifeManager;
import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.utils.StatusBarUtils;
import com.sui10.commonlib.ui.view.base.IBaseActivityView;
import com.sui10.commonlib.utils.CommonViewUtils;
import com.sui10.commonlib.utils.DensityUtils;
import com.sui10.commonlib.utils.ToolBarUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 处理
 * 1、进入进出动画
 * 2、创建presenter
 * 3、是否在前台
 * 4、baselayout设置
 * 5、自定义任务栏；沉浸式状态栏
 * 6、自定义是否显示侧边栏
 */

public abstract class BaseActivity<T,P extends BasePresenter<T>> extends NetBaseActivity implements IBaseActivityView {

    private static final String TAG = "BaseActivity";

    protected P mPresenter;
    private volatile boolean mIsInFront = false;
    private View mBaseLayout;
    private ViewGroup mLayoutContaier;
    private FrameLayout mSideMenuContainer;
    private DrawerLayout mDrawerLayout;
    private CustomToolBar mToolBar;
    private String mCurrentFragmentTag;
    private Fragment mCurrentFragment;
    private boolean mHasFragmentInbackStack = false;
    private boolean mSavedInstanceState = false;

    private static final int[] TOP_BAR_ATTRS = new int[]{
            R.attr.common_topBar,    //是否显示顶部栏
            R.attr.common_topBarOverlay,
            R.attr.common_statusBarImmersive  //是否实现沉浸式状态栏
    };

    protected static final int LEFT = 0;
    protected static final int RIGHT = 1;
    protected static final int TOP = 2;
    protected static final int BOTTOM = 3;
    protected static final int SCALE = 4;
    protected static final int FADE = 5;
    @IntDef({LEFT, RIGHT,
            TOP, BOTTOM,SCALE,FADE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TransitionMode {}

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //进入、退出动画
        initPendingTransition();

        //Presenter
        initPresenter(savedInstanceState);

        //状态栏
        initStatusBar();
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        mIsInFront = true;
        mSavedInstanceState = false;
    }

    @Override
    @CallSuper
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    @CallSuper
    protected void onPause() {
        super.onPause();
        mIsInFront = false;
    }

    @Override
    @CallSuper
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void finish() {
        super.finish();
        initPendingTransition();
        BaseViewManager.getInstance().addActivity(this.getClass().getSimpleName(),this);
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        BaseViewManager.getInstance().removeActivity(getCurrentName());
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mSavedInstanceState = true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSavedInstanceState = false;
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(getLayoutInflater().inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this);
        ButterKnife.setDebug(BuildHelper.isDebug());
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        initUILayout(view,params);
        super.setContentView(mBaseLayout,params);
    }

//    @Override
//    public void addContentView(View view, ViewGroup.LayoutParams params) {
//        initUILayout(view,params);
//        super.addContentView(mBaseLayout, params);
//    }

    @Override
    protected boolean needMonitorNetWork() {
        return true;
    }

    private void initUILayout(View view, ViewGroup.LayoutParams params){
        //base layout
        initBaseLayout();

        //抽屉侧边栏
        initSideMenu();

        //标题栏
        initToolBar();

        //初始化baseview内容
        initBaseViewContent(view,params);
    }

    //TODO ...
    @Override
    public void showError(String msg) {

    }

    @Override
    public void showException(String msg) {

    }

    @Override
    public void showNetError() {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void showNoData(String msg) {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void hideEmpty() {

    }

    @Override
    public void hideException() {

    }

    @Override
    public void hideNetWorkError() {

    }

    protected int getOverridePendingTransitionMode() {
        return RIGHT;
    }

    private void initPendingTransition()
    {
        switch (getOverridePendingTransitionMode()) {
            case LEFT:
                overridePendingTransition(R.anim.common_left_in,R.anim.common_left_out);
                break;
            case RIGHT:
                overridePendingTransition(R.anim.common_right_in,R.anim.common_right_out);
                break;
            case TOP:
                overridePendingTransition(R.anim.common_top_in,R.anim.common_top_out);
                break;
            case BOTTOM:
                overridePendingTransition(R.anim.common_bottom_in,R.anim.common_bottom_out);
                break;
            case SCALE:
                overridePendingTransition(R.anim.common_scale_in,R.anim.common_scale_out);
                break;
            case FADE:
                overridePendingTransition(R.anim.common_fade_in,R.anim.common_fade_out);
                break;
        }
    }


    private String getCurrentName()
    {
        return this.getClass().getSimpleName();
    }

    private void initPresenter(Bundle savedInstanceState){
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((T) this);
            loadData();
        }
    }

    public abstract P createPresenter();

    protected abstract void loadData();

    @Override
    public RxActivityLifeManager getRxActivityLifeManager() {
        return mRxActivityLifeManager;
    }

    public boolean isInFront() {
        return mIsInFront;
    }


    private void initBaseLayout() {
        mBaseLayout = (ViewGroup) getLayoutInflater().inflate(
                isEnableSideMenu()? R.layout.common_acitvity_base_ui_layout:R.layout.common_activity_base_ui_no_sidemenu, null);
        mLayoutContaier = (ViewGroup) mBaseLayout.findViewById(R.id.ui_container);
    }

    /**
     * 是否显示侧边栏:默认不显示，需要显示时重写该函数并设为true
     */
    protected  boolean isEnableSideMenu() {
        return false;
    }

    /**
     * 获取侧边栏布局文件，不显示时可以返回0
     */
    protected  int getSideLayout() {
        return 0;
    }

    private void initSideMenu(){
        if(isEnableSideMenu() && mBaseLayout != null){
            mSideMenuContainer = mBaseLayout.findViewById(R.id.ui_sidemenu_container);
            View sideLayout= LayoutInflater.from(this).inflate(getSideLayout(),null);
            int sideMenuWidth= DensityUtils.getDisplayWidth(this)*300/375;
            FrameLayout.LayoutParams sideParam=new FrameLayout.LayoutParams(sideMenuWidth,FrameLayout.LayoutParams.MATCH_PARENT);
            mSideMenuContainer.addView(sideLayout,sideParam);
            mDrawerLayout = mBaseLayout.findViewById(R.id.ui_drawer);
        }
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public boolean isSideMenuOpen(){
        if(null != mDrawerLayout){
            return mDrawerLayout.isDrawerOpen(mSideMenuContainer);
        }
        return false;
    }

    public void showSideMenu(){
        if(null != mDrawerLayout){
            mDrawerLayout.openDrawer(mSideMenuContainer);
        }
    }

    public void hideSideMenu(){
        if(null != mDrawerLayout){
            mDrawerLayout.closeDrawer(mSideMenuContainer);
        }
    }

    /**
     * 设置侧边栏的方向
     * @param gravity Gravity类型，Gravity.start左边，Gravity.end右边
     */
    public void setSideMenuDirection(int gravity){
        if (isEnableSideMenu()&&mSideMenuContainer!=null){
            DrawerLayout.LayoutParams layoutParams= (DrawerLayout.LayoutParams) mSideMenuContainer.getLayoutParams();
            layoutParams.gravity=gravity;
            mSideMenuContainer.setLayoutParams(layoutParams);
            mSideMenuContainer.requestLayout();
        }
    }

    @Override
    public void onBackPressed() {
        if(!CommonViewUtils.isValidActivity(this)){
            if(mHasFragmentInbackStack){
                if(mSavedInstanceState)
                    finish();
                else {
                    //TODO..栈里面的fragment回退之前调用自定义的onback进行回退操作(比如不退出，进行二次确认)
                    try {
                        //会进行fragment回退栈的处理，fragment栈中为空时再进行activity栈的回退
                        //会调用fragment的生命周期函数 onDestory onDestoryView
                          super.onBackPressed();
                        } catch (Exception e) {
                            finish();
                        }
//                    if(getSupportFragmentManager().getBackStackEntryCount() > 0){
//                        getSupportFragmentManager().popBackStackImmediate();
//                    }
//                    else {
//                        try {
//                            super.onBackPressed();
//                        } catch (Exception e) {
//                            finish();
//                        }
//                    }
                }
            }else {
                //TODO...只调用了一个fragment的onback，如果有多个时的处理逻辑
                Fragment fragment = mCurrentFragment == null ? getSupportFragmentManager().findFragmentByTag(mCurrentFragmentTag) : mCurrentFragment;
                if (fragment instanceof BaseFragment) {
                    BaseFragment baseFragment = (BaseFragment) fragment;
                    ((BaseFragment) fragment).onBack();
                }
                try {
                    if(mSavedInstanceState)
                        finish();
                    else
                        super.onBackPressed();
                } catch (Exception e) {
                    finish();
                }
            }
        }
    }

    /**
     * 自定义顶部栏
     */
    private void initToolBar(){
        final TypedArray typedArray = obtainStyledAttributes(TOP_BAR_ATTRS);
        boolean isTopBarShow = typedArray.getBoolean(0, true);
        boolean isTopBarOverlayout = typedArray.getBoolean(1, false);
        typedArray.recycle();

        if (isTopBarShow) {
            ViewStub topViewStub = mBaseLayout.findViewById(R.id.ui_topbar_viewstub);
            mToolBar = (CustomToolBar) topViewStub.inflate();
            mToolBar.getTopLeftImage().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            //设置标题
            mToolBar.getTitleTextView().setText(getTitle());

            //判断是否悬浮顶部栏
            if (!isTopBarOverlayout) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mLayoutContaier.getLayoutParams();
                lp.addRule(RelativeLayout.BELOW, R.id.tool_bar_layout);
                mLayoutContaier.setLayoutParams(lp);
            }
            ToolBarUtils.adjustStatusBar(mToolBar,this);
        }
    }

    /**
     * 状态栏
     */
    private void initStatusBar() {
        final TypedArray a = obtainStyledAttributes(TOP_BAR_ATTRS);
        boolean isStatusBarImmersive = a.getBoolean(2, true);
        if(isStatusBarImmersive)
            StatusBarUtils.immersive(this);
    }


    private void initBaseViewContent(View view, ViewGroup.LayoutParams params){
        if (mBaseLayout != null) {
            mLayoutContaier.removeAllViews();
            mLayoutContaier.addView(view, params);
        }else{
            mBaseLayout  = view;
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mToolBar != null) {
            mToolBar.getTitleTextView().setText(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (mToolBar != null) {
            mToolBar.getTitleTextView().setText(titleId);
        }
    }

    public void setTitleTextColor(int colorId) {

        if (mToolBar != null) {
            mToolBar.getTitleTextView().setTextColor(this.getResources().getColor(colorId));
        }

    }

    public void setLeftTitle(CharSequence title) {
        if (mToolBar != null) {
            mToolBar.getTopLeftTextView().setText(title);
        }
    }

    public void setLeftTitle(int titleId) {
        if (mToolBar != null) {
            mToolBar.getTopLeftTextView().setText(titleId);
        }
    }

    public void setTopLeftImage(int imageId) {
        if (mToolBar != null) {
            mToolBar.getTopLeftImage().setImageResource(imageId);
        }
    }

    /**
     * 设置右上角的View
     *
     * @param trView
     */
    public void setTopRightView(View trView) {
        setTopRightView(trView, null);
    }

    public void setTopActionBar1Image(int imageId)
    {
        if(mToolBar != null)
            mToolBar.getActionBtn1().setImageResource(imageId);
    }

    public void setTopActionBar2Image(int imageId)
    {
        if(mToolBar != null)
            mToolBar.getActionBtn2().setImageResource(imageId);
    }

    /**
     * 设置右上角的View
     *
     * @param trView
     */
    public void setTopRightView(View trView, ViewGroup.LayoutParams lp) {
        if (trView != null && mToolBar != null) {
            if (lp == null) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                lp = layoutParams;
            }
            mToolBar.getTopRightLayout().removeAllViews();
            mToolBar.getTopRightLayout().addView(trView, lp);
        }
    }

    /**
     * 自定义设置顶部界面
     */
    public void setTopCenterView(View view) {
        setTopCenterView(view, null);
    }

    /**
     * 自定义设置顶部界面
     */
    public void setTopCenterView(View view, ViewGroup.LayoutParams lp) {
        if (view != null && mToolBar != null) {
            if (lp == null) {
                lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            mToolBar.getTopCenterLayout().removeAllViews();
            mToolBar.getTopCenterLayout().addView(view, lp);
        }
    }

    /**
     * 顶部ActionBar是否显示返回键
     */
    public void setDisplayGoBackView(boolean enabled) {
        if (mToolBar != null) {
            mToolBar.getTopLeftImage().setVisibility(enabled ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 左边标题，默认不显示
     * @param enabled
     */
    public void setDisplayLeftTitle(boolean enabled) {
        if (mToolBar != null) {
            mToolBar.getTopLeftTextView().setVisibility(enabled ? View.VISIBLE : View.GONE);
        }
    }


    /**
     * Fragment相关处理
     */
    protected final void addFragment(@IdRes int containerViewId, @NonNull Fragment fragment, String tag, boolean addToBack) {
        try {
            FragmentManager manager = getSupportFragmentManager();
            if (manager != null && findFragmentByTag(tag) == null) {
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                if(fragmentTransaction != null)
                {
                    fragmentTransaction.add(containerViewId, fragment, tag);

                    if(addToBack){
                        fragmentTransaction.addToBackStack(tag);
                        if(!mHasFragmentInbackStack)
                            mHasFragmentInbackStack = addToBack;
                    }
                    fragmentTransaction.commitAllowingStateLoss();
                    mCurrentFragment = fragment;
                    mCurrentFragmentTag = tag;
                }
            }
        } catch (Exception ex) {
            LogManager.e(TAG, "add fragment failure  %s", tag);
        }
    }


    protected <T extends Fragment> T findFragmentByTag(String tag) {
        try {
            Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(tag);
            if (fragmentByTag != null) {
                return (T) fragmentByTag;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected boolean showFragment(String tag) {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(tag);
            if (fragmentByTag != null) {
                transaction.show(fragmentByTag);
                transaction.commitAllowingStateLoss();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected void hideFragment(String tag) {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(tag);
            if (fragmentByTag != null) {
                transaction.hide(fragmentByTag);
            }
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchFragment(String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> list = manager.getFragments();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTag().equals(tag)) {
                transaction.show(list.get(i));
            } else {
                transaction.hide((list.get(i)));
            }
        }
        transaction.commitNowAllowingStateLoss();
    }

}
