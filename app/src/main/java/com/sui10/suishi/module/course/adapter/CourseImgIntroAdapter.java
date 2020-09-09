package com.sui10.suishi.module.course.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sui10.suishi.R;
import com.sui10.suishi.common.ui.adapter.BaseAdapter;
import com.sui10.suishi.common.utils.ImageLoadUtils;

public class CourseImgIntroAdapter extends BaseAdapter<String,CourseImgIntroAdapter.CourseImgIntroViewHolder> {

    @Override
    public CourseImgIntroViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course_img_intro, viewGroup,false);
        return new CourseImgIntroViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseImgIntroViewHolder holder, int i) {
        String url = mDataList.get(i);
        if(!TextUtils.isEmpty(url))
            ImageLoadUtils.loadImage(url,holder.mIntroIv);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class CourseImgIntroViewHolder extends RecyclerView.ViewHolder{

        public ImageView mIntroIv;

        public CourseImgIntroViewHolder(View view){
            super(view);
            mIntroIv = view.findViewById(R.id.course_intro_iv);
        }
    }
}
