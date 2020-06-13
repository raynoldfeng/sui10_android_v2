package com.sui10.commonlib.thirdparty.login;

public interface ThirdLoginCallback {
    public void success(String uId, String nick, String avatar, String accessToken);
    public void failure();
    public void cancle();
}
