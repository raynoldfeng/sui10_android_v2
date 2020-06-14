package com.sui10.suishi.common.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseAdapter<T,V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {
    protected List<T> mDataList;
    protected OnItemClickListener mItemClickListener;

    public BaseAdapter() {
        mDataList=new ArrayList<>();
    }

    @Override
    public abstract V onCreateViewHolder(ViewGroup viewGroup, int i);

    @Override
    public abstract void onBindViewHolder(V holder, int i) ;

    @Override
    public abstract int getItemCount() ;

    public void append(List<T> dataList){
        int startPos= mDataList.size();
        int appendCount= dataList.size();
        mDataList.addAll(dataList);
        if(startPos>0 && appendCount>0){
            notifyItemRangeInserted(startPos,appendCount);
        }else {
            notifyDataSetChanged();
        }
    }

    public void setData(List<T> dataList){
        if(dataList!=null){
            mDataList.clear();
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void remove(int position){
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    public List<T> getDataList(){
        return mDataList;
    }

    public void clear(){
        mDataList.clear();
        notifyDataSetChanged();
    }

    public OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener<T>{
        void onItemClick(View view, T item, int position);
    }
}

