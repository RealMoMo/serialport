package com.newline.serialport.model.recevier;

import android.content.Context;

import com.newline.serialport.utils.SystemUtils;

public class SerialPortSpeaker extends SerialPortModel {

    public static final String QUERYING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 82 CF";
    //● 01: mute
    //● 00: non-mute
    //xx
    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 82 XX CF";

    public static String stateHexString = "";

    @Override
    public String getReturnCode() {
        return returnCode.replace("XX", stateHexString);
    }

    @Override
    public void action(Context context) {
        stateHexString = SystemUtils.isMute(context) ? "01" : "00";
    }
}
