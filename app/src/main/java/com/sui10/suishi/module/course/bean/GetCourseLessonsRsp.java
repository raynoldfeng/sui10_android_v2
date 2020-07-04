package com.sui10.suishi.module.course.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class GetCourseLessonsRsp {
    private int code;
    private String message;
    @SerializedName("data")
    private List<CourseLessonBean> courseLessonsList;

    public int getCode() {
        return code;
    }

    public List<CourseLessonBean> getCourseLessonList() {
        return courseLessonsList;
    }

    public String getMessage() {
        return message;
    }
}
