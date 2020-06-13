package com.sui10.suishi.module.usersystem.models;


import com.sui10.commonlib.network.manager.RetrofitManager;
import com.sui10.suishi.base.service.AccountService;
import com.sui10.suishi.base.service.LoginService;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.reactivex.Observable;

public class AccountModel {

    private static LoginService getLoginService(){
        return RetrofitManager.getInstance().get(LoginService.class);
    }

    public static Observable<JsonObject> loginByAccount(String account, String password){
        return getLoginService().loginByAccount(account,password);
    }

    public static Observable<JsonObject> loginBySmsCode(String nationCode, String phone, String code){
        return getLoginService().loginBySmsCode(nationCode,phone,code);
    }

    public static Observable<JsonPrimitive> baiduTest(){
        return getLoginService().baiduTest();
    }
}
