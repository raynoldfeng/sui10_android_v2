package com.sui10.suishi.module.login.mvp;

public interface ILoginPresenter {
    void getSmsCode(String phone);
    void loginBySmsCode(String phone,String smsCode);
}
