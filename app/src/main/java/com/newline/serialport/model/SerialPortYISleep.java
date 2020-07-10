package com.newline.serialport.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.newline.provider.lib.HHTSetting;
import com.hht.tools.device.ProcessUtils;
import com.hht.tools.log.Logger;

public class SerialPortYISleep extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 08 CF";

    //● XX = 01 唤醒
    //● XX = 00 休眠
    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 08 XX CF";
    private static final String  STANDBY_STATUS_FLAG = "hht_standby";

    public static String stateHexStr = "";

    @Override
    public String getReturnCode() {
        return returnCode.replace("XX", stateHexStr);
    }

    @Override
    public void action(Context context) {
        String foregroundActivity = ProcessUtils.getForegroundActivity(context);
        Logger.d("foregroundActivity = "+ foregroundActivity);
        if ("com.hht.standby/com.hht.standby.MainActivity".equals(foregroundActivity)) {
            Settings.Secure.putInt(context.getContentResolver(), STANDBY_STATUS_FLAG,0);//关闭假待机
            try {
                HHTSetting.System.putBoolean(context.getContentResolver(), HHTSetting.System.IS_STANDBY, false);

                Logger.i(HHTSetting.System.IS_STANDBY +  "  == " +HHTSetting.System.getBoolean(context.getContentResolver(),HHTSetting.System.IS_STANDBY));
            } catch (Exception e) {
                Logger.e(e);
            }
            stateHexStr = "00";
        } else {
            Settings.Secure.putInt(context.getContentResolver(), STANDBY_STATUS_FLAG,1);//打开假待机
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.hht.standby","com.hht.standby.MainActivity"));
            context.startActivity(intent);
            stateHexStr = "01";
        }
    }


}
