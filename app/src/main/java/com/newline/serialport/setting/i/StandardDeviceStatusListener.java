package com.newline.serialport.setting.i;

/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/4/10 10:27
 * @describe  标准Android平台 状态监听接口
 */
public interface StandardDeviceStatusListener extends BaseDeviceStatusListener{
    /**
     * 静音状态变化
     * @param mute 是否静音
     */
    void onMuteChange(boolean mute);

    /**
     * 音量大小变化
     * @param value  当前音量
     */
    void onVolumeChange(int value);

    /**
     * 亮度大小变化
     * @param value  当前亮度
     */
    void onBrightnessChange(int value);

}
