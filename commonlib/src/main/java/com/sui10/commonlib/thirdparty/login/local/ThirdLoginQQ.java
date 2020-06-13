package com.sui10.commonlib.thirdparty.login.local;

import android.app.Activity;
import android.content.Intent;

import com.sui10.commonlib.thirdparty.login.ThirdLogin;
import com.sui10.commonlib.thirdparty.login.ThirdLoginCallback;
import com.sui10.commonlib.thirdparty.login.ThirdLoginConstants;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.Map;

/*
* breif :QQ的登录会返回到activity的onActivityResult中，再进行处理；
* 因此需要外部调用的activity回调。
 */

public class ThirdLoginQQ implements ThirdLogin {

    private Tencent mTencent = null;
    private Activity mActivity = null;
    private IUiListener mUiListener = null;
    private ThirdLoginCallback mLoginCallback;

    @Override
    public void init(Map<String, String> param, Activity activity) {
        mActivity = activity;
        if(param != null && param.containsKey(ThirdLoginConstants.CLIENTID)){
            String appid = param.get(ThirdLoginConstants.CLIENTID);
            mTencent = Tencent.createInstance(appid,activity);
            if(mTencent != null){
                mUiListener = new IUiListener() {
                    @Override
                    public void onComplete(Object o) {
                        JSONObject responseJsonobject = (JSONObject) o;
                        final String openid = responseJsonobject.optString("openid");
                        final String access_token = responseJsonobject.optString("access_token");
                        final String expires_in = responseJsonobject.optString("expires_in");

                        QQToken qqToken = mTencent.getQQToken();
                        mTencent.setOpenId(openid);
                        mTencent.setAccessToken(access_token, expires_in);
                        UserInfo info = new UserInfo(mActivity.getApplicationContext(), qqToken);

                        info.getUserInfo(new IUiListener() {
                            @Override
                            public void onComplete(Object o) {
                                String nickname = ((JSONObject) o).optString("nickname");
                                String sexStr = ((JSONObject) o).optString("sex");
                                String headImg = ((JSONObject) o).optString("figureurl_qq_2");
                                successCallBack(openid, nickname, headImg, access_token );
                            }

                            @Override
                            public void onError(UiError uiError) {
                                successCallBack(openid, "", "", access_token );
                            }

                            @Override
                            public void onCancel() {
                                successCallBack(openid, "", "", access_token );
                            }
                        });
                    }

                    @Override
                    public void onError(UiError uiError) {
                        failuerCallBack();
                    }

                    @Override
                    public void onCancel() {
                        cancleCallBack();
                    }
                };
            }
        }
    }

    @Override
    public void login(ThirdLoginCallback callback) {
        mLoginCallback = callback;
        mTencent.login(mActivity, "all", mUiListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTencent.onActivityResultData(requestCode, resultCode, data, mUiListener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_QQ_SHARE ||
                    resultCode == Constants.REQUEST_QZONE_SHARE ||
                    resultCode == Constants.REQUEST_OLD_SHARE) {
                mTencent.handleResultData(data, mUiListener);
            }
        }
    }

    void successCallBack(String uId, String nick, String avatar, String accessToken){
        if( mLoginCallback != null )
            mLoginCallback.success(uId, nick, avatar, accessToken);
    }

    void cancleCallBack(){
        if( mLoginCallback != null )
            mLoginCallback.cancle();
    }

    void failuerCallBack(){
        if( mLoginCallback != null )
            mLoginCallback.failure();
    }
}
