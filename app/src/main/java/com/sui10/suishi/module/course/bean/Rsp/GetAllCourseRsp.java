package com.sui10.suishi.module.course.bean.Rsp;

import com.google.gson.annotations.SerializedName;
import com.sui10.suishi.module.course.bean.CourseBean;

import java.util.List;
import java.util.Map;

public class GetAllCourseRsp {
    private int code;
    private String message;
    @SerializedName("data")
    private Map<String, CourseBean> courseBeanList;

    public int getCode() {
        return code;
    }

    public Map<String, CourseBean> getCourseBeanList() {
        return courseBeanList;
    }

    public String getMessage() {
        return message;
    }
}
