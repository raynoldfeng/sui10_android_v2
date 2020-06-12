package com.comic.yoyo.module.main.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.comic.commonlib.log.LogManager;
import com.comic.commonlib.ui.presenter.BasePresenter;
import com.comic.commonlib.ui.view.widget.BaseFragment;
import com.comic.yoyo.R;

public class HomeFragment extends BaseFragment {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
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
        setTitle(R.string.tab_home_text);
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
}
