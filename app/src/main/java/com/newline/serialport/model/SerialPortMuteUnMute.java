package com.newline.serialport.model;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;

import com.newline.serialport.utils.KeyUtils;

public class SerialPortMuteUnMute extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 02 CF";

    private String returnCodes = "7F 09 99 A2 B3 C4 02 FF 01 02 01 CF";

    @Override
    public String getReturnCode() {
        return returnCodes;
    }


    @Override
    public void action(Context context) {
        Log.i("Mute", "do mute");
        KeyUtils.sendDownKey(KeyEvent.KEYCODE_VOLUME_MUTE);
    }


}
