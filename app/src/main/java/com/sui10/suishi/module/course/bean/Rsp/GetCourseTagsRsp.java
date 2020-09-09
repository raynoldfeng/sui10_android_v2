package com.sui10.suishi.module.course.bean.Rsp;

import com.google.gson.annotations.SerializedName;
import com.sui10.suishi.module.course.bean.OpenCourseTagsBean;

import java.util.List;

public class GetCourseTagsRsp {
    private int code;
    private String message;
    @SerializedName("data")
    private List<OpenCourseTagsBean> courseTagsList;

    public int getCode() {
        return code;
    }

    public List<OpenCourseTagsBean> getCourseTagsList() {
        return courseTagsList;
    }

    public String getMessage() {
        return message;
    }
}
