package com.newline.serialport.setting.observer.observer.contentobserver.platform.newline;

import android.content.Context;
import android.provider.Settings;

import com.newline.serialport.setting.observer.observer.contentobserver.BaseContentObserver;
import com.newline.serialport.setting.utils.SettingConstant;


/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/9/25 22:28
 * @describe
 */
public class FreezeObserver extends BaseContentObserver {

    public FreezeObserver(Context context) {
        super(context);
    }

    @Override
    public void registerObserver() {
        mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingConstant.FIELD_IS_FROZEN),true, this);
    }

    @Override
    public void onChange(boolean selfChange) {
        proxyDeviceListener.onFreezeChange();
    }
}
