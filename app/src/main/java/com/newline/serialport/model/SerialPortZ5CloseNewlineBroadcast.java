package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.utils.StartActivityManager;
import com.newline.serialport.utils.StartActivityManager;

import static com.newline.serialport.utils.StartActivityManager.PACKAGE_NEWLINE_BROADCAST;

public class SerialPortZ5CloseNewlineBroadcast extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 09 99 A2 B3 C4 02 FF 0B 05 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 0B 05 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        StartActivityManager.closeApp(context, StartActivityManager.PACKAGE_NEWLINE_BROADCAST);
    }
}
