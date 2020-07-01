package com.sui10.suishi.common.constant;

import com.sui10.commonlib.base.constants.HostConstant;

public class LoginConstant {

    public interface LOGIN_URL{
        String loginByAccount = HostConstant.LOGIN_SERVER_URL +"/api/account/v1/loginByPassword";

        String loginBySmsCode = HostConstant.LOGIN_SERVER_URL +"/api/account/v1/loginBySmsCode";
    }
}
