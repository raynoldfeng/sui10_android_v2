package com.sui10.suishi.manager;

import com.sui10.suishi.module.login.bean.AccountInfo;

public class UserManager {
    private static UserManager mInstance;
    private String mToken;
    private AccountInfo mAccountInfo;

    public static UserManager getInstance() {
        if (mInstance == null) {
            synchronized (UserManager.class) {
                if (mInstance == null) {
                    mInstance = new UserManager();
                }
            }
        }
        return mInstance;
    }

    public boolean isLogin(){
        return mAccountInfo != null && mToken != null;
    }

    public void setToken(String token){
        mToken = token;
    }

    public String getToken(){
        return mToken;
    }

    public AccountInfo getAccountInfo() {
        return mAccountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo){
        mAccountInfo = accountInfo;
    }
}
