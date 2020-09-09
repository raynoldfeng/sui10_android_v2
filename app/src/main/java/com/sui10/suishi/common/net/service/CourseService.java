package com.sui10.suishi.common.net.service;

import com.sui10.suishi.common.constant.CourseConstant;
import com.sui10.suishi.module.course.bean.Rsp.GetAllCourseRsp;
import com.sui10.suishi.module.course.bean.Rsp.GetCourseLessonsRsp;
import com.sui10.suishi.module.course.bean.Rsp.GetCourseTagsRsp;
import com.sui10.suishi.module.course.bean.Rsp.GetOpenCourseByTagRsp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CourseService {

    @GET(CourseConstant.COURSE_URL.ALL_COURSE)
    Observable<GetAllCourseRsp> getAllCourse();


    @GET(CourseConstant.COURSE_URL.GET_COURSE_LESSONS)
    Observable<GetCourseLessonsRsp> getCourseLessons(@Query("courseName") String courseName);

    @GET(CourseConstant.COURSE_URL.GET_OPEN_COURSE_TAGS)
    Observable<GetCourseTagsRsp> getOpenCourseTags();

    @GET(CourseConstant.COURSE_URL.GET_OPEN_COURSES_BY_TAG)
    Observable<GetOpenCourseByTagRsp> getOpenCourseByTag(@Query("tagId") int tagId, @Query("page") int page, @Query("size") int size);
}
