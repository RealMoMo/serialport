package com.newline.serialport.setting.observer.observer.contentobserver.platform.mstar;

import android.content.Context;
import android.net.Uri;

import com.newline.serialport.setting.observer.observer.contentobserver.BaseContentObserver;


/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/9/25 15:25
 * @describe
 */
public class VolumeMstarV21Observer extends BaseContentObserver {

    public VolumeMstarV21Observer(Context context) {
        super(context);
    }

    @Override
    public void registerObserver() {
        mContext.getContentResolver().registerContentObserver(
                Uri.parse("content://mstar.tv.usersetting/soundsetting/Volume")
                , true, this);
    }


    @Override
    public void onChange(boolean selfChange) {
        if(proxyDeviceListener != null){
            proxyDeviceListener.onVolumeChange();
        }
    }
}
