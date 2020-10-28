package com.sui10.suishi.module.usersystem.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.base.BaseFragment;
import com.sui10.suishi.R;
import com.sui10.suishi.common.utils.ImageLoadUtils;
import com.sui10.suishi.manager.UserManager;
import com.sui10.suishi.module.login.bean.AccountInfo;

import butterknife.BindView;

public class AccountInfoSettingFragment extends BaseFragment {

    @BindView(R.id.personal_nick)
    TextView mNickTv;
    @BindView(R.id.personal_gender)
    TextView mGenderTv;
    @BindView(R.id.personal_birth)
    TextView mBirthTv;
    @BindView(R.id.personal_signature)
    TextView mPersonalSignatureTv;
    @BindView(R.id.personal_portrait)
    ImageView mPortraitIv;



    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_account_setting;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initViewsAndEvents() {
        setTitle(R.string.main_menu_user_info);
        initView();
    }

    private void initView(){
        if(UserManager.getInstance().isLogin()){
            AccountInfo accountInfo = UserManager.getInstance().getAccountInfo();
            mPersonalSignatureTv.setText(accountInfo.identification);
            mNickTv.setText(accountInfo.nick);
            if(!TextUtils.isEmpty(accountInfo.avatar))
                ImageLoadUtils.loadCircleImg(accountInfo.avatar,mPortraitIv);
            mGenderTv.setText(accountInfo.gender);
        }
    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
