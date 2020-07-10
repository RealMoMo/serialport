package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.utils.StartActivityManager;
import com.newline.serialport.utils.SystemUtils;

public class SerialPortModelName extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 87 CF";

    protected String returnCode = "7F 08 99 A2 B3 C4 02 FF 01 87 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        SystemUtils.getFirmwareVersion();
        new StartActivityManager().startFactoryMenu(context);
    }
}
