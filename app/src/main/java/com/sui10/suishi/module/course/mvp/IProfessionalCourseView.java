package com.sui10.suishi.module.course.mvp;

import com.sui10.suishi.module.course.bean.CourseBean;

import java.util.List;

public interface IProfessionalCourseView {
    void onProCourseListGetSucess(List<CourseBean> courseBeanList);
}
