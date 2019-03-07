package com.comsince.github.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * Created by zbin on 17-6-13.
 */

public class Network {
    private static NetworkInfo activeInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            return cm.getActiveNetworkInfo();
        }
        return null;
    }

    public static boolean isWifi(Context context) {
        NetworkInfo info = activeInfo(context);
        return info != null &&
                info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isConnected(Context context) {
        NetworkInfo info = activeInfo(context);
        return info != null &&
                info.isConnected();
    }

    private static String key(Context context, NetworkInfo info) {
        String key = null;
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wm = (WifiManager)
                        context.getSystemService(Context.WIFI_SERVICE);
                if (wm != null) {
                    key = wm.getConnectionInfo().getSSID();
                }
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                TelephonyManager tm = (TelephonyManager)
                        context.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY) {
                    key = tm.getSimOperator();
                }
            }
        }
        return key;
    }

    public static String key(Context context) {
        return key(context, activeInfo(context));
    }

    public static Info info(Context context) {
        Info info = new Info();
        NetworkInfo networkInfo = activeInfo(context);
        if (networkInfo != null) {
            info.key = key(context, networkInfo);
            info.isConnected = networkInfo.isConnected();
            info.type = networkInfo.getType();
            info.subType = networkInfo.getSubtype();
            info.isWifi = (info.type == ConnectivityManager.TYPE_WIFI);
        }
        return info;
    }

    public static class Info {
        public String key;
        public boolean isConnected;
        public int type;
        public int subType;
        public boolean isWifi;
    }
}
