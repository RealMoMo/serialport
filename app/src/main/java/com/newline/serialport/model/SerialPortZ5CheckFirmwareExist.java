package com.newline.serialport.model;

import android.content.Context;

public class SerialPortZ5CheckFirmwareExist extends SerialPortModel {

    public static final String QUERYING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 89 CF";

    //XX:
    //01  yes
    //00  no
    protected String returnCode = "7F 08 99 A2 B3 C4 02 FF 01 89 XX CF";

    public static String HexString = "00";

    @Override
    public String getReturnCode() {
        return returnCode.replace("XX", HexString);
    }

    @Override
    public void action(Context context) {

    }
}
