package com.sui10.commonlib.thirdparty.login.local;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class ThirdLoginWeChatActivity extends Activity implements IWXAPIEventHandler {

     public interface WeChatLoginCallback{
         public void onResp(BaseResp baseResp);
     }

     private static IWXAPI mWeChatApi;
     private static WeChatLoginCallback mCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeChatApi.handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if(mCallback != null)
            mCallback.onResp(baseResp);
        finish();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWeChatApi.handleIntent(intent, this);
        finish();
    }

    public static void init(IWXAPI weChatApi, WeChatLoginCallback chatLoginCallback){
        mWeChatApi = weChatApi;
        mCallback = chatLoginCallback;
    }
}
