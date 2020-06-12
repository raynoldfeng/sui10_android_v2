package com.comic.yoyo.module.main.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.comic.commonlib.ui.presenter.BasePresenter;
import com.comic.commonlib.ui.view.widget.BaseActivity;
import com.comic.yoyo.R;
import com.comic.yoyo.base.constant.HomeConstant;
import com.comic.yoyo.base.ui.ComicNoScrollViewPager;
import com.comic.yoyo.module.main.adapter.MainViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private MainViewPagerAdapter mMainViewPagerAdapter;
    private long mLastKeyBackTime = 0;
    private Toast mBackToast;
    private final int TOAST_SHOW_TIME = 2000;

    @BindView(R.id.main_pager)
    ComicNoScrollViewPager mMainViewPager;
    @BindView(R.id.bottom_tab)
    TabLayout mBottomTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewAndEvent();
    }


    private void initViewAndEvent(){

        mMainViewPager.setScanScroll(false);
        mMainViewPager.setOffscreenPageLimit(3);
        mMainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBottomTab));
        mMainViewPager.setCurrentItem(HomeConstant.PAGE_INDEX_MINE);
        mMainViewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager()));

        mBottomTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() < mMainViewPager.getChildCount()) {
                    mMainViewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mLastKeyBackTime) > TOAST_SHOW_TIME) {
            if (mBackToast != null) {
                mBackToast.cancel();
            }
            mBackToast = Toast.makeText(this,R.string.click_again_and_exit, Toast.LENGTH_LONG);
            mBackToast.show();
            mLastKeyBackTime = System.currentTimeMillis();
        } else {
            mLastKeyBackTime=0;
            super.onBackPressed();
        }
    }

    }
