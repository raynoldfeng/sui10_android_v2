package com.sui10.suishi.module.course.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.utils.ToastUtils;
import com.sui10.commonlib.ui.view.base.BaseFragment;
import com.sui10.suishi.R;
import com.sui10.commonlib.base.constants.NetConstant;
import com.sui10.suishi.common.net.models.CourseModels;
import com.sui10.suishi.common.ui.JumpManager;
import com.sui10.suishi.common.ui.adapter.BaseAdapter;
import com.sui10.suishi.module.course.adapter.CourseLessonsAdapter;
import com.sui10.suishi.module.course.bean.CourseBean;
import com.sui10.suishi.module.course.bean.CourseLessonBean;
import com.sui10.suishi.module.course.bean.GetCourseLessonsRsp;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CourseCatalogFragment extends BaseFragment {
    private final static String TAG = "CourseCatalogFragment";

    private CourseLessonsAdapter mCourseLessonsAdapter;
    private LinearLayoutManager mLayoutManager;
    private CourseBean mCourseBean;

    @BindView(R.id.course_catalog_data_rv)
    RecyclerView mCourseCatalogRv;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_catalog;
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
        mCourseCatalogRv.setLayoutManager(mLayoutManager);
        mCourseLessonsAdapter = new CourseLessonsAdapter();
        mCourseCatalogRv.setAdapter(mCourseLessonsAdapter);
        mCourseLessonsAdapter.setItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object item, int position) {
                CourseLessonBean bean = (CourseLessonBean)item;
                String url = bean.url;
                if(TextUtils.isEmpty(url)){
                    ToastUtils.showShort(R.string.please_buy_first);
                }else if(url.length() >6){
                    url= url.substring(2,url.length()-2);
                    JumpManager.gotoWebViewActivity(getContext(),url,bean.name);
                }
            }
        });
        initData();
    }

    private void initData(){
        Bundle bundle = getArguments();
        if(bundle != null){
            mCourseBean = (CourseBean)bundle.getSerializable(ProfessionalCourseDetailActivity.COURSE_BEAN_KEY);
        }
        if(mCourseBean != null)
            requestCourseLessons(mCourseBean.getName());
    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    private void requestCourseLessons(String courseName){
        CourseModels.getCourseLessons(courseName).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetCourseLessonsRsp>() {
                    @Override
                    public void accept(GetCourseLessonsRsp rsp) throws Exception {
                        if(rsp != null && rsp.getCode() == NetConstant.RSP_CODE.OK){
                            mCourseLessonsAdapter.setData(rsp.getCourseLessonList());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }
}
