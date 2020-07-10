package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.utils.StartActivityManager;
import com.newline.serialport.utils.StartActivityManager;

import static com.newline.serialport.utils.StartActivityManager.PACKAGE_NEWLINE_CAST;

public class SerialPortWOTActiveNewlineCast extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 00 0D CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 00 0D 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        StartActivityManager.openApp(context, StartActivityManager.PACKAGE_NEWLINE_CAST);
    }
}
