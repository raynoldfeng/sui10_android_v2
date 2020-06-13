package com.sui10.commonlib.ui.view.widget;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sui10.commonlib.network.NetChangeListener;
import com.sui10.commonlib.network.manager.NetStateReceiver;
import com.sui10.commonlib.ui.manager.RxLifeManager.RxFragmentLifeManager;

/*
*brief:处理网络相关逻辑
 * 1、无网的检测回调
 * 2、RxJava的内存泄露
 * TODO... 无网络时，UI展示
 */

public abstract class NetBaseFragment extends Fragment {

    private final static String TAG = "NetBaseFragment";
    //网络状态
    protected NetChangeListener mNetChangeObserver = null;

    protected RxFragmentLifeManager mRxFragmentLifeManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxFragmentLifeManager = RxFragmentLifeManager.getInstance();
        mRxFragmentLifeManager.onCreate(savedInstanceState);

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

    @Override
    public void onStart() {
        super.onStart();
        if(mRxFragmentLifeManager != null)
            mRxFragmentLifeManager.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mRxFragmentLifeManager != null)
            mRxFragmentLifeManager.onResume();
        if (needMonitorNetWork()){
            NetStateReceiver.registerNetworkStateReceiver(getActivity());
        }
        NetStateReceiver.registerObserver(mNetChangeObserver);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RxFragmentLifeManager.getInstance().onAttach();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mRxFragmentLifeManager != null)
            mRxFragmentLifeManager.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mRxFragmentLifeManager != null)
            mRxFragmentLifeManager.onPause();
        if (needMonitorNetWork()) {
            NetStateReceiver.unRegisterNetworkStateReceiver(getActivity());
        }
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mRxFragmentLifeManager != null)
            mRxFragmentLifeManager.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNetChangeObserver = null;
        if(mRxFragmentLifeManager != null)
            mRxFragmentLifeManager.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mRxFragmentLifeManager != null)
            mRxFragmentLifeManager.onDestroyView();
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
