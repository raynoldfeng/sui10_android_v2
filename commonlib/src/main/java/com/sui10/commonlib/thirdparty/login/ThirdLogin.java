package com.sui10.commonlib.thirdparty.login;

import android.app.Activity;
import android.content.Intent;

import java.util.Map;

public interface ThirdLogin {
    public void init(Map<String, String> param, Activity activity);
    public void login(ThirdLoginCallback callback);
    public void onActivityResult(int requestCode, int resultCode, Intent data);
}
