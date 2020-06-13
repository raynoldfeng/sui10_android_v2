package com.sui10.suishi.module.main.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sui10.suishi.base.constant.HomeConstant;
import com.sui10.suishi.module.main.ui.ProfessionalCourseFragment;
import com.sui10.suishi.module.main.ui.OpenCourseFragment;
import com.sui10.suishi.module.main.ui.MeFragment;

public class MainViewPagerAdapter extends FragmentPagerAdapter {


    public MainViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case HomeConstant.PAGE_INDEX_HOME:
                fragment = new OpenCourseFragment();
                break;
            case HomeConstant.PAGE_INDEX_FOLLOW:
                fragment = new ProfessionalCourseFragment();
                break;
            case HomeConstant.PAGE_INDEX_MINE:
                fragment = new MeFragment();
                break;
            default:
                break;
        }
        return fragment;
    }


    @Override
    public int getCount() {
        return 3;
    }
}
