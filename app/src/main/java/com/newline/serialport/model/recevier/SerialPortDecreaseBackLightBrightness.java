package com.newline.serialport.model.recevier;

import android.content.Context;
import android.view.KeyEvent;

import com.newline.serialport.utils.KeyUtils;

public class SerialPortDecreaseBackLightBrightness extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 48 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 48 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        KeyUtils.sendSyncKey(KeyEvent.KEYCODE_BRIGHTNESS_DOWN);
    }
}
