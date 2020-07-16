package com.newline.serialport.setting.observer.observer.contentobserver.platform.standard;

import android.content.Context;
import android.provider.Settings;

import com.newline.serialport.setting.observer.observer.contentobserver.BaseContentObserver;


/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/9/25 14:39
 * @describe
 */
public class BrightnessObserver extends BaseContentObserver {


    public BrightnessObserver(Context context) {
        super(context);
    }



    @Override
    public void registerObserver() {
        mContext.getContentResolver().registerContentObserver(
                Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS)
                , true, this);
    }

    @Override
    public void onChange(boolean selfChange) {
        if(proxyDeviceListener != null){
            proxyDeviceListener.onBrightnessChange();
        }
    }
}
