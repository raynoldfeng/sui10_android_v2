package com.sui10.suishi.common.net.service;

import com.sui10.suishi.common.constant.LoginConstant;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sui10.suishi.common.ui.rsp.CommonRsp;
import com.sui10.suishi.module.login.bean.rsp.SmsLoginRsp;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginService {
    /*
     * 账号密码登录
     */
    @FormUrlEncoded
    @POST(LoginConstant.LOGIN_URL.loginByAccount)
    Observable<JsonObject> loginByAccount(@Field("account") String account, @Field("password") String password);

    /*
     * 手机号、验证码登录
     */
    @FormUrlEncoded
    @POST(LoginConstant.LOGIN_URL.loginBySmsCode)
    Observable<SmsLoginRsp> loginBySmsCode(@Field("nationCode") String nationCode, @Field("phone") String phone, @Field("code") String code);

    /*
     * 获取验证码
     * @param nationCode  国家代码"86";
     * @param code 图形码
     */
    @FormUrlEncoded
    @POST(LoginConstant.LOGIN_URL.getSmsCode)
    Observable<CommonRsp> getSmsCode(@Field("nationCode") String nationCode, @Field("phone") String phone, @Field("code") String code, @Field("rand") String rand);


    /*
     * 自动登录
     */
    @GET("https://www.baidu.com/")
    Observable<JsonPrimitive> baiduTest();


    /*
     * 第三方登录
     */
}
