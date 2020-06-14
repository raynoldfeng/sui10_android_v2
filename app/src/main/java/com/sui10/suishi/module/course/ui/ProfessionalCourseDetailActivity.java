package com.sui10.suishi.module.course.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.utils.ToastUtils;
import com.sui10.commonlib.ui.view.widget.BaseActivity;
import com.sui10.suishi.R;
import com.sui10.suishi.common.ui.JumpManager;
import com.sui10.suishi.manager.UserManager;
import com.sui10.suishi.module.course.bean.CourseBean;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfessionalCourseDetailActivity extends BaseActivity {

    public static final String COURSE_BEAN_KEY = "course_bean";
    private CourseBean mCourseBean;

    @BindView(R.id.course_price_tv)
    TextView mCoursePriceTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();
        setContentView(R.layout.activity_professional_course_detail);
        initData();
    }

    private void parseIntent(){
        Intent intent = getIntent();
        mCourseBean = (CourseBean) intent.getSerializableExtra(COURSE_BEAN_KEY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    private void initData(){
        if(mCourseBean != null)
         mCoursePriceTv.setText(String.valueOf(mCourseBean.getPrice()));
    }

    @OnClick(R.id.study_now_tv)
    void onStudyNowClicked(){
        if(!UserManager.getInstance().isLogin()){
            ToastUtils.showShort(R.string.please_login_first);
            JumpManager.gotoLoginActivity(this);
        }else {
            //TODO ... 跳至支付页面
        }
    }
}
