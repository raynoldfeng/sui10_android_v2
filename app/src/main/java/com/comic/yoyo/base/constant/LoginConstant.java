package com.comic.yoyo.base.constant;

import com.comic.commonlib.base.constants.HostConstant;

public class LoginConstant {

    public interface LoginUrl{
        public static String loginByAccount = HostConstant.LOGIN_SERVER_URL +"/api/account/v1/loginByPassword";

        public static String loginBySmsCode = HostConstant.LOGIN_SERVER_URL +"/api/account/v1/loginBySmsCode";
    }
}
