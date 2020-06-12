package com.comic.commonlib.ui.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.comic.commonlib.R;

public class CustomToolBar extends RelativeLayout {
    public static final int[] STATUS_BAR_ATTR = {
            R.attr.common_statusBarBackground,
            R.attr.common_topBarBackground
    };

    private ImageView mActionBtn1;
    private  ImageView mActionBtn2;
    private FrameLayout mActionMore;
    private TextView titleTextView;
    private LinearLayout topRightLayout;
    private LinearLayout topCenterLayout;
    private ImageView topLeftImage;
    private View mTitleBar;
    private TextView topLeftTextView;
    private RelativeLayout mTopLeftCustomView;


    public CustomToolBar(Context context) {
        this(context, null);
    }

    public CustomToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.common_toolbar_layout, this);
        initAttr(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topLeftTextView=findViewById(R.id.top_left_title);
        titleTextView = (TextView) findViewById(R.id.title_text);
        topRightLayout = (LinearLayout) findViewById(R.id.top_right_layout);
        topCenterLayout = (LinearLayout) findViewById(R.id.top_center_layout);
        topLeftImage = (ImageView) findViewById(R.id.top_left_image);
        mActionBtn1 = (ImageView) findViewById(R.id.common_action_btn1);
        mActionBtn2 = (ImageView) findViewById(R.id.common_action_btn2);
        mActionMore=findViewById(R.id.commom_action_more);
        mTopLeftCustomView=findViewById(R.id.top_left_custom);
        mTitleBar =findViewById(R.id.commom_title_bar);
        if (mTitleBar != null) {
            mTitleBar.setBackgroundResource(R.color.transparent);
        }
    }

    private void initAttr(Context context) {
        TypedArray array = context.obtainStyledAttributes(STATUS_BAR_ATTR);
        @SuppressLint("ResourceType")
        int titleBg = array.getResourceId(1, android.R.color.white);
        setBackgroundResource(titleBg);
        array.recycle();
    }

    public TextView getTopLeftTextView(){
        return topLeftTextView;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public LinearLayout getTopRightLayout() {
        return topRightLayout;
    }

    public LinearLayout getTopCenterLayout() {
        return topCenterLayout;
    }

    public ImageView getTopLeftImage() {
        return topLeftImage;
    }

    public @Nullable
    ImageView getActionBtn1() {
        return mActionBtn1;
    }

    public FrameLayout getActionMore() {
        return mActionMore;
    }

    public @Nullable ImageView getActionBtn2() {
        return mActionBtn2;
    }

    public View getTitleBar() {
        return mTitleBar;
    }

    public RelativeLayout getTopLeftCustomView() {
        return mTopLeftCustomView;
    }
}
