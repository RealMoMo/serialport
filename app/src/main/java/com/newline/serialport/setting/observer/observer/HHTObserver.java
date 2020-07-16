package com.newline.serialport.setting.observer.observer;

import android.support.annotation.IntDef;


import com.newline.serialport.setting.observer.ProxyDeviceListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/9/25 16:46
 * @describe
 */
public interface HHTObserver {

    @IntDef({HHTObserverType.BROADCAST, HHTObserverType.DB_URI})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HHTObserverType {
        int BROADCAST = 0;
        int DB_URI = 1;

    }


    void registerObserver();

    void unregisterObserver();

    int getHHTObserverType();

    void setProxyDeviceListner(ProxyDeviceListener listner);
}
