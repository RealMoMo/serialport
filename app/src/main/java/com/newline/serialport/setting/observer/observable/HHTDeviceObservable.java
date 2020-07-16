package com.newline.serialport.setting.observer.observable;

import android.database.Observable;

import com.newline.serialport.setting.observer.ProxyDeviceListener;
import com.newline.serialport.setting.observer.observer.HHTObserver;


/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/9/25 14:41
 * @describe
 */
public class HHTDeviceObservable extends Observable<HHTObserver> {

    private ProxyDeviceListener proxyDeviceListener;

    public void setProxyDeviceListener(ProxyDeviceListener proxyDeviceListener) {
        this.proxyDeviceListener = proxyDeviceListener;
    }

    @Override
    public void registerObserver(HHTObserver observer) {
        super.registerObserver(observer);
        observer.registerObserver();
        observer.setProxyDeviceListner(proxyDeviceListener);
    }

    @Override
    public void unregisterObserver(HHTObserver observer) {
        super.unregisterObserver(observer);
        observer.unregisterObserver();
    }

    @Override
    public void unregisterAll() {
        for (HHTObserver hhtObserver : mObservers) {
            hhtObserver.unregisterObserver();
        }
        super.unregisterAll();
    }
}
