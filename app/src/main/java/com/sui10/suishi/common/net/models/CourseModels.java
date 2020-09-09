package com.sui10.suishi.common.net.models;

import com.sui10.commonlib.network.manager.RetrofitManager;
import com.sui10.suishi.common.net.service.CourseService;
import com.sui10.suishi.module.course.bean.Rsp.GetAllCourseRsp;
import com.sui10.suishi.module.course.bean.Rsp.GetCourseLessonsRsp;
import com.sui10.suishi.module.course.bean.Rsp.GetCourseTagsRsp;
import com.sui10.suishi.module.course.bean.Rsp.GetOpenCourseByTagRsp;

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

    public static Observable<GetCourseTagsRsp> getOpenCourseTags(){
        return getCourserService().getOpenCourseTags();
    }

    public static Observable<GetOpenCourseByTagRsp> getOpenCourseByTags(int tagId, int page ,int size){
        return getCourserService().getOpenCourseByTag(tagId, page, size);
    }
}
