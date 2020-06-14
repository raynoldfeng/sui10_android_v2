package com.sui10.suishi.module.course.bean;

import java.io.Serializable;
import java.util.List;

//课程仓库bean
public class CourseBean implements Serializable {
    private int id;
    private String name;
    private String cover;//图片
    private String desc;
    private String type;
    private int order;
    private int status;
    private int participants;
    private String watch;
    private long createAt;
    private long updateAt;
    private int isFree;
    private float price;
    private float discountPrice;
    private List<String> imgIntro;

    public String getWatch() {
        return watch;
    }

    public void setWatch(String watch) {
        this.watch = watch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }


    public float getDiscountPrice() {
        return discountPrice;
    }

    public float getPrice() {
        return price;
    }

    public int getIsFree() {
        return isFree;
    }

    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public void setIsFree(int isFree) {
        this.isFree = isFree;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getImgIntro() {
        return imgIntro;
    }

    public void setImgIntro(List<String> imgIntro) {
        this.imgIntro = imgIntro;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }
}

