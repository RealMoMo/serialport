package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.utils.Sender;

public class SerialPortZ5ProceedFWUpdate extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 88 CF";

    protected String returnCode = "7F 08 99 A2 B3 C4 02 FF 01 88 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        Sender.sendOTAUpdate(context);
    }
}
