package com.sui10.suishi.module.login.mvp;

import com.sui10.commonlib.base.constants.NetConstant;
import com.sui10.commonlib.log.LogManager;
import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.utils.ToastUtils;
import com.sui10.suishi.common.net.models.LoginModels;
import com.sui10.suishi.common.ui.rsp.CommonRsp;
import com.sui10.suishi.module.login.bean.rsp.SmsLoginRsp;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<ILoginView> implements ILoginPresenter {
    private static String TAG = "LoginPresenter";

    @Override
    public void getSmsCode(String phone) {
        addDisposable(LoginModels.getSmsCode(phone)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommonRsp>() {
            @Override
            public void accept(CommonRsp commonRsp) throws Exception {
                if(getView() != null){
                    getView().onGetSmsCodeRsp(commonRsp);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogManager.e(TAG,"getSmsCode error "+throwable.getMessage());
            }
        }));
    }

    @Override
    public void loginBySmsCode(String phone, String smsCode) {
        addDisposable(LoginModels.loginBySmsCode(phone,smsCode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SmsLoginRsp>() {
                    @Override
                    public void accept(SmsLoginRsp smsLoginRsp) throws Exception {
                        if(smsLoginRsp.getCode() == NetConstant.RSP_CODE.OK){
                            if(getView() != null){
                                getView().onLoginSucess(smsLoginRsp.smsLoginData.token,smsLoginRsp.smsLoginData.account);
                            }
                        }else {
                            ToastUtils.showShort(smsLoginRsp.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogManager.e(TAG,"loginBySmsCode error "+throwable.getMessage());
                    }
                }));
    }
}
