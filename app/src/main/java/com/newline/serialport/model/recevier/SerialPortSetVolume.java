package com.newline.serialport.model.recevier;

import android.content.Context;
import android.text.TextUtils;

import com.newline.serialport.utils.SystemUtils;
import com.hht.tools.log.Logger;

public class SerialPortSetVolume extends SerialPortModel {

    //7F 08 99 A2 B3 C4 02 FF 05 XX CF
    //XX indicates the value of backlight brightness (0 - 100), corresponding to the hexadecimal number system (00 - 64).
    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 05";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 05 XX 01 CF";

    public static String volumeHexString = "";

    @Override
    public String getReturnCode() {
        return returnCode.replace("XX", volumeHexString);
    }

    @Override
    public void action(Context context) {
        if (!TextUtils.isEmpty(volumeHexString)) {
            int volume = Integer.parseInt(volumeHexString, 16);
            Logger.i("volume =" + volume);
            if (volume > 100) {
                volume = 100;
            } else if (volume < 0) {
                volume = 0;
            }
            Logger.i("set volume =" + volume);
            SystemUtils.setVolume(context, volume);
        }
    }
}
