package com.sui10.suishi.module.course.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sui10.suishi.R;
import com.sui10.suishi.common.ui.adapter.BaseAdapter;
import com.sui10.suishi.module.course.bean.OpenCourseTagsBean;

//每个课程的标签
public class PerCourseTagsApdapter extends BaseAdapter<OpenCourseTagsBean,PerCourseTagsApdapter.CourseTagsViewHolder> {

    @Override
    public CourseTagsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_per_course_tag, viewGroup,false);
        return new CourseTagsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseTagsViewHolder holder, int i) {
        OpenCourseTagsBean bean = mDataList.get(i);
        if(bean != null)
           holder.mTagTv.setText(bean.getName());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class CourseTagsViewHolder extends RecyclerView.ViewHolder{
        public TextView mTagTv;

        public CourseTagsViewHolder(View view){
            super(view);
            mTagTv = view.findViewById(R.id.tag_name_tv);
        }
    }
}
