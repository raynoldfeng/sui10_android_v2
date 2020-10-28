package com.sui10.suishi.module.login.bean.rsp;

import com.google.gson.annotations.SerializedName;
import com.sui10.suishi.module.login.bean.AccountInfo;

import java.io.Serializable;

public class SmsLoginData implements Serializable {
    public String token;

    @SerializedName("account")
    public AccountInfo account;
}
