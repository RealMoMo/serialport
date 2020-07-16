package com.newline.serialport.setting.observer.observer.contentobserver;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;

import com.newline.serialport.setting.observer.ProxyDeviceListener;
import com.newline.serialport.setting.observer.observer.HHTObserver;


/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/9/25 15:14
 * @describe
 */
public abstract class BaseContentObserver extends ContentObserver implements HHTObserver {

    private static final Handler H = new Handler(Looper.getMainLooper());
    protected ProxyDeviceListener proxyDeviceListener;
    protected Context mContext;

    public BaseContentObserver(Context context) {
        super(H);
        mContext = context.getApplicationContext();

    }


    @Override
    public void unregisterObserver() {
        mContext.getContentResolver().unregisterContentObserver(this);
        mContext = null;
        proxyDeviceListener = null;
    }

    @Override
    public int getHHTObserverType() {
        return HHTObserverType.DB_URI;
    }


    @Override
    public void setProxyDeviceListner(ProxyDeviceListener listner) {
        this.proxyDeviceListener = listner;
    }
}
