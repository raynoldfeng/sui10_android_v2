package com.sui10.suishi.module.course.bean;

import java.io.Serializable;

public class OpenCourseTagsBean implements Serializable {
//    "id": 3,
//     "name": "情商",
//     "isDel": 0
    private int id;
    private String name;
    private int isDel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
