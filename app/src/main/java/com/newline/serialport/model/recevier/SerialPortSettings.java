package com.newline.serialport.model.recevier;

import android.content.Context;
import android.provider.Settings;

import com.newline.serialport.utils.StartActivityManager;

public class SerialPortSettings extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 20 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 20 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {

        StartActivityManager.openApp(context, Settings.ACTION_SETTINGS, "");

    }
}
