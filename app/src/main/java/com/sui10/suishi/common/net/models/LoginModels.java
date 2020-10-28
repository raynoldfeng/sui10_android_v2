package com.sui10.suishi.common.net.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sui10.commonlib.network.manager.RetrofitManager;
import com.sui10.commonlib.utils.LoginRandomCodeUtils;
import com.sui10.suishi.common.constant.LoginConstant;
import com.sui10.suishi.common.net.service.LoginService;
import com.sui10.suishi.common.ui.rsp.CommonRsp;
import com.sui10.suishi.module.login.bean.rsp.SmsLoginRsp;

import io.reactivex.Observable;

public class LoginModels {
    private static LoginService getLoginService(){
        return RetrofitManager.getInstance().get(LoginService.class);
    }

    public static Observable<JsonObject> loginByAccount(String account, String password){
        return getLoginService().loginByAccount(account,password);
    }

    public static Observable<SmsLoginRsp> loginBySmsCode(String phone, String code){
        return getLoginService().loginBySmsCode(LoginConstant.NATION_CODE,phone,code);
    }

    public static Observable<CommonRsp> getSmsCode(String phone){
        return getLoginService().getSmsCode(LoginConstant.NATION_CODE,phone,"", LoginRandomCodeUtils.GetRandomCode());
    }

    public static Observable<JsonPrimitive> baiduTest(){
        return getLoginService().baiduTest();
    }
}
