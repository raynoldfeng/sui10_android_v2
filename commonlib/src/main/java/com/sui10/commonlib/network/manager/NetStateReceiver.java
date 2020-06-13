package com.sui10.commonlib.network.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.sui10.commonlib.network.NetChangeListener;

import java.util.ArrayList;

public class NetStateReceiver extends BroadcastReceiver {
    private final static String TAG = "NetStateReceiver";
    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";


    private static boolean isNetAvailable = false;
    public static volatile boolean mNetWorkChanged = false;
    private static int mNetType;
    private static ArrayList<NetChangeListener> mNetChangeObservers = new ArrayList<NetChangeListener>();
    private volatile static NetStateReceiver mInstance = null;
    private NetStateReceiver (){
    }
    public static NetStateReceiver getInstance(){
        if (mInstance==null) {
            synchronized (NetStateReceiver.class) {
                if (mInstance == null) {
                    mInstance = new NetStateReceiver();
                }
            }
        }
        return mInstance;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)) {
            if (!NetworkManager.isNetworkAvailable(context)) {
                isNetAvailable = false;
                mNetWorkChanged = true;
            } else {
                if (!isNetAvailable){
                    mNetWorkChanged = true;
                }else {
                    mNetWorkChanged = false;
                }
                isNetAvailable = true;
                mNetType = NetworkManager.getAPNType(context);
            }
            if (mNetWorkChanged) {
                notifyObserver();
            }
        }
    }

    public static void registerNetworkStateReceiver(Context mContext) {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            mContext.registerReceiver(getInstance(), filter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void unRegisterNetworkStateReceiver(Context mContext) {
        if (mInstance != null) {
            try {
                mContext.unregisterReceiver(mInstance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isNetworkAvailable() {
        return isNetAvailable;
    }

    public static int getAPNType() {
        return mNetType;
    }

    private void notifyObserver() {
        if (mNetChangeObservers!=null && !mNetChangeObservers.isEmpty()) {
            int size = mNetChangeObservers.size();
            for (int i = 0; i < size; i++) {
                NetChangeListener observer = mNetChangeObservers.get(i);
                if (observer != null && mNetWorkChanged) {
                    if (isNetworkAvailable()) {
                        observer.onNetConnected(mNetType);
                    } else {
                        observer.onNetDisConnect();
                    }
                }
            }
        }
    }

    public static void registerObserver(NetChangeListener observer) {
        if (mNetChangeObservers == null) {
            mNetChangeObservers = new ArrayList<>();
        }
        if (!mNetChangeObservers.contains(observer)) {
            mNetChangeObservers.add(observer);
        }
    }

    public static void removeRegisterObserver(NetChangeListener observer) {
        if (mNetChangeObservers != null) {
            if (mNetChangeObservers.contains(observer)) {
                mNetChangeObservers.remove(observer);
            }
        }
    }

    public static void clearResource() {
        if (mNetChangeObservers != null) {
            mNetChangeObservers.clear();
            mNetChangeObservers = null;
        }
        mInstance = null;

    }
}
