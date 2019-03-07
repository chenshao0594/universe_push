package com.comsince.github;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;

import com.comsince.github.utils.Network;
import com.meizu.cloud.pushinternal.DebugLogger;

public class NetworkService {
    private static final String tag = "NetworkService";
    private Context context;
    private ConnectService connectService;
    private NetworkReceiver mNetworkReceiver;

    public NetworkService(Context context) {
        this.context = context;
    }

    public NetworkService(Context context, ConnectService connectService) {
        this(context);
        this.connectService = connectService;
    }

    public void start(){
        DebugLogger.i(tag, "start network service");
        mNetworkReceiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction("android.intent.action.ANY_DATA_STATE");
        context.registerReceiver(mNetworkReceiver, filter);
    }

    public void stop(){
        DebugLogger.i(tag, "stop network service");
        context.unregisterReceiver(mNetworkReceiver);
    }


    public enum Type {
        NULL(-1),
        WIFI(4),
        BLUETOOTH(7),
        MOBILE_2G(1),
        MOBILE_3G(2),
        MOBILE_4G(3),
        MOBILE_XG(8),
        ETHERNET(9),
        OTHER(0);

        private int mValue;

        Type(int value) {
            mValue = value;
        }

        public int value() {
            return mValue;
        }
    }

    private class NetworkReceiver extends BroadcastReceiver {

        private Type type(Network.Info info) {
            switch (info.type) {
                case ConnectivityManager.TYPE_WIFI:
                    return Type.WIFI;
                case ConnectivityManager.TYPE_ETHERNET:
                    return Type.ETHERNET;
                case ConnectivityManager.TYPE_BLUETOOTH:
                    return Type.BLUETOOTH;
                case ConnectivityManager.TYPE_MOBILE: {
                    switch (info.subType) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return Type.MOBILE_2G;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return Type.MOBILE_3G;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return Type.MOBILE_4G;
                        default:
                            return Type.MOBILE_XG;
                    }
                }
                default:
                    return Type.OTHER;
            }
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            DebugLogger.d(tag, "on receive network changed");
            Network.Info info = Network.info(context);

            if(info.isConnected){
                connectService.connect();
            } else {
                connectService.stop();
            }

        }
    }
}
