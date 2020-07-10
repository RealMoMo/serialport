package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.utils.KeyUtils;
import com.newline.serialport.utils.LangoKeyCode;

/**
 * 打开mic mute off
 */
public class SerialPortWOTMicMuteOff extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 09 99 A2 B3 C4 02 FF 0C 02 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 0C 02 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        KeyUtils.sendSyncKey(LangoKeyCode.KEYCODE_RS232_MIC_OFF);
    }
}
