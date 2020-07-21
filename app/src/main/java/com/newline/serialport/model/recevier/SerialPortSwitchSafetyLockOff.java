package com.newline.serialport.model.recevier;

import android.content.Context;

import com.newline.serialport.utils.Sender;

/**
 * 打开童锁
 */
public class SerialPortSwitchSafetyLockOff extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 09 99 A2 B3 C4 02 FF 0D 00 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 0D 00 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        Sender.sendChildLockOff(context);
    }
}
