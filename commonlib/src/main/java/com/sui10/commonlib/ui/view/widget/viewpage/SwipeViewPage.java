package com.sui10.commonlib.ui.view.widget.viewpage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.core.view.ViewConfigurationCompat;


public class SwipeViewPage extends ViewPagerFixed {

    private float mDownX;

    private SwipeCallback mSwipeCallback;
    private DisallowInterceptCallback mDisallowInterceptCallback;

    private int mTouchSlop;
    private boolean isDisallowScroll;

    public void setDisallowScroll() {
        isDisallowScroll = true;
    }

    public SwipeViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        isDisallowScroll = false;
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context))*3;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float curX = ev.getX();
        if (!isDisallowScroll) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = ev.getX();
                    //L.debug("MotionEvent", "DOWN-------x=" + ev.getX() + ",y=" + ev.getY());
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    float offsetX = curX - mDownX;
                    if (mSwipeCallback != null) {
                        if (offsetX > mTouchSlop
                                && !mSwipeCallback.canLeftSwipe()) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                            if (mDisallowInterceptCallback != null){
                                mDisallowInterceptCallback.requestDisallowInterceptTouchEvent();
                            }
                            return false;
                        } else if (offsetX < -mTouchSlop
                                && !mSwipeCallback.canRightSwipe()) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                            if (mDisallowInterceptCallback != null){
                                mDisallowInterceptCallback.requestDisallowInterceptTouchEvent();
                            }
                            return false;
                        }
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    //L.debug("MotionEvent", "MOVE-------x=" + ev.getX() + ",y=" + ev.getY());
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    getParent().requestDisallowInterceptTouchEvent(false);
                    //L.debug("MotionEvent", "UP-------x=" + ev.getX() + ",y=" + ev.getY());
                    break;
            }
        } else {
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }


    public void registerSwipeCallback(SwipeCallback swipeCallback) {
        mSwipeCallback = swipeCallback;
    }

    public void removeSwipeCallback() {
        mSwipeCallback = null;
    }

    public interface SwipeCallback {
        public boolean canLeftSwipe();

        public boolean canRightSwipe();
    }

    public void registerDisallowInterceptCallback(DisallowInterceptCallback callback){
        this.mDisallowInterceptCallback = callback;
    }

    public void removeDisallowInterceptCallback(){
        mDisallowInterceptCallback = null;
    }

    public interface DisallowInterceptCallback{
        public void requestDisallowInterceptTouchEvent();
    }
}
