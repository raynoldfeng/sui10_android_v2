package com.sui10.commonlib.thirdparty.login.local;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.sui10.commonlib.log.LogManager;
import com.sui10.commonlib.thirdparty.login.HttpUtil;
import com.sui10.commonlib.thirdparty.login.ThirdLogin;
import com.sui10.commonlib.thirdparty.login.ThirdLoginCallback;
import com.sui10.commonlib.thirdparty.login.ThirdLoginConstants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/*
*微信登录必须在app包的路径下新建wxapi目录下并且新增一个
* WXEntryActivity.java文件；授权结果会回调WXEntryActivity中的onResp
* 因此外层业务需要继承ThirdLoginWeChatActivity并回调onResp
*
* 流程：授权 -> 获取token -> 获取用户信息
 */
public class ThirdLoginWeChat implements ThirdLogin,ThirdLoginWeChatActivity.WeChatLoginCallback {

    private  static final String TAG = "ThirdLoginWeChat";
    private  String mWxAppid;
    private  String mWxAppSecret;
    private  String mWxCode;
    private static IWXAPI mWxAPi;
    private ThirdLoginCallback mLoginCallback;

    //微信请求token
    private static final String URL_WX_REQUEST_ACCESS_TOKEN ="https://api.weixin.qq.com/sns/oauth2/access_token";
    //微信请求用户信息
    private static final String URL_WX_GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo";

    public static IWXAPI getWxAPi(){
        return mWxAPi;
    }

    @Override
    public void init(Map<String, String> param, Activity activity) {
        if(param != null){
            mWxAppid = param.get(ThirdLoginConstants.CLIENTID);
            mWxAppSecret = param.get(ThirdLoginConstants.SECRET);

            if(mWxAPi != null){
                mWxAPi.unregisterApp();
                mWxAPi.detach();
            }
            mWxAPi = WXAPIFactory.createWXAPI(activity, mWxAppid, true);
            mWxAPi.registerApp(mWxAppid);
            ThirdLoginWeChatActivity.init(mWxAPi,this);
        }
    }

    @Override
    public void login(ThirdLoginCallback callback) {
        mLoginCallback = callback;
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "suishi_wechat_login";
        mWxAPi.sendReq(req);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        mWxCode = ((SendAuth.Resp) baseResp).code;
                        if(mWxCode == null) {
                            failuerCallBack();
                        }
                        else {
                            userInfoRequestThread.start();
                            try {
                                userInfoRequestThread.join();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                cancleCallBack();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                cancleCallBack();
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                failuerCallBack();
                break;
            default:
                break;
        }
    }

    Thread userInfoRequestThread = new Thread(new Runnable() {
        @Override
        public void run() {
            requestWxToken(mWxCode);
        }
    });

    //请求微信token
    private void requestWxToken(final String code){
        Log.e(TAG,"code:" +code);
        try{
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("appid",  mWxAppid);
            params.put("secret", mWxAppSecret);
            params.put("code", code);
            params.put("grant_type", "authorization_code");
            HttpUtil.GetHttpRequest(URL_WX_REQUEST_ACCESS_TOKEN, params, new okhttp3.Callback() {
                public void onFailure(Call call, IOException e) {
                    failuerCallBack();
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String data = response.body().string();
                    JSONObject jsonObject = null;
                    LogManager.d(TAG,"requestWxToken rsp:" +data);
                    try {
                        jsonObject = new JSONObject(data);
                        String accessToken = jsonObject.getString("access_token");
                        String expiresIn = jsonObject.getString("expires_in");
                        String refreshToken = jsonObject.getString("refresh_token");
                        String openId = jsonObject.getString("openid");
                        String scope = jsonObject.getString("scope");
                        String unionid = jsonObject.getString("unionid");
                        requestWxUserInfo(accessToken,openId);

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }});
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //请求微信用户信息
    private void requestWxUserInfo(final String accessToken , final String openId) {

        LogManager.d(TAG,"requestWxUserInfo token:" +accessToken+"openId :"+openId);
        try{
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("access_token", accessToken);
            params.put("openid",  openId);
            HttpUtil.GetHttpRequest(URL_WX_GET_USERINFO, params, new okhttp3.Callback() {
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    failuerCallBack();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String data = response.body().string();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(data);
                        String nickname = jsonObject.getString("nickname");
                        int sex = Integer.parseInt(jsonObject.get("sex").toString());
                        String headimgurl = jsonObject.getString("headimgurl");
                        String openid1 = jsonObject.getString("openid");
                        String province = jsonObject.getString("province");
                        String city = jsonObject.getString("city");
                        String country = jsonObject.getString("country");
                        LogManager.d(TAG,"requestWxUserInfo nick:" +nickname);
                        successCallBack(openId,nickname,headimgurl,accessToken);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
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
