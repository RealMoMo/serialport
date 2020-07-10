package com.newline.serialport.model;

import android.content.Context;
import android.view.KeyEvent;

import com.hht.middleware.model.SourceValue;
import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.utils.KeyUtils;
import com.newline.serialport.utils.LangoKeyCode;

public class SerialPortHomePage extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 1C CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 1C 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {

        if (SourceValue.isMS848()) {
            KeyUtils.sendUpKey(KeyEvent.KEYCODE_HOME);
        } else {
            KeyUtils.sendDownKey(KeyEvent.KEYCODE_HOME);
        }
    }
}
