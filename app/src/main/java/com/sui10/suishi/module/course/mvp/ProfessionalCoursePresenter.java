package com.sui10.suishi.module.course.mvp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sui10.commonlib.log.LogManager;
import com.sui10.commonlib.ui.presenter.BasePresenter;
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
            .subscribe(new Consumer<JsonObject>() {
             @Override
            public void accept(JsonObject jsonObject) throws Exception {
                 LogManager.d(TAG,"@@@@@@@json Object"+jsonObject.toString());
                 try {
                     Gson gson = new Gson();
                     GetAllCourseRsp rsp = gson.fromJson(jsonObject, GetAllCourseRsp.class);
                     if(rsp.getCode() == 200){
                         if(getView() != null){
                             getView().onProCourseListGetSucess(new ArrayList<CourseBean>(rsp.getCourseBeanList().values()));
                         }
                     }

                 }catch (Exception e){

                 }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }
}
