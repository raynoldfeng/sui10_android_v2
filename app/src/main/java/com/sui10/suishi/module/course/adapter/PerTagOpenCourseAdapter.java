package com.sui10.suishi.module.course.adapter;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sui10.commonlib.utils.DensityUtils;
import com.sui10.suishi.R;
import com.sui10.suishi.common.ui.adapter.BaseAdapter;
import com.sui10.suishi.common.utils.ImageLoadUtils;
import com.sui10.suishi.module.course.bean.CourseBean;
import com.sui10.suishi.module.course.bean.OpenCourseTagsBean;

import java.util.List;

//每个具体标签下的课程列表
public class PerTagOpenCourseAdapter extends BaseAdapter<CourseBean,PerTagOpenCourseAdapter.OpenCourseViewHolder> {

    @Override
    public OpenCourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_open_course, viewGroup,false);
        return new OpenCourseViewHolder(itemView);
    }

    //TODO... 音频视频课程类型的判断
    @Override
    public void onBindViewHolder(OpenCourseViewHolder holder, int i) {
        CourseBean courseBean = mDataList.get(i);
        ImageLoadUtils.loadRoundImg(courseBean.getCover(),holder.mCourseCoverIv,4);
        holder.mCourseNameTv.setText(courseBean.getName());
        holder.mLearningPeopleTv.setText(courseBean.getWatch()+" 人正在学习");
        //String type = courseBean.getType();
        List<OpenCourseTagsBean> openCourseTagsBeans = courseBean.getTags();
        if(openCourseTagsBeans != null && !openCourseTagsBeans.isEmpty()){
            holder.mCourseTagsAdapter.setData(openCourseTagsBeans);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener != null){
                    mItemClickListener.onItemClick(holder.itemView,courseBean,i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class OpenCourseViewHolder extends RecyclerView.ViewHolder{

        public ImageView mCourseCoverIv;
        public TextView mCourseNameTv;
        public TextView mLearningPeopleTv;
        public ImageView mCourseTypeIv;
        public RecyclerView mCourseTagsRv;
        public PerCourseTagsApdapter mCourseTagsAdapter;

        public OpenCourseViewHolder(View view){
            super(view);
            mCourseCoverIv = view.findViewById(R.id.course_cover_iv);
            mCourseNameTv = view.findViewById(R.id.course_name_tv);
            mLearningPeopleTv = view.findViewById(R.id.course_learning_people_tv);
            mCourseTypeIv = view.findViewById(R.id.course_type_iv);
            mCourseTagsRv = view.findViewById(R.id.course_tags_rv);
            mCourseTagsAdapter = new PerCourseTagsApdapter();
            mCourseTagsRv.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.HORIZONTAL,false));
            mCourseTagsRv.setAdapter(mCourseTagsAdapter);
            mCourseTagsRv.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.right = DensityUtils.dip2px(view.getContext(),8);
                }
            });

        }
    }
}
