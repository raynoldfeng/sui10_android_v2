package com.sui10.suishi.common.constant;

import com.sui10.commonlib.base.constants.HostConstant;

public class LoginConstant {

    public interface LOGIN_URL{
        /*
         * 账号密码登录
         */
        String loginByAccount = HostConstant.LOGIN_SERVER_URL +"/api/account/v1/loginByPassword";

        /*
         * 手机号、验证码登录
         */
        String loginBySmsCode = HostConstant.LOGIN_SERVER_URL +"/api/account/v1/loginBySmsCode";

        /*
         * 获取验证码
         */
        String getSmsCode = HostConstant.LOGIN_SERVER_URL +"/api/account/v1/sendSmsCode";
    }

    public static String NATION_CODE = "86";
    public static String RAND_CODE = "";


}
