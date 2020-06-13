package com.sui10.commonlib.ui.view.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sui10.commonlib.R;
import com.sui10.commonlib.eventbus.EventBusManager;
import com.sui10.commonlib.log.LogManager;
import com.sui10.commonlib.ui.manager.RxLifeManager.RxFragmentLifeManager;
import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.base.IBaseFragmentView;
import com.sui10.commonlib.utils.CommonViewUtils;
import com.sui10.commonlib.utils.ToolBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 处理
 * 1、attachView
 * 2、创建presenter
 * 3、顶部栏设置：fragment中需要include common_toolbar
 * 4、顶部栏回退：默认调用activity的onBackPress();添加到系统的回退栈时会调用系统的回退栈，否则会回调回onBack()
 */

public abstract class BaseFragment<T, P extends BasePresenter<T>> extends NetBaseFragment implements IBaseFragmentView {

    private static final String TAG = "BaseFragment";
    public static final String KEY_FRAG_TITLE ="key_frag_title";
    protected P mPresenter;

    private CustomToolBar mCustomToolBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if(mPresenter!=null) {
            mPresenter.attachView((T) this);
        }
        LogManager.d(TAG,"onCreate");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (isBindEventBusHere()) {
            EventBusManager.register(this);
        }
        initToolBarViewAndEvents(view);
        LogManager.d(TAG,"onViewCreated");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogManager.d(TAG,"onCreateView");
        if (getContentViewLayoutID() != 0) {
            View rootView = inflater.inflate(getContentViewLayoutID(), container, false);
            return rootView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogManager.d(TAG,"onActivityCreated");
        initViewsAndEvents();
    }

    @Override
    public void onDestroy() {
        LogManager.d(TAG,"onDestroy");
        if (isBindEventBusHere()) {
            EventBusManager.unregister(this);
        }
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter!=null) {
            mPresenter.detachView();
        }
    }

    @Override
    protected boolean needMonitorNetWork() {
        return true;
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

    @Override
    public RxFragmentLifeManager getRxFragmentLifeManager() {
        return mRxFragmentLifeManager;
    }

    /**
     * 获取当前布局文件ID
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    private void initToolBarViewAndEvents(View view){
        mCustomToolBar = view.findViewById(R.id.tool_bar_layout);
        adjustStatusBar();
        if(mCustomToolBar != null){
            mCustomToolBar.getTopLeftImage().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    private void onBackPressed(){
        Activity hostActivty = getActivity();
        if(!CommonViewUtils.isValidActivity(hostActivty)){
            hostActivty.onBackPressed();
        }
    }

    public void onBack(){
        LogManager.d(TAG,"@@@@onBack");
    }

    /**
     *绑定EventBus
     * @return
     */
    protected abstract boolean isBindEventBusHere();



    public abstract P createPresenter();

    public abstract void initViewsAndEvents();


    private void adjustStatusBar(){
        Activity activity = getActivity();
        if (mCustomToolBar != null && activity != null) {
            ToolBarUtils.adjustStatusBar(mCustomToolBar, activity);
        }
    }

    public void setTitle(CharSequence title) {
        if (mCustomToolBar != null) {
            mCustomToolBar.getTitleTextView().setText(title);
        }
    }

    public void setTitle(int titleId) {
        if (mCustomToolBar != null) {
            mCustomToolBar.getTitleTextView().setText(titleId);
        }
    }

    public void setTitleTextColor(int colorId) {

        if (mCustomToolBar != null) {
            mCustomToolBar.getTitleTextView().setTextColor(this.getResources().getColor(colorId));
        }

    }

    public void setLeftTitle(CharSequence title) {
        if (mCustomToolBar != null) {
            mCustomToolBar.getTopLeftTextView().setText(title);
        }
    }

    public void setLeftTitle(int titleId) {
        if (mCustomToolBar != null) {
            mCustomToolBar.getTopLeftTextView().setText(titleId);
        }
    }

    public void setTopLeftImage(int imageId) {
        if (mCustomToolBar != null) {
            mCustomToolBar.getTopLeftImage().setImageResource(imageId);
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
        if(mCustomToolBar != null)
            mCustomToolBar.getActionBtn1().setImageResource(imageId);
    }

    public void setTopActionBar2Image(int imageId)
    {
        if(mCustomToolBar != null)
            mCustomToolBar.getActionBtn2().setImageResource(imageId);
    }

    /**
     * 设置右上角的View
     *
     * @param trView
     */
    public void setTopRightView(View trView, ViewGroup.LayoutParams lp) {
        if (trView != null && mCustomToolBar != null) {
            if (lp == null) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                lp = layoutParams;
            }
            mCustomToolBar.getTopRightLayout().removeAllViews();
            mCustomToolBar.getTopRightLayout().addView(trView, lp);
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
        if (view != null && mCustomToolBar != null) {
            if (lp == null) {
                lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            mCustomToolBar.getTopCenterLayout().removeAllViews();
            mCustomToolBar.getTopCenterLayout().addView(view, lp);
        }
    }

    /**
     * 顶部ActionBar是否显示返回键
     */
    public void setDisplayGoBackView(boolean enabled) {
        if (mCustomToolBar != null) {
            mCustomToolBar.getTopLeftImage().setVisibility(enabled ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 左边标题，默认不显示
     * @param enabled
     */
    public void setDisplayLeftTitle(boolean enabled) {
        if (mCustomToolBar != null) {
            mCustomToolBar.getTopLeftTextView().setVisibility(enabled ? View.VISIBLE : View.GONE);
        }
    }

}
