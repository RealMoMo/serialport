package com.newline.serialport.setting.impl;


import com.newline.serialport.setting.i.FullStatusListener;

/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/9/26 12:40
 * @describe
 */
public class FullStatusListenerEmptyImpl implements FullStatusListener {
    @Override
    public void onFreezeChange(boolean isFreeze) {

    }

    @Override
    public void onCurrentSourceChange(String sourceType) {

    }

    @Override
    public void onCurrentSourceChange(int sourceType) {

    }

    @Override
    public void onMuteChange(boolean mute) {

    }

    @Override
    public void onVolumeChange(int value) {

    }

    @Override
    public void onBrightnessChange(int value) {

    }


}
