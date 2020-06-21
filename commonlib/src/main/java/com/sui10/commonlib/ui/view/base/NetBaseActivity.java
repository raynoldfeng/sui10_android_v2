package com.sui10.commonlib.ui.view.base;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sui10.commonlib.network.NetChangeListener;
import com.sui10.commonlib.ui.manager.RxLifeManager.RxActivityLifeManager;

/**
 * 处理 网络相关逻辑
 * 1、无网络、网络错误等相关的UI展示等
 * 2、RxJava的内存泄露
 * TODO... 无网络时，UI展示
 */

public abstract class NetBaseActivity extends AppCompatActivity {
    protected static final String TAG = "RxBaseActivity";
    //网络状态
    protected NetChangeListener mNetChangeObserver = null;

    protected RxActivityLifeManager mRxActivityLifeManager;


    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tryMonitorNetWork();

        mRxActivityLifeManager = RxActivityLifeManager.getInstance();
        mRxActivityLifeManager.onCreate(savedInstanceState);
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        if(mRxActivityLifeManager != null)
            mRxActivityLifeManager.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mRxActivityLifeManager != null)
            mRxActivityLifeManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mRxActivityLifeManager != null)
            mRxActivityLifeManager.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mRxActivityLifeManager != null)
            mRxActivityLifeManager.onStop();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        if(needMonitorNetWork()){
            mNetChangeObserver = null;
        }
    }

    private void tryMonitorNetWork()
    {
        if (needMonitorNetWork()) {
            mNetChangeObserver = new NetChangeListener() {
                @Override
                public void onNetConnected(int type) {
                    onNetworkConnected(type);
                }

                @Override
                public void onNetDisConnect() {
                    onNetworkDisConnected();
                }
            };
        }
    }

    /**
     * 是否需要监听网络
     * @return
     */
    protected abstract boolean needMonitorNetWork();

    /**
     * 网络已经连接
     */
    protected abstract void onNetworkConnected(int type);

    /**
     * 网络已经断开
     */
    protected abstract void onNetworkDisConnected();
}
