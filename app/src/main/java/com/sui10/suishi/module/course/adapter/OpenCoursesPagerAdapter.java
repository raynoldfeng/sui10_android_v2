package com.sui10.suishi.module.course.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.sui10.commonlib.ui.view.widget.viewpage.LazyFragmentPagerAdapter;
import com.sui10.suishi.common.ui.entity.FragClassifyEntity;

import java.util.List;

public class OpenCoursesPagerAdapter extends LazyFragmentPagerAdapter {

    private List<FragClassifyEntity> mFrgInfoList;
    private Activity mActivity;

    public OpenCoursesPagerAdapter(Activity activity, FragmentManager fm) {
        super(fm);
        mActivity = activity;
    }

    public void setData(List<FragClassifyEntity> frgList){
        mFrgInfoList = frgList;
        notifyDataSetChanged();
    }

    @Override
    protected Fragment getItem(ViewGroup container, int position) {
        FragClassifyEntity entity = mFrgInfoList.get(position);
        Bundle args = entity.getBundle();
        Fragment fragment = Fragment.instantiate(mActivity, entity.getFragClass().getName(), args);
        return fragment;
    }

    @Override
    public int getCount() {
        if(mFrgInfoList != null)
            return mFrgInfoList.size();
        else
            return 0;
    }
}
