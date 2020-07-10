package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.utils.Sender;

/**
 * 打开童锁
 */
public class SerialPortSwitchSafetyLockOn extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 09 99 A2 B3 C4 02 FF 0D 01 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 0D 01 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        Sender.sendChildLockOn(context);
    }
}
