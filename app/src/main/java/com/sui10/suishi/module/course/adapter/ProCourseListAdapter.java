package com.sui10.suishi.module.course.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sui10.suishi.R;
import com.sui10.suishi.common.ui.adapter.BaseAdapter;
import com.sui10.suishi.common.utils.ImageLoadUtils;
import com.sui10.suishi.module.course.bean.CourseBean;

public class ProCourseListAdapter extends BaseAdapter<CourseBean, ProCourseListAdapter.CourseViewHolder> {

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int i) {
        CourseBean courseBean = mDataList.get(i);
        holder.mCourseNameTv.setText(courseBean.getName());
        holder.mLearendPeopleTv.setText("已有"+courseBean.getWatch()+"人学习");
        ImageLoadUtils.setRoundImgUrlWithRefererHeader(courseBean.getCover(),holder.mCourseCoverIv,8);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener != null)
                    mItemClickListener.onItemClick(holder.itemView,courseBean,i);
            }
        });
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pro_course, viewGroup,false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder{

        public ImageView mCourseCoverIv;
        public TextView mCourseNameTv;
        public TextView mLearendPeopleTv;

        public CourseViewHolder(View itemView){
            super(itemView);
            mCourseCoverIv = itemView.findViewById(R.id.course_cover_iv);
            mCourseNameTv = itemView.findViewById(R.id.course_name_tv);
            mLearendPeopleTv = itemView.findViewById(R.id.learend_people_cnt_tv);
        }

    }
}
