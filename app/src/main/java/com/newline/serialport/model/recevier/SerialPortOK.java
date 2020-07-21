package com.newline.serialport.model.recevier;

import android.content.Context;
import android.view.KeyEvent;

import com.hht.middleware.model.SourceValue;
import com.newline.serialport.utils.KeyUtils;

public class SerialPortOK extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 2B CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 2B 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {

            if (SourceValue.isMS848()) {
                KeyUtils.sendSyncKey(KeyEvent.KEYCODE_ENTER);
            } else {
                KeyUtils.sendDownKey(KeyEvent.KEYCODE_ENTER);
            }

    }
}
