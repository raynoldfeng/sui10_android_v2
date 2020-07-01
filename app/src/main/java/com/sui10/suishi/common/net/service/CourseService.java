package com.sui10.suishi.common.net.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sui10.suishi.common.constant.CourseConstant;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CourseService {

    @GET(CourseConstant.COURSE_URL.ALL_COURSE)
    Observable<JsonObject> getAllCourse();
}
