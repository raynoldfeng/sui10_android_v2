package com.sui10.suishi.module.login.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AccountInfo implements Serializable {
    public int id;

    public String account;

    public String phone;

    public String nick;

    public int identify;

    public String identification;

    public int gender;

    public String avatar;//头像

    public String bio;//简介

    @SerializedName("tag")
    public List<String> tag;

    public int status;

    public long regTime;

    public long lastLoginTime;

    public String lastLoginIp;
    public int quizClear;   //是否通过测试
}
