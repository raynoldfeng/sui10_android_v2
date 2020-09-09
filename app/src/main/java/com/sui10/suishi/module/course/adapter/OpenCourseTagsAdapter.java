package com.sui10.suishi.module.course.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sui10.commonlib.utils.ResourceUtils;
import com.sui10.suishi.R;
import com.sui10.suishi.common.ui.adapter.BaseAdapter;

//公开课的所有标签列表
public class OpenCourseTagsAdapter extends BaseAdapter<String,OpenCourseTagsAdapter.OpenCourseTagsViewHolder> {

    private int mCurTagIndex = 0;

    public void setCurTag(int index){
        mCurTagIndex = index;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(OpenCourseTagsViewHolder holder, int i) {
        String name = mDataList.get(i);
        holder.mTagTv.setText(name);
        if(mCurTagIndex == i){
            holder.mTagTv.setTextColor(ResourceUtils.getColor(R.color.color_FFE3A344));
            holder.mIndicatorIv.setVisibility(View.VISIBLE);
        }else {
            holder.mTagTv.setTextColor(ResourceUtils.getColor(R.color.color_FF999999));
            holder.mIndicatorIv.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener != null)
                    mItemClickListener.onItemClick(view,name,i);
            }
        });
    }

    @Override
    public OpenCourseTagsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course_tag, viewGroup,false);
        return new OpenCourseTagsViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class OpenCourseTagsViewHolder extends RecyclerView.ViewHolder{
        public ImageView mIndicatorIv;
        public TextView mTagTv;

        public OpenCourseTagsViewHolder(View view){
            super(view);
            mIndicatorIv = view.findViewById(R.id.indicator_iv);
            mTagTv = view.findViewById(R.id.tag_name_tv);
        }
    }
}
