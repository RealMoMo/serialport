package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.utils.StartActivityManager;

import static com.newline.serialport.utils.StartActivityManager.PACKAGE_NEWLINE_BROADCAST;

public class SerialPortWOTActiveNewlineBroadcast extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 00 0F CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 00 0F 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        StartActivityManager.openApp(context, PACKAGE_NEWLINE_BROADCAST);
    }
}
