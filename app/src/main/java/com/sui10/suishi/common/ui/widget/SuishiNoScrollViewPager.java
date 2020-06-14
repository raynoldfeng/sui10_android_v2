package com.sui10.suishi.common.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class SuishiNoScrollViewPager extends ViewPager {

    private boolean mCanScroll = false;

    public SuishiNoScrollViewPager(@NonNull Context context){
        super(context);
    }

    public SuishiNoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
    }

    public void setScanScroll(boolean canScroll) {
        this.mCanScroll = canScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mCanScroll) {
            return false;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mCanScroll) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, false);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, false);
    }

}
