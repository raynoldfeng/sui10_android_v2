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
import com.sui10.suishi.module.course.bean.CourseLessonBean;

public class CourseLessonsAdapter extends BaseAdapter<CourseLessonBean,CourseLessonsAdapter.CourseLessonsViewHolder> {


    @Override
    public CourseLessonsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pro_course_lesson, viewGroup,false);
        return new CourseLessonsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseLessonsViewHolder holder, int i) {
        CourseLessonBean courseLessonBean = mDataList.get(i);
        holder.mLessionNameTv.setText((i+1)+". "+courseLessonBean.name);
        holder.mWatchCntTv.setText(courseLessonBean.watch +"人学习");
        if(TextUtils.isEmpty(courseLessonBean.url))
            holder.mNotFreeIv.setVisibility(View.VISIBLE);
        else
            holder.mNotFreeIv.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener != null)
                    mItemClickListener.onItemClick(view,courseLessonBean,i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class CourseLessonsViewHolder extends RecyclerView.ViewHolder{

        public TextView mLessionNameTv;
        public TextView mWatchCntTv;
        public ImageView mNotFreeIv;

        public CourseLessonsViewHolder(View view){
            super(view);
            mLessionNameTv = view.findViewById(R.id.lesson_name_tv);
            mWatchCntTv = view.findViewById(R.id.user_cnt_tv);
            mNotFreeIv = view.findViewById(R.id.not_free_iv);
        }
    }

}
