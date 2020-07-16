package com.newline.serialport.setting.i;

/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/9/25 9:52
 * @describe 鸿合特有功能状态监听接口
 */
public interface HHTDeviceStatusListener extends BaseDeviceStatusListener{

    /**
     * 冻屏状态变化
     * @param isFreeze 是否冻屏中
     */
    void onFreezeChange(boolean isFreeze);

    /**
     * 信号源变化
     * @param sourceType  信号源类型
     */
    void onCurrentSourceChange(String sourceType);

    /**
     * 信号源变化
     * @param sourceType 信号源类型对应底层的Enum.ordinal
     */
    void onCurrentSourceChange(int sourceType);



}
