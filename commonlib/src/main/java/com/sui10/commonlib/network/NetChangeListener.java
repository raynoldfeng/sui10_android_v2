package com.sui10.commonlib.network;

public interface NetChangeListener {

    /**
     * 网络连接时调用
     */
    void onNetConnected(int type);

    /**
     *  网络断开时回调
     */
    void onNetDisConnect();
}
