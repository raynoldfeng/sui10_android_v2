package com.sui10.suishi.module.login.thirdparty;

import android.app.Activity;
import android.content.Intent;

import com.sui10.commonlib.base.CommonApplication;
import com.sui10.commonlib.thirdparty.login.ThirdLogin;
import com.sui10.commonlib.thirdparty.login.ThirdLoginCallback;
import com.sui10.commonlib.thirdparty.login.ThirdLoginConstants;
import com.sui10.commonlib.thirdparty.login.ThirdLoginFactory;
import com.sui10.commonlib.thirdparty.login.ThirdLoginType;
import com.sui10.commonlib.thirdparty.login.local.ThirdLoginWeChat;
import com.sui10.commonlib.ui.utils.ToastUtils;
import com.sui10.suishi.R;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.HashMap;
import java.util.Map;

public class SuishiThirdLogin {
    private ThirdLogin mThirdLogin;
    private Activity mActivity;

    public SuishiThirdLogin(Activity activity){
        mActivity = activity;
    }

    /**
     * onActivityResult的回调
     */
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if(null != mThirdLogin){
            mThirdLogin.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void login(int type, ThirdLoginCallback callback){
        init(type);
        doThirdLogin(type, callback);
    }

    private void doThirdLogin(int type ,ThirdLoginCallback callback){
        if(mThirdLogin != null)
            mThirdLogin.login(callback);
    }

    private void init(int type){
        switch (type){
            case ThirdLoginType.QQ:
                initQQLoginObj();
                break;
            case ThirdLoginType.WECHAT:
                initWeChatLoginObj();
                break;
            default:
                break;
        }
    }

    private void initQQLoginObj(){
        mThirdLogin = ThirdLoginFactory.build(ThirdLoginType.QQ);
        Map<String, String> map = new HashMap<String, String>();
        map.put(ThirdLoginConstants.CLIENTID, SuishiThirdConstant.QQ_APP_ID);
        mThirdLogin.init(map, mActivity);
    }

    private void initWeChatLoginObj(){
        mThirdLogin = ThirdLoginFactory.build(ThirdLoginType.WECHAT);
        Map<String, String> map = new HashMap<String, String>();
        map.put(ThirdLoginConstants.CLIENTID, SuishiThirdConstant.WX_APPID);
        map.put(ThirdLoginConstants.SECRET, SuishiThirdConstant.WX_APP_SECRET);
        mThirdLogin.init(map, mActivity);

        IWXAPI iwxapi = ThirdLoginWeChat.getWxAPi();
        if(null != iwxapi){
            if(!iwxapi.isWXAppInstalled()){
                ToastUtils.showShort(CommonApplication.getContext().getString(R.string.wechat_not_installed));
                return;
            }
        }
    }



}
