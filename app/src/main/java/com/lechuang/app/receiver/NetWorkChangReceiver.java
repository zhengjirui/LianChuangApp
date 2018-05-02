package com.lechuang.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.lechuang.app.events.EventBusUtils;
import com.lechuang.app.events.NetStateLisenter;
import com.lechuang.app.utils.Logger;

/**
 * @author: LGH
 * @since: 2018/5/2
 * @describe:
 */

public class NetWorkChangReceiver extends BroadcastReceiver {
    private final String TAG = "TAG_NetWorkChangReceiver";
    /**
     * 获取连接类型
     *
     * @param type
     * @return
     */
    private String getConnectionType(int type) {
        String connType = "";
        if (type == ConnectivityManager.TYPE_MOBILE) {
            connType = "移动网络数据";
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            connType = "WIFI网络数据";
        } else if(type == ConnectivityManager.TYPE_BLUETOOTH){
            connType = "蓝牙共享数据";
        }
        return connType;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {// 监听wifi的打开与关闭，与wifi的连接无关
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            Logger.e(TAG, "wifiState:" + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    break;
            }
        }
        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            //获取联网状态的NetworkInfo对象
            NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (info != null) {
                //如果当前的网络连接成功并且网络连接可用
                String type = getConnectionType(info.getType());
                if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI || info.getType() == ConnectivityManager.TYPE_MOBILE) {

                        NetStateLisenter netStateLisenter = new NetStateLisenter();
                        netStateLisenter.connType = type;
                        netStateLisenter.netState = true;
                        EventBusUtils.getInstance().post(netStateLisenter);
                        Logger.i(TAG, type + "连接！");
                    }
                } else {
                    NetStateLisenter netStateLisenter = new NetStateLisenter();
                    netStateLisenter.connType = "";
                    netStateLisenter.netState = false;
                    EventBusUtils.getInstance().post(netStateLisenter);
                    Logger.i(TAG, type + "断开!");
                }
            }
        }
    }
}
