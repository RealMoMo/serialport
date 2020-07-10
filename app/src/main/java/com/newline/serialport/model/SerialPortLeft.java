package com.newline.serialport.model;

import android.content.Context;
import android.view.KeyEvent;

import com.newline.serialport.utils.KeyUtils;

public class SerialPortLeft extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 2C CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 2C 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        KeyUtils.sendDownKey(KeyEvent.KEYCODE_DPAD_LEFT);
    }
}
