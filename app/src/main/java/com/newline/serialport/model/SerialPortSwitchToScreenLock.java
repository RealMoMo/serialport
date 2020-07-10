package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.utils.Sender;
import com.newline.serialport.utils.SystemUtils;
import com.hht.tools.log.Logger;

public class SerialPortSwitchToScreenLock extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 57 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 57 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        boolean currentIsLock = SystemUtils.isChildLockOn(context);
        Logger.i("currentIsLock = "+currentIsLock);
        if (currentIsLock) {
            Sender.sendChildLockOff(context);
        } else {
            Sender.sendChildLockOn(context);
        }
    }
}
