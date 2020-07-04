package com.sui10.suishi.module.course.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseLessonBean {
//    "id": 21,
//            "name": "会计要素——资产、负债、所有者权益",
//            "desc": "会计要素——资产、负债、所有者权益",
//            "cover": null,
//            "url": "[\"http:\\/\\/res.sui10.com\\/%E4%BC%9A%E8%AE%A1%E8%A6%81%E7%B4%A0%E2%80%94%E2%80%94%E8%B5%84%E4%BA%A7%E3%80%81%E8%B4%9F%E5%80%BA%E3%80%81%E6%89%80%E6%9C%89%E8%80%85%E6%9D%83%E7%9B%8A.mp4\"]",
//            "preposition": "0",
//            "status": 0,
//            "order": 1,
//            "length": 0.0,
//            "resourceType": "LESSON_RESOURCE_TYPE_VIDEO",
//            "createdAt": 1562223813000,
//            "updateAt": 1563711519000,
//            "courseName": "创业企业财务管理",
//            "reply": 0,
//            "watch": 0 // 观看人数

    public int id;
    public String name;
    public String desc;
    public String cover;
    public String url;
    public String preposition;
    public int status;
    public int order;
    public float length;
    public String type;
    public long createAt;
    public long updateAt;
    public String courseName;
    public int reply;
    public int watch;
}
