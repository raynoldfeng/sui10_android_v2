package com.sui10.suishi.common.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.widget.BaseActivity;
import com.sui10.commonlib.ui.view.widget.BaseFragment;
import com.sui10.commonlib.utils.CommonUtils;
import com.sui10.suishi.R;

public class FragContainerActivity extends BaseActivity {

    private static final String KEY_FRAG_CLASS = "key_frag_class";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_common_frag_container);
        String fragClz = getIntent().getStringExtra(KEY_FRAG_CLASS);
        if(!CommonUtils.isEmpty(fragClz)){
            try {
                Fragment fragment = (Fragment) Class.forName(fragClz).newInstance();
                fragment.setArguments(getIntent().getExtras());
                addFragment(R.id.frag_container,fragment,fragClz,false);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void start(Context context, Class fragClz, Bundle outArgs){
        start(context,fragClz,"",outArgs);
    }

    public static void start(Context context, Class fragClz, String title, Bundle outArgs){
        Intent intent = getIntent(context, fragClz, title, outArgs);
        context.startActivity(intent);
    }

    public static Intent getIntent(Context context, Class fragClz, String title, Bundle outArgs) {
        Intent intent = new Intent(context,FragContainerActivity.class);
        if (outArgs != null) {
            intent.putExtras(outArgs);
        }
        intent.putExtra(KEY_FRAG_CLASS,fragClz.getName());
        intent.putExtra(BaseFragment.KEY_FRAG_TITLE,title);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return intent;

    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected void onNetworkConnected(int type) {

    }
}
