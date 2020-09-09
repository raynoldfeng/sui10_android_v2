package com.sui10.suishi.common.constant;

import com.sui10.commonlib.base.constants.HostConstant;

public class CourseConstant {

    public interface COURSE_URL{
        //获取所有课程 TODO...page和type动态传入
        String ALL_COURSE = HostConstant.COURSE_SERVER_URL + "/api/study/v2/course/all?page=0&type=1";

        //获取课件
        String GET_COURSE_LESSONS = HostConstant.COURSE_SERVER_URL +"/api/study/v2/lesson/query";

        //获取公开课程tag
        String GET_OPEN_COURSE_TAGS = HostConstant.COURSE_SERVER_URL +"/api/study/v2/course/tags/query";

        //获取指定TAG的公开课列表
        String GET_OPEN_COURSES_BY_TAG = HostConstant.COURSE_SERVER_URL +"/api/study/v2/course/queryByTag";
    }
}
