package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.chip.UniteImpl;

/**
 *
 */
public class SerialPortWOTMicMuteStatus extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 45 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 45 XX CF";
    //  XX:
    //01  mute
    //00  unmute
    public static String stateHexStr = "";
    @Override
    public String getReturnCode() {
        return returnCode.replace("XX", stateHexStr);
    }

    @Override
    public void action(Context context) {
        getMicMuteStatus();
    }

    public static String getMicMuteStatus() {
        stateHexStr = UniteImpl.isMicMute() ? "01" : "02";
        return stateHexStr;
    }
}
