package com.newline.serialport.setting.observer;

/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/9/25 20:24
 * @describe
 */
public interface ProxyDeviceListener {

    void onFreezeChange();

    void onCurrentSourceChange();

    void onMuteChange();

    void onVolumeChange();

    void onBrightnessChange();


}
