package com.sui10.suishi.module.course.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sui10.commonlib.base.constants.NetConstant;
import com.sui10.commonlib.log.LogManager;
import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.utils.ToastUtils;
import com.sui10.commonlib.ui.view.base.BaseFragment;
import com.sui10.commonlib.utils.DensityUtils;
import com.sui10.commonlib.utils.ResourceUtils;
import com.sui10.suishi.R;
import com.sui10.suishi.common.net.models.CourseModels;
import com.sui10.suishi.module.course.adapter.PerTagOpenCourseAdapter;
import com.sui10.suishi.module.course.bean.Rsp.GetOpenCourseByTagRsp;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

//TODO...下拉刷新，上拉加载更多
public class PerTagOpenCourseFragment  extends BaseFragment {

    public static String OPEN_COURSE_TAG_ID = "open_course_tag_id";
    private static String TAG = "OpenCourseLessonsFragment";
    private int mTagId = -1;
    private LinearLayoutManager mLayoutManager;
    private PerTagOpenCourseAdapter mAdapter;


    @BindView(R.id.data_rv)
    RecyclerView mCourseLessonsRv;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.layout_common_recyclerview;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initViewsAndEvents() {
        mLayoutManager=new LinearLayoutManager(getContext());
        mCourseLessonsRv.setLayoutManager(mLayoutManager);
        mCourseLessonsRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = DensityUtils.dip2px(getActivity(),24);
            }
        });

        mAdapter = new PerTagOpenCourseAdapter();
        mCourseLessonsRv.setAdapter(mAdapter);
        initData();
    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    private void initData(){
        Bundle bundle = getArguments();
        if(bundle != null){
            mTagId = bundle.getInt(OPEN_COURSE_TAG_ID,-1);
            if(mTagId != -1){
                LogManager.d(TAG,"tagId :"+mTagId);
                reqCourseListWithTag();
            }else {
                showNoData(ResourceUtils.getString(R.string.prepareing));
            }
        }
    }

    private void reqCourseListWithTag(){
        if(mTagId == -1)
            return;
          addDisposable(CourseModels.getOpenCourseByTags(mTagId,0,10)
                  .subscribeOn(Schedulers.newThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Consumer<GetOpenCourseByTagRsp>() {
                      @Override
                      public void accept(GetOpenCourseByTagRsp getOpenCourseByTagRsp) throws Exception {
                          if(getOpenCourseByTagRsp != null && getOpenCourseByTagRsp.getCode() == NetConstant.RSP_CODE.OK){
                              mAdapter.setData(getOpenCourseByTagRsp.getCoursesList());
                          }else {
                              ToastUtils.showShort(getOpenCourseByTagRsp.getMessage());
                          }
                      }
                  }, new Consumer<Throwable>() {
                      @Override
                      public void accept(Throwable throwable) throws Exception {
                          LogManager.e(TAG,"getOpenCourseByTags exception : "+throwable.getMessage());

                      }
                  }));
    }
}
