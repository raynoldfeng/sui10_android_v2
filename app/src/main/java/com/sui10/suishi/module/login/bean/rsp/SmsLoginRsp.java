package com.sui10.suishi.module.login.bean.rsp;

import com.google.gson.annotations.SerializedName;
import com.sui10.suishi.common.ui.rsp.CommonRsp;
import com.sui10.suishi.module.login.bean.rsp.SmsLoginData;

public class SmsLoginRsp extends CommonRsp {

    @SerializedName("data")
    public SmsLoginData smsLoginData;
}
