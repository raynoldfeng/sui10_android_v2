package com.sui10.suishi.module.course.mvp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sui10.commonlib.log.LogManager;
import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.suishi.common.constant.NetConstant;
import com.sui10.suishi.common.net.models.CourseModels;
import com.sui10.suishi.module.course.bean.CourseBean;
import com.sui10.suishi.module.course.bean.GetAllCourseRsp;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProfessionalCoursePresenter extends BasePresenter<IProfessionalCourseView> implements IProfessionalCoursePresenter{

    private static final String TAG ="ProfessionalCoursePresenter";

    @Override
    public void getProfessionalCourse() {
        CourseModels.getAllCourse()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<GetAllCourseRsp>() {
             @Override
            public void accept(GetAllCourseRsp rsp) throws Exception {
                 if(rsp != null && rsp.getCode() == NetConstant.RSP_CODE.OK){
                     if(getView() != null){
                         getView().onProCourseListGetSucess(new ArrayList<CourseBean>(rsp.getCourseBeanList().values()));
                     }
                 }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }
}
