package com.sui10.commonlib.ui.view.widget.viewpage;


/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Method;

/**
 * <p/>
 * 这里需要Fix一个bug，该bug的描述如下：
 * ViewPager在设置完adapter后，会把mCurItem 设置为0；
 * 然后我们在初始化中，设置默认值0时（setCurrentItem）， 是不会触发pageChange的回调的，因为它监听到你的mCurItem相同
 * 具体参见代码：
 * setCurrentItemInternal ->  final boolean dispatchSelected = mCurItem != item;
 * 其中dispatchSelected 决定了是否调用pageChange的回调
 * <p/>
 * 修复如下，在调用setAdapter后，会给个参数，判断是否重新初始化了，如果从新初始化过，第一次默认值是会发生回调
 */
public class CustomViewPager extends SwipeViewPage {

    private boolean mIsReInit = false;

    private Method mDispatchOnPageSelected = null;

    public CustomViewPager(Context context) {
        super(context,null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        mIsReInit = true;
    }

    @Override
    public void setCurrentItem(final int item) {
        boolean isNeedCallback = false;
        if (mIsReInit) {
            isNeedCallback = getCurrentItem() == item;
            mIsReInit = false;
        }

        super.setCurrentItem(item);

        if (isNeedCallback) {
            invokePrivateMothod(item);
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        boolean isNeedCallback = false;
        if (mIsReInit) {
            isNeedCallback = getCurrentItem() == item;
            mIsReInit = false;
        }
        super.setCurrentItem(item, smoothScroll);

        if (isNeedCallback) {
            invokePrivateMothod(item);
        }
    }

    /**
     * 获取并调用私有方法
     */
    private void invokePrivateMothod(int postion) {
        try {
            if (mDispatchOnPageSelected == null) {
                mDispatchOnPageSelected = ViewPager.class.getDeclaredMethod("dispatchOnPageSelected", int.class);
                // 若调用私有方法，必须抑制java对权限的检查
                mDispatchOnPageSelected.setAccessible(true);
            }
            // 使用invoke调用方法，并且获取方法的返回值，需要传入一个方法所在类的对象，new Object[]
            mDispatchOnPageSelected.invoke(this, postion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        return false;
    }
}
