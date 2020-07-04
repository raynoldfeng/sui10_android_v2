package com.sui10.suishi.common.net.models;

import com.google.gson.JsonObject;
import com.sui10.commonlib.base.constants.HostConstant;
import com.sui10.commonlib.network.manager.RetrofitManager;
import com.sui10.suishi.common.net.service.CourseService;
import com.sui10.suishi.module.course.bean.GetAllCourseRsp;
import com.sui10.suishi.module.course.bean.GetCourseLessonsRsp;

import io.reactivex.Observable;

public class CourseModels {

    private static CourseService getCourserService(){
        return RetrofitManager.getInstance().get(CourseService.class);
    }

    public static Observable<GetAllCourseRsp> getAllCourse(){
        return getCourserService().getAllCourse();
    }

    public static Observable<GetCourseLessonsRsp> getCourseLessons(String courseName){
        return getCourserService().getCourseLessons(courseName);
    }
}
