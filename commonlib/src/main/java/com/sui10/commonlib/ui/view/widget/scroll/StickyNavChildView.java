package com.sui10.commonlib.ui.view.widget.scroll;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

public class StickyNavChildView extends RelativeLayout implements NestedScrollingChild {

    private NestedScrollingChildHelper nestedScrollingChildHelper;

    private final int[] offset = new int[2];
    private final int[] consumed = new int[2];
    private int lastY;
    private int touchOffsetY = 0;
    private ViewConfiguration viewConfiguration;
    private int minTouchSlop = 10;

    public StickyNavChildView(Context context){
        super(context);
        viewConfiguration = ViewConfiguration.get(context);
        minTouchSlop = viewConfiguration.getScaledTouchSlop();
    }

    public StickyNavChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewConfiguration = ViewConfiguration.get(context);
        minTouchSlop = viewConfiguration.getScaledTouchSlop();
    }


    private NestedScrollingChildHelper getNestedScrollingChildHelper(){
        if(nestedScrollingChildHelper == null){
            nestedScrollingChildHelper = new NestedScrollingChildHelper(this);
            nestedScrollingChildHelper.setNestedScrollingEnabled(true);
        }
        return nestedScrollingChildHelper;
    }



    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getNestedScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getNestedScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getNestedScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        getNestedScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getNestedScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return getNestedScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return getNestedScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getNestedScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getNestedScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = (int) event.getRawY();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                int curY = (int) event.getRawY();
                int offsetY = lastY - curY;
                lastY = curY;
                if(dispatchNestedPreScroll(0,offsetY,consumed,offset)){
                    //do nothing
                }
                break;
            case MotionEvent.ACTION_UP:
                stopNestedScroll();
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            if(getChildCount() != 0) {
                lastY = (int) ev.getRawY();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchOffsetY = (int) ev.getRawY();
                result = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(ev.getRawY() - touchOffsetY)< minTouchSlop){
                    result = false;
                }else {
                    result = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                result = false;
                break;

        }
        return result;
    }
}
