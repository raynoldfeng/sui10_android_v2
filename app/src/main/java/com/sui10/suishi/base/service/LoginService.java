package com.sui10.suishi.base.service;

import com.sui10.suishi.base.constant.LoginConstant;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginService {
    /*
     * 账号密码登录
     */
    @FormUrlEncoded
    @POST(LoginConstant.LoginUrl.loginByAccount)
    Observable<JsonObject> loginByAccount(@Field("account") String account, @Field("password") String password);

    /*
     * 手机号、验证码登录
     */
    @FormUrlEncoded
    @POST(LoginConstant.LoginUrl.loginBySmsCode)
    Observable<JsonObject> loginBySmsCode(@Field("nationCode") String nationCode, @Field("phone") String phone, @Field("code") String code);

    /*
     * 自动登录
     */
    @GET("https://www.baidu.com/")
    Observable<JsonPrimitive> baiduTest();


    /*
     * 第三方登录
     */
}
