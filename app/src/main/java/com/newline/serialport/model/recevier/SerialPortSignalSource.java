package com.newline.serialport.model.recevier;

import android.content.Context;

import com.newline.serialport.utils.StartActivityManager;

public class SerialPortSignalSource extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 06 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 06 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        StartActivityManager.startLauncher(context, StartActivityManager.LAUNCHER_SOURCE);
    }
}
