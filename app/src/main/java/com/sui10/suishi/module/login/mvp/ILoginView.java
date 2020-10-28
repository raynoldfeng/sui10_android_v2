package com.sui10.suishi.module.login.mvp;

import com.sui10.suishi.common.ui.rsp.CommonRsp;
import com.sui10.suishi.module.login.bean.AccountInfo;

public interface ILoginView {
    void onGetSmsCodeRsp(CommonRsp commonRsp);
    void onLoginSucess(String token, AccountInfo accountInfo);
}
