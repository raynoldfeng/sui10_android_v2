package com.sui10.commonlib.ui.view.widget.scroll;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;


import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sui10.commonlib.R;
import com.sui10.commonlib.log.LogManager;
import com.sui10.commonlib.ui.utils.StatusBarUtils;

public class StickyNavLayout extends LinearLayout implements NestedScrollingParent {
    private static final String TAG = "StickyNavLayout";
    boolean debugScroll = true;
    private int predy;

    //private View mTopBar;
    private int mTopbarHeight;
    private View mHeadView;
    private View mNav;
    private View mContentLayout;
    private int mTopViewHeight;
    private OverScroller mScroller;
    public float mLastY;

    public StickyNavLayout(Context context){
        this(context,null);
    }

    public StickyNavLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public StickyNavLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        LogManager.d(TAG, "onStartNestedScroll:" + nestedScrollAxes);
        
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        LogManager.d(TAG, "onNestedScrollAccepted:" + nestedScrollAxes);
        isScroll = true;
    }

    public boolean isScroll;

    @Override
    public void onStopNestedScroll(View target) {
        isScroll = false;
        LogManager.d(TAG, "onStopNestedScroll");
        if (listener != null) {
            listener.onStopScroll();
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        LogManager.d(TAG, "onNestedScroll dyConsumed:" + dyConsumed + "|dyUnconsumed:" + dyUnconsumed);
        measuredHeight();
    }

    private int preScrollY;
    private boolean shouldInterceptFlip=false;
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        int currentScrollY=getScrollY();
        if(preScrollY==0&&currentScrollY==mTopViewHeight){
            //突然快速滑动的情况
            shouldInterceptFlip=true;
        }
        preScrollY=currentScrollY;

        boolean hiddenTop = dy > 0 && preScrollY < mTopViewHeight;
        this.predy = dy;
        boolean childCanScroll;
        if (target == null) {
            // java.lang.NullPointerException:
            // Attempt to invoke virtual method 'boolean android.support.v7.widget.RecyclerView$i.canScrollVertically()' on a null object reference
            return;
        }
        if (target instanceof RecyclerView && ((RecyclerView) target).getLayoutManager() == null) {
            return;
        }
        // direction > 0 判断是否能上拉， direction < 0判断是否能下拉 。
        try {
            if (dy > 0) {
                childCanScroll = ViewCompat.canScrollVertically(target, 1);
            } else {
                childCanScroll = ViewCompat.canScrollVertically(target, -1);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return;
        }

        boolean showTop = dy < 0 && preScrollY > 0 && !childCanScroll;
        if (debugScroll) {
            LogManager.d(TAG, "onNestedPreScroll 当次滑动（正数向上）:" + dy
                    + "\nmTopViewHeight:" + mTopViewHeight
                    + "\nhiddenTop:" + hiddenTop
                    + "\nshowTop:" + showTop
                    + "\ngetScrollY():" + preScrollY
                    +"\ntop: "+getTop()
                    +"\nY: "+getY()
                    +"\nTranslationY: "+getTranslationY()

            );
        }
        if(null != listener){
            int direction=0;
            if(dy>=1){
                direction=1;
            }
            if(dy<=-1){
                direction=-1;
            }
            listener.onScrollStateChange(!hiddenTop&&!showTop&&preScrollY==0,direction);
        }
        if (hiddenTop || showTop) {//当前没有在下拉放大过程中
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        LogManager.d(TAG, "onNestedFling "
                + "\nvelocityY:" + velocityY
                + "\nconsumed:" + consumed
        );

        return true;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        boolean realData = true;
        if ((predy > 0 && velocityY < 0) || (predy < 0 && velocityY > 0)) {//加速度方向与滑动方向不一致，异常数据
            LogManager.d(TAG, "onNestedPreFling --- 异常数据 velocityY:" + velocityY + " --- predy:" + predy);
            velocityY = -velocityY;
            realData = false;
        }
        //拦截的2种情况：还没滑动到头部，上滑。2.子类下滑到顶，继续下滑,并且父view没到0
        boolean action1 = velocityY > 0 && getScrollY() < mTopViewHeight;//上滑
        boolean childHasOnTop = false;

        if (target instanceof RecyclerView && ((RecyclerView) target).getLayoutManager() == null) {
            childHasOnTop = false;
        } else {
            try {
                childHasOnTop = !ViewCompat.canScrollVertically(target, -1);//子view已经滑到顶
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        boolean action2 = velocityY < 0 && getScrollY() > 0 && childHasOnTop;//下滑

        //当快速上滑时，调用scrollBy,这时获取getscrolly有返回，但是view却没有发生变化，这种情况需要拦截
        boolean action3=velocityY > 0&&(shouldInterceptFlip||(preScrollY==0&&getScrollY()==mTopViewHeight))&&getScrollY()==mTopViewHeight;
        LogManager.d(TAG, "onNestedPreFling --- "
                    + "\nY轴加速度:" + velocityY
                    + "\nmTopViewHeight:" + mTopViewHeight
                    + "\ngetScrollY():" + getScrollY()
                    + "\naction1:" + action1
                    + "\naction2:" + action2
                    + "\naction3:" + action3
                    +"\nrealData: "+realData
            );

        if (action1 || action2 || action3) {
            if(action3){
                shouldInterceptFlip=false;
            }
            return true;
        } else {
            if (realData) {
                return false;
            } else {
                return true;//如果是假数据，也拦截掉不发给子类
            }
        }
    }

    @Override
    public int getNestedScrollAxes() {
        LogManager.d(TAG, "getNestedScrollAxes");
        return ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeadView = findViewById(R.id.scroll_head);
        //mTopBar=findViewById(R.id.common_top_bar);
        mTopbarHeight= StatusBarUtils.getStatusBarHeight(getContext());
        mNav = findViewById(R.id.scroll_nav);
        mContentLayout =findViewById(R.id.scroll_content);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        ViewGroup.LayoutParams params = mContentLayout.getLayoutParams();
        params.height = getMeasuredHeight()-mNav.getHeight()- mTopbarHeight;
        setMeasuredDimension(getMeasuredWidth(), mHeadView.getMeasuredHeight() + mContentLayout.getMeasuredHeight()+mNav.getHeight());

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        measuredHeight();
    }

    private void measuredHeight() {
        mTopViewHeight = mHeadView.getMeasuredHeight() - mTopbarHeight;
        LogManager.d(TAG, "onSizeChanged mTopViewHeight:" + mTopViewHeight);
    }

    public void resetHeadHeight(int height){
       int oldHeight= mHeadView.getMeasuredHeight();
        LogManager.d(TAG, "resetHeadHeight oldHeigth:" + oldHeight+" newHeight: "+height);
       if(height!= oldHeight){
           ViewGroup.LayoutParams param=mHeadView.getLayoutParams();
           param.height=height;
           mHeadView.setLayoutParams(param);
           mHeadView.requestLayout();
           requestLayout();
       }
    }


    public void fling(int velocityY) {
        LogManager.d(TAG, "fling: getScrollY: "+getScrollY());
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    public void setScrollListener(StickyScrollListener listener) {
        this.listener = listener;
    }

    StickyScrollListener listener;

    public interface StickyScrollListener {
        void scrollTo(int x, int y);

        /**
         *
         * @param isEnd 头部是否已经滑动到底
         * @param direction 滑动方向：1: 表示向上滑动 0:静止 -1：向下
         */
        void onScrollStateChange(boolean isEnd,int direction);
        void onStopScroll();
    }

    private boolean isClosed;
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public void scrollTo(int x, int y) {
        LogManager.d(TAG, "scrollTo y:" + y);
        if (listener != null) {
            listener.scrollTo(x, y);
        }
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
            isClosed=true;
        }else {
            isClosed =false;
        }
        int scrolly=getScrollY();
        LogManager.d(TAG, "scrolly:" + scrolly);
        mLastY = y;
        if (y != scrolly) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    public int getTopViewHeight() {
        return mTopViewHeight;
    }

    public int y = 0;

}

