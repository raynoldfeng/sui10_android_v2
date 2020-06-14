package com.sui10.suishi.manager;

public class UserManager {
    private static UserManager mInstance;

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
        return false;
    }
}
