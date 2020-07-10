package com.newline.serialport.utils;

import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;

public class GPIOUtils {
    private static final int INT_LED_RED  = 8;// * #define LED_RED 8   TRUE 红色   FALSE 白色       关机后是红色
    public static void setPowerLED(boolean isOn) {
        try {
            TvManager.getInstance().setGpioDeviceStatus(INT_LED_RED, isOn);
        } catch (TvCommonException e) {
            e.printStackTrace();
        }
    }

    public static boolean getPowerLED() {
        try {
           return TvManager.getInstance().getGpioDeviceStatus(INT_LED_RED) == 1;
        } catch (TvCommonException e) {
            e.printStackTrace();
        }

        return false;
    }
}
