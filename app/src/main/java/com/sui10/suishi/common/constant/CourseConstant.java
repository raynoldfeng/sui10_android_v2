package com.sui10.suishi.common.constant;

import com.sui10.commonlib.base.constants.HostConstant;

public class CourseConstant {

    public interface COURSE_URL{
        //获取所有课程 TODO...page和type动态传入
        String ALL_COURSE = HostConstant.COURSE_SERVER_URL + "/api/study/v2/course/all?page=0&type=1";

        //获取课件
        String GET_COURSE_LESSONS = HostConstant.COURSE_SERVER_URL +"/api/study/v2/lesson/query";
    }
}
