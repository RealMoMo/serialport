package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.utils.SystemUtils;

public class SerialPortCheckOTA extends SerialPortModel {

    public static final String QUERYING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 89 CF";
    //● 01: mute
    //● 00: non-mute
    //xx
    protected String returnCode = "7F 08 99 A2 B3 C4 02 FF 01 89 XX CF";

    public static String stateHexString = "";

    @Override
    public String getReturnCode() {
        return returnCode.replace("XX", stateHexString);
    }

    @Override
    public void action(Context context) {
        stateHexString = SystemUtils.isSysUpdate() ? "01" : "00";
    }
}
