package com.sui10.commonlib.thirdparty.login;

public class ThirdLoginFactory{

    public static ThirdLogin build(int type){

        String className = null;
        switch (type){
            case ThirdLoginType.QQ:
                className = "com.sui10.commonlib.thirdparty.login.local.ThirdLoginQQ";
                break;
            case ThirdLoginType.WECHAT:
                className = "com.sui10.commonlib.thirdparty.login.local.ThirdLoginWeChat";
                break;
            default:
                break;
        }

        if(className == null)
            return null;

        try{
            Class<?> thirdLoginClass = Class.forName(className);
            return (ThirdLogin) thirdLoginClass.newInstance();
        }catch (Exception e){
            return null;
        }

    }
}
