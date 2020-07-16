package com.newline.serialport.setting.observer.observer.contentobserver.platform.standard;

import android.content.Context;
import android.provider.Settings;

import com.newline.serialport.setting.observer.observer.contentobserver.BaseContentObserver;


/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/9/25 15:10
 * @describe
 */
public class MuteV21Observer extends BaseContentObserver {

    public MuteV21Observer(Context context) {
        super(context);
    }

    @Override
    public void registerObserver() {
        mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("volume_master_mute"),false,this);
    }

    @Override
    public void onChange(boolean selfChange) {
        proxyDeviceListener.onMuteChange();
    }
}
