package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.utils.KeyUtils;
import com.newline.serialport.utils.KeyUtils;

public class SerialPortYILockDown extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 21 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 21 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {//易 遥控器上的锁定触控
        KeyUtils.sendSyncKey(320);
    }
}
