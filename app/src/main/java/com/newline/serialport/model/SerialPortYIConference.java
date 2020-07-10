package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.utils.StartActivityManager;

public class SerialPortYIConference extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 3F CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 3F 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        StartActivityManager.startConference(context);
    }
}
