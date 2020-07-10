package com.newline.serialport.model;

import android.content.Context;
import android.view.KeyEvent;

import com.newline.serialport.utils.KeyUtils;
import com.newline.serialport.utils.KeyUtils;

public class SerialPortIncreaseBackLightBrightness extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 47 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 47 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        KeyUtils.sendSyncKey(KeyEvent.KEYCODE_BRIGHTNESS_UP);
    }
}
