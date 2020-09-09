package com.sui10.suishi.module.course.bean.Rsp;

import com.google.gson.annotations.SerializedName;
import com.sui10.suishi.module.course.bean.CourseBean;

import java.util.List;

public class GetOpenCourseByTagRsp {
    private int code;
    private String message;
    @SerializedName("data")
    private List<CourseBean> coursesList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CourseBean> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<CourseBean> coursesList) {
        this.coursesList = coursesList;
    }
}
