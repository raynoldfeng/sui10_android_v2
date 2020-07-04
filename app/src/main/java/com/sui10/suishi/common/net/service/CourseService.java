package com.sui10.suishi.common.net.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sui10.suishi.common.constant.CourseConstant;
import com.sui10.suishi.module.course.bean.GetAllCourseRsp;
import com.sui10.suishi.module.course.bean.GetCourseLessonsRsp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CourseService {

    @GET(CourseConstant.COURSE_URL.ALL_COURSE)
    Observable<GetAllCourseRsp> getAllCourse();


    @GET(CourseConstant.COURSE_URL.GET_COURSE_LESSONS)
    Observable<GetCourseLessonsRsp> getCourseLessons(@Query("courseName") String courseName);
}
