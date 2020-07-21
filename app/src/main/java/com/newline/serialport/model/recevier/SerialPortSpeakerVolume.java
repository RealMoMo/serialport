package com.newline.serialport.model.recevier;

import android.content.Context;

import com.newline.serialport.utils.SystemUtils;
import com.hht.tools.log.Logger;

public class SerialPortSpeakerVolume extends SerialPortModel {

    public static final String QUERYING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 33 CF";

    //XX indicates current volume value (XX is a hexadecimal value, range: 00~64).
    //For example, XX = 20 indicates current volume value is 32 (decimal value), XX = 00 indicates mute state.
    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 33 XX CF";

    public static String volumeHexStr = "";

    @Override
    public String getReturnCode() {
        return returnCode.replace("XX", volumeHexStr);
    }

    @Override
    public void action(Context context) {
        volumeHexStr = getCurrentVolume(context);
    }

    public static String getCurrentVolume(Context context) {
        String volumeHexStr = "";
        int volume = SystemUtils.getVolume(context);
        volumeHexStr = Integer.toHexString(volume);
        if (volumeHexStr.length() == 1) {//补位
            volumeHexStr = "0"+volumeHexStr;
        }
        Logger.i("volumeHexStr = "+volumeHexStr);
        return volumeHexStr;
    }
}
