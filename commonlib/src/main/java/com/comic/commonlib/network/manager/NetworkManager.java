package com.comic.commonlib.network.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.comic.commonlib.base.CommonApplication;
import com.comic.commonlib.utils.CommonUtils;

import java.util.Locale;


import static android.telephony.TelephonyManager.NETWORK_TYPE_1xRTT;
import static android.telephony.TelephonyManager.NETWORK_TYPE_CDMA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EDGE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EHRPD;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_0;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_A;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_B;
import static android.telephony.TelephonyManager.NETWORK_TYPE_GPRS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSDPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPAP;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSUPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_IDEN;
import static android.telephony.TelephonyManager.NETWORK_TYPE_LTE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_UMTS;


public class NetworkManager {
    public static final int NETWORK_CLASS_UNKNOWN=0;
    public static final int WIFI = 1;
    public static final int NONE = 4;
    public static final int NETWORK_CLASS_2_G = 5; //2G
    public static final int NETWORK_CLASS_3_G = 6; //3G
    public static final int NETWORK_CLASS_4_G = 7; //4G

    public static boolean isNetworkAvailable(Context context) {
        if(context != null) {
            ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] info;
            if (mgr != null && (info = mgr.getAllNetworkInfo()) != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo;
            if (mConnectivityManager != null && (mNetworkInfo = mConnectivityManager.getActiveNetworkInfo()) != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (null == mWiFiNetworkInfo){
                return false;
            }else {
                if (mWiFiNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    public static int getAPNType(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NONE;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            TelephonyManager telephonyManager = (TelephonyManager) CommonApplication.getContext().getSystemService(
                    Context.TELEPHONY_SERVICE);
            int networkType = NETWORK_CLASS_UNKNOWN;
            switch (telephonyManager.getNetworkType()) {
                case NETWORK_TYPE_GPRS:
                case NETWORK_TYPE_EDGE:
                case NETWORK_TYPE_CDMA:
                case NETWORK_TYPE_1xRTT:
                case NETWORK_TYPE_IDEN:
                    networkType= NETWORK_CLASS_2_G;
                    break;
                case NETWORK_TYPE_UMTS:
                case NETWORK_TYPE_EVDO_0:
                case NETWORK_TYPE_EVDO_A:
                case NETWORK_TYPE_HSDPA:
                case NETWORK_TYPE_HSUPA:
                case NETWORK_TYPE_HSPA:
                case NETWORK_TYPE_EVDO_B:
                case NETWORK_TYPE_EHRPD:
                case NETWORK_TYPE_HSPAP:
                    networkType =  NETWORK_CLASS_3_G;
                    break;
                case NETWORK_TYPE_LTE:
                    networkType =  NETWORK_CLASS_4_G;
                    break;
            }
            return networkType;
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return WIFI;
        }
        return NONE;
    }

    public static String getAPNTypeForWS(Context context) {
        int type = getAPNType(context);
        switch (type) {
            case WIFI:
                return "WIFI";
            case NETWORK_CLASS_2_G:
                return "2G";
            case NETWORK_CLASS_3_G:
                return "3G";
            case NETWORK_CLASS_4_G:
                return "4G";
            default:
                return "other";
        }
    }

    public static String getAPNTypeName(Context context) {
        int type = getAPNType(context);
        switch (type) {
            case WIFI:
                return "WIFI";
            case NETWORK_CLASS_2_G:
                return "2G";
            case NETWORK_CLASS_3_G:
                return "3G";
            case NETWORK_CLASS_4_G:
                return "4G";
            default:
                return "Unknown";
        }
    }

    public static boolean getNetWorkChanged(){
        return NetStateReceiver.mNetWorkChanged;
    }


    public static String getNetWorkTypeName() {
        ConnectivityManager connMgr = (ConnectivityManager) CommonApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return "";
        }
        int nType = networkInfo.getType();

        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (!CommonUtils.isEmpty(networkInfo.getExtraInfo())) {
                if (networkInfo.getExtraInfo().toLowerCase(Locale.getDefault()).equals("cmnet")) {
                    return "CMNET";
                }else {
                    return "CMWAP";
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return "WIFI";
        }
        return "";
    }
}

