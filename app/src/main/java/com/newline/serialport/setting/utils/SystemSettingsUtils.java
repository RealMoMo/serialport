package com.newline.serialport.setting.utils;

import android.content.Context;
import android.provider.Settings;

public class SystemSettingsUtils {

    public static boolean isFrozenScreen(Context context) {
        String isFrozen = Settings.System.getString(context.getContentResolver(), SettingConstant.FIELD_IS_FROZEN);
        return"true".equals(isFrozen);
    }
}
