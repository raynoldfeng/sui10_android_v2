package com.sui10.commonlib.ui.view.widget.viewpage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sui10.commonlib.R;
import com.sui10.commonlib.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class SwipeTabView extends LinearLayout{

    protected List<TabItem> mTabItemList = new ArrayList<>();

    protected int mCurrentSelected = 0;

    private boolean mBottomLineVisible = true;  //默认显示

    public LinearLayout mTabContent;
    public View mBottomLine;

    private static final int INDICATOR_WIDTH_V9 = 30;

    protected int mIndicatorWidth;
    protected int mIndicatorHeight;
    private int mIndicatorColor;
    private int mIndicatorConerRadius;
    private boolean mIndicatorVisible = true;
    private int mCutomWidth = 0;
    private int[] mLastLocation = new int[]{-1, -1};

    private int mCutomHeight = 0;

    private int mLastPosition;

    private int mSelectedPosition;

    private float mSelectionOffset;

    private boolean mIsDrag;

    private IndicationInterpolator mIndicationInterpolator;

    private final RectF mIndicatorRectF = new RectF();

    private final Paint mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public SwipeTabView(Context context) {
        this(context, null);
    }

    public SwipeTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SwipeTabView);
        if (ta != null) {
            mCurrentSelected = ta.getInt(R.styleable.SwipeTabView_stv_default_item, 0);
            mBottomLineVisible = ta.getBoolean(R.styleable.SwipeTabView_bottom_line_visibility, true);
            mIsDrag = ta.getBoolean(R.styleable.SwipeTabView_drag_indicator, true);
            ta.recycle();
        }
    }

    public LinearLayout getTabContent(){
        return mTabContent;
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        initMainLayout();
        mTabContent = (LinearLayout) findViewById(R.id.tab_content);
        mBottomLine = findViewById(R.id.tab_bottom_line);
        mBottomLine.setVisibility(mBottomLineVisible ? VISIBLE : GONE);

        if (mCurrentSelected == -1) {
            setTabIndicatorVisible(false);
        }
        mIndicationInterpolator = new IndicationInterpolator();
        mIndicatorColor = getResources().getColor(R.color.color_FFE6B66E);
        mIndicatorHeight = DensityUtils.dip2px(getContext(), 2);
        mIndicatorConerRadius = DensityUtils.dip2px(getContext(), 1);
        setWillNotDraw(false);
    }

    protected void initMainLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.common_swipe_tabview_layout, this);
    }

    public void setIndicatorWidth(int widthPx) {
        this.mIndicatorWidth = widthPx;
    }

    public void setTabIndicatorColor(int color) {
        this.mIndicatorColor = color;

    }

    public void setIndicatorConerRadius(int conerRadiusPx) {
        this.mIndicatorConerRadius = conerRadiusPx;
    }

    public void setTabItemStyle(int bg, int textColor, int textSize) {
        int size = mTabItemList.size();
        for (int index = 0; index < size; index++) {
            TabItem tabItem = mTabItemList.get(index);
            if (tabItem != null) {
                RelativeLayout rlTabViewItem = (RelativeLayout) mTabContent.getChildAt(index).findViewById(R.id.rl_tab_view_item);
                TextView title = (TextView) mTabContent.getChildAt(index).findViewById(R.id.tab_title);
                rlTabViewItem.setBackgroundColor(getResources().getColor(bg));
                title.setTextColor(getResources().getColorStateList(textColor));
                if (textSize > 0) {
                    title.setTextSize(textSize);
                }
            }
        }
    }

    public void setTabItemStyle(int bg, int textColor) {
        setTabItemStyle(bg, textColor, 0);
    }

    public void setTabItemSize(float textSize) {
        int size = mTabItemList.size();
        for (int index = 0; index < size; index++) {
            TabItem tabItem = mTabItemList.get(index);
            if (tabItem != null) {
                TextView title = (TextView) mTabContent.getChildAt(index).findViewById(R.id.tab_title);
                title.setTextSize(textSize);
            }
        }
    }


    public void setTabIndicatorVisible(boolean visible) {
        this.mIndicatorVisible = visible;
    }

    public void setTabArrays(List<Integer> list) {
        if (list==null||list.isEmpty()) {
            return;
        }
        int i = 0;
        mTabItemList.clear();
        for (Integer resId : list) {
            String title = getContext().getString(resId);
            if (title == null) {
                title = "";
            }
            mTabItemList.add(new TabItem(i++, title));
        }
        initView();
    }

    public void setTabArrays(int... ids) {
        if (ids == null || ids.length == 0)
            return;

        mTabItemList.clear();
        for (int i = 0, n = ids.length; i < n; ++i) {
            String title = getContext().getString(ids[i]);
            mTabItemList.add(new TabItem(i, title));
        }
        initView();
    }

    public void setTabArrays(String... ids) {
        if (ids == null || ids.length == 0)
            return;

        mTabItemList.clear();
        for (int i = 0, n = ids.length; i < n; ++i) {
            String title =ids[i];
            mTabItemList.add(new TabItem(i, title));
        }
        initView();
    }

    public void setTabArray(List<? extends CharSequence> list) {
        if (list==null||list.isEmpty()) {
            return;
        }

        int i = 0;
        mTabItemList = new ArrayList<>();
        for (CharSequence charSequence : list) {
            mTabItemList.add(new TabItem(i++, charSequence));
        }
        initView();
    }

    protected void initView() {
        mTabContent.removeAllViews();
        int count = mTabItemList.size();

        for (int i = 0; i < count; i++) {
            addTab(i, mTabItemList.get(i));
        }

        updateTabView(mCurrentSelected);
//        mTabIndicator.setTabContent(this);
    }

    // ADDBY: chuangxinli at 2016/8/8
    public void updateItemTab(int index, String tabName) {
        if (index < mTabItemList.size() && index > -1) {
            TabItem tabItem = mTabItemList.get(index);
            if (tabItem != null) {
                tabItem.setText(tabName);
                TextView title = (TextView) mTabContent.getChildAt(index).findViewById(R.id.tab_title);
                title.setText(tabName);
            }
        }
    }

    public void showRedDot(int index, boolean show) {
        if (index < mTabItemList.size() && index > -1) {
            TabItem tabItem = mTabItemList.get(index);
            if (tabItem != null) {
                View redDot = mTabContent.getChildAt(index).findViewById(R.id.view_red_dot);
                View numView= mTabContent.getChildAt(index).findViewById(R.id.tv_unread_num);
                if(null != redDot){
                    redDot.setVisibility(show ? View.VISIBLE:View.GONE);
                    if(show){
                        numView.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    public void showUnreadRedDot(int index, int num) {
        if (index < mTabItemList.size() && index > -1) {
            TabItem tabItem = mTabItemList.get(index);
            if (tabItem != null) {
                View redDot = mTabContent.getChildAt(index).findViewById(R.id.view_red_dot);
                TextView numView= mTabContent.getChildAt(index).findViewById(R.id.tv_unread_num);
                if(num >0){
                    redDot.setVisibility(View.GONE);
                    numView.setVisibility(View.VISIBLE);
                    numView.setText(num>99 ? "99+" : (num+""));
                }else{
                    numView.setVisibility(View.GONE);
                }
            }
        }
    }

    public void updateItemTab(int index, CharSequence tabName) {
        if (index < mTabItemList.size() && index > -1) {
            TabItem tabItem = mTabItemList.get(index);
            if (tabItem != null) {
                tabItem.setText(tabName);
                TextView title = (TextView) mTabContent.getChildAt(index).findViewById(R.id.tab_title);
                title.setText(tabName);
            }
        }
    }

    public void updateItemTabWithNum(int index, String tabName) {
        updateItemTab(index, stringWithNumToCharSequence(tabName));
    }

    public void updateItemTabWithNum(List<String> tabNames) {
        if (tabNames == null || tabNames.size() == 0)
            return;
        int length = tabNames.size();
        for (int i = 0; i < length; i++) {
            updateItemTab(i, stringWithNumToCharSequence(tabNames.get(i)));
        }
    }

    public CharSequence stringWithNumToCharSequence(String s) {
        int start = s.indexOf("/");
        if (start >= 0) {
            SpannableString sp = new SpannableString(s);
            sp.setSpan(new AbsoluteSizeSpan(11, true), start, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sp;
        } else {
            return s;
        }
    }

    public void updateItemTab(int index, int padding, final Drawable drawable, String tabName) {
        if (index < mTabItemList.size() && index > -1) {
            TabItem tabItem = mTabItemList.get(index);
            if (tabItem != null) {
                tabItem.setText(tabName);
                TextView title = (TextView) mTabContent.getChildAt(index).findViewById(R.id.tab_title);
                title.setText(tabName);
                title.setCompoundDrawablePadding(padding);
                title.setCompoundDrawables(null, null, drawable, null);
            }
        }
    }

    public void setCurrentItem(int position) {
        mCurrentSelected = position;
        updateTabView(mCurrentSelected);
    }

    public int getCurrentItem() {
        return mCurrentSelected;
    }

    public int getItemCount() {
        return mTabItemList.size();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mTabContent.getChildCount() == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (mCutomWidth != mIndicatorWidth) {
                mIndicatorWidth = mCutomWidth;
            }
        } else {
            int parentWidthSize = MeasureSpec.getSize(widthMeasureSpec);
            final int tmpIndicatorWidth = (int) (parentWidthSize / (float) mTabContent.getChildCount());
            if (tmpIndicatorWidth != mIndicatorWidth) {
                mIndicatorWidth = tmpIndicatorWidth;
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setTabItemSize(int position) {
        int size = mTabItemList.size();
        for (int index = 0; index < size; index++) {
            TabItem tabItem = mTabItemList.get(index);


            if (index == position) {
                if (tabItem != null) {
                    TextView title = (TextView) mTabContent.getChildAt(index).findViewById(R.id.tab_title);
                    title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
                    title.setTextColor(getResources().getColor(R.color.color_FFE3A344));
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) title.getLayoutParams();
                    lp.setMargins(DensityUtils.dip2px(getContext(), 10),
                            DensityUtils.dip2px(getContext(), 8), DensityUtils.dip2px(getContext(), 10), 0);
                    title.setLayoutParams(lp);

                }
            } else {
                if (tabItem != null) {
                    TextView title = (TextView) mTabContent.getChildAt(index).findViewById(R.id.tab_title);
                    title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                    title.setTextColor(getResources().getColor(R.color.color_FF888888));
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) title.getLayoutParams();
                    lp.setMargins(DensityUtils.dip2px(getContext(), 10),
                            DensityUtils.dip2px(getContext(), 10), DensityUtils.dip2px(getContext(), 10), 0);
                    title.setLayoutParams(lp);
                }
            }
        }
        invalidate();
    }


    protected void addTab(int position, TabItem tabItem) {
        View view = getItemView();
        TextView title = (TextView) view.findViewById(R.id.tab_title);
        view.setOnClickListener(mTabClickListener);
        title.setText(tabItem.text);
        view.setTag(tabItem.getIndex());//索引作为view的tag，用于处理点击事件

        //LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) title.getLayoutParams();
//        if (position == 0) {
//            title.setTextColor(getResources().getColor(R.color.common_text_black));
//        }else{
//            title.setTextColor(getResources().getColor(R.color.common_second_text_color));
//        }

        //title.setLayoutParams(lp);
        mTabContent.addView(view, new LinearLayout.
                LayoutParams(WRAP_CONTENT, mCutomHeight == 0 ? MATCH_PARENT : mCutomHeight,1));
//        }
//        mTabIndicator.requestLayout();
    }

    /**
     * 自定义tab宽度
     *
     * @param width >0 表示使用自定义宽度，否则使用默认布局
     */
    public void setCustomWidth(int width) {
        mCutomWidth = width;
    }

    /**
     * 自定义tab高度
     *
     * @param height >0 表示使用自定义高度，否则使用默认布局
     */
    public void setCustomHeight(int height) {
        this.mCutomHeight = height;
    }

    /**
     * 加载item布局，该布局中需包含id为R.id.tab_title的TextView
     * 如果有特殊需求可以继承该类重写该方法自定义布局
     *
     * @return
     */
    protected View getItemView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.common_swipe_tabview_item, null);
        return view;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (l == 0) {
            if (mLastLocation[1] <= 0) {
                int[] location = new int[2];
                getLocationInWindow(mLastLocation);
                if (location[0] > 0) {
                    mLastLocation = location;
                }
            }
        }
    }


    public interface OnTabSelectedListener {
        void onTabSelected(int position);
    }

    private OnTabSelectedListener mTabSelectedListener;

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        mTabSelectedListener = listener;
    }

    public OnTabSelectedListener getOnTabSelectedListener() {
        return mTabSelectedListener;
    }

    protected final View.OnClickListener mTabClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            mCurrentSelected = (int) view.getTag();
            updateTabView(mCurrentSelected);
            if (mTabSelectedListener != null) {
                mTabSelectedListener.onTabSelected(mCurrentSelected);
            }
        }
    };


    protected void updateTabView(int selectedItem) {
        for (int i = 0; i < mTabContent.getChildCount(); i++) {
            View title = mTabContent.getChildAt(i).findViewById(R.id.tab_title);
            title.setSelected(i == selectedItem);
        }

        if (selectedItem > -1) {
            setTabIndicatorVisible(true);
        }
    }


    /**
     * 设置下划线移动坐标
     *
     * @param position             当前的item
     * @param positionOffset       变化偏移量百分比
     * @param positionOffsetPixels 变化偏移量绝对值
     */
    public void updateIndicatorByCoordinate(int position, float positionOffset, int positionOffsetPixels) {
        updateIndicatorByCoordinate(position, positionOffset, positionOffsetPixels, false);
    }

    public void updateIndicatorByCoordinate(final int position, final float positionOffset, int positionOffsetPixels, boolean isSmooth) {
        int count = mTabItemList.size();
        if (count > 0) {

            onViewPagerPageChanged(position, positionOffset);
        }
    }


    public View getChildView(int childIndex) {
        if (childIndex >= 0 && childIndex < mTabContent.getChildCount()) {
            return mTabContent.getChildAt(childIndex);
        }
        return null;
    }

    public class TabItem {
        public int index;//索引，在tab中的位置,也作为view的tag
        public CharSequence text;//tab上显示的文字

        public TabItem(int index, CharSequence text) {
            this.index = index;
            this.text = text;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public CharSequence getText() {
            return text;
        }

        public void setText(CharSequence text) {
            this.text = text;
        }
    }

    /**
     * 修改文字
     *
     * @param oldText
     * @param newText
     */
    public void changeText(String oldText, String newText) {
        if (mTabContent != null && mTabContent.getChildCount() > 0) {
            for (int i = 0; i < mTabContent.getChildCount(); i++) {
                View child = mTabContent.getChildAt(i);
                if (child != null && child.findViewById(R.id.tab_title) != null) {
                    TextView tv = (TextView) child.findViewById(R.id.tab_title);
                    String s = tv.getText().toString();
                    if (!TextUtils.isEmpty(s) && s.equals(oldText)) {
                        tv.setText(newText);
                    }
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mIndicatorVisible) {
            return;
        }
        final int childCount = mTabContent.getChildCount();
        if (childCount > 0) {
            View selectTab = mTabContent.getChildAt(mSelectedPosition);
            if (selectTab != null) {
                int padding = (selectTab.getWidth() - DensityUtils.dip2px(getContext(), INDICATOR_WIDTH_V9)) / 2;
                int left = selectTab.getLeft() + padding;
                int right = selectTab.getRight() - padding;

                if (mSelectionOffset > 0f && mSelectedPosition < (childCount - 1)) {
                    float leftOffset;
                    float rightOffset;

                    if (mIsDrag) {
                        leftOffset = mIndicationInterpolator.getLeftEdge(mSelectionOffset);
                        rightOffset = mIndicationInterpolator.getRightEdge(mSelectionOffset);
                    } else {
                        leftOffset = mSelectionOffset;
                        rightOffset = mSelectionOffset;
                    }

//                    KGLog.d("indicatorOffset", "     : " + leftOffset);
//                    KGLog.d("SelectionOffset", "     : " + mSelectionOffset);

                    View nextTab = mTabContent.getChildAt(mSelectedPosition + 1);
                    left = (int) (leftOffset * (nextTab.getLeft() + padding) +
                            (1.0f - leftOffset) * left);
                    right = (int) (rightOffset * (nextTab.getRight() - padding) +
                            (1.0f - rightOffset) * right);
                }

                mIndicatorPaint.setColor(mIndicatorColor);
                mIndicatorPaint.setStyle(Paint.Style.FILL);

                if(Build.VERSION.SDK_INT<17){
                    mIndicatorRectF.set(left , getHeight() - mIndicatorHeight, right , getHeight());
                }else{
                    mIndicatorRectF.set(left,
                            getHeight() - mIndicatorHeight,
                            right , getHeight());
                }

                canvas.drawRoundRect(mIndicatorRectF, mIndicatorConerRadius,
                        mIndicatorConerRadius, mIndicatorPaint);
            }
        }
    }

    void onViewPagerPageChanged(int position, float positionOffset) {
        mSelectedPosition = position;
        mSelectionOffset = positionOffset;
        if (positionOffset == 0f && mLastPosition != mSelectedPosition) {
            mLastPosition = mSelectedPosition;
        }
        invalidate();
    }

    public static class IndicationInterpolator {

        private static final float DEFAULT_INDICATOR_INTERPOLATION_FACTOR = 3.0f;

        private final Interpolator mLeftEdgeInterpolator;
        private final Interpolator mRightEdgeInterpolator;

        public IndicationInterpolator() {
            this(DEFAULT_INDICATOR_INTERPOLATION_FACTOR);
        }

        public IndicationInterpolator(float factor) {
            mLeftEdgeInterpolator = new AccelerateInterpolator(factor);
            mRightEdgeInterpolator = new DecelerateInterpolator(factor);
        }

        public float getLeftEdge(float offset) {
            return mLeftEdgeInterpolator.getInterpolation(offset);
        }

        public float getRightEdge(float offset) {
            return mRightEdgeInterpolator.getInterpolation(offset);
        }
    }

}