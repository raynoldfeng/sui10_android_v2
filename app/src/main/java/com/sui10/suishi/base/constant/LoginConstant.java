package com.sui10.suishi.base.constant;

import com.sui10.commonlib.base.constants.HostConstant;

public class LoginConstant {

    public interface LoginUrl{
        public static String loginByAccount = HostConstant.LOGIN_SERVER_URL +"/api/account/v1/loginByPassword";

        public static String loginBySmsCode = HostConstant.LOGIN_SERVER_URL +"/api/account/v1/loginBySmsCode";
    }
}
