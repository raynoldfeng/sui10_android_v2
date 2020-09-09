package com.sui10.suishi.module.course.mvp;

import com.sui10.suishi.module.course.bean.OpenCourseTagsBean;

import java.util.List;

public interface IOpenCourseView {
    void onCourseTagsReqSuccess(List<OpenCourseTagsBean> beans);
}
