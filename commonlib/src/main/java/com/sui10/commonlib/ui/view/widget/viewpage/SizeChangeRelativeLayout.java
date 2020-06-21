package com.sui10.commonlib.ui.view.widget.viewpage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SizeChangeRelativeLayout extends RelativeLayout {

    private SizeChangeListener mSizeChangeListener;

    public SizeChangeRelativeLayout(Context context) {
        super(context);
    }

    public SizeChangeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SizeChangeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setSizeChangeListener(SizeChangeListener mSizeChangeListener) {
        this.mSizeChangeListener = mSizeChangeListener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(null != mSizeChangeListener){
            mSizeChangeListener.onSizeChange(getMeasuredWidth(),getMeasuredHeight());
        }
    }

    public interface SizeChangeListener{
        void onSizeChange(int width,int height);
    }
}
