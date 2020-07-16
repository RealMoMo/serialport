package com.newline.serialport.setting.observer.observer.broadcastobserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import com.newline.serialport.setting.observer.ProxyDeviceListener;
import com.newline.serialport.setting.observer.observer.HHTObserver;


/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/9/9 14:40
 * @describe
 */
public abstract class BaseBroadcastReceiver extends BroadcastReceiver implements HHTObserver {

    protected Context mContext;

    protected ProxyDeviceListener proxyDeviceListener;

    public BaseBroadcastReceiver(Context context) {
        mContext = context.getApplicationContext();

    }


    protected abstract IntentFilter initReceiver();

    @Override
    public void setProxyDeviceListner(ProxyDeviceListener listner) {
        proxyDeviceListener = listner;
    }

    @Override
    public void registerObserver() {
        mContext.registerReceiver(this, initReceiver());
    }



    @Override
    public void unregisterObserver() {
        if(mContext !=null){
            mContext.unregisterReceiver(this);
            mContext = null;
        }
    }

    @Override
    public int getHHTObserverType() {
        return HHTObserverType.BROADCAST;
    }
}
