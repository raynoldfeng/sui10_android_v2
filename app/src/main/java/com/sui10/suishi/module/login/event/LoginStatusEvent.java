package com.sui10.suishi.module.login.event;

public class LoginStatusEvent {
    public int mStatus;

    public interface LOGIN_STATUS{
        int LOGIN_SUCESS = 0;
        int LOG_OUT = 1;
    }

    public LoginStatusEvent(int status){
        mStatus = status;
    }
}
