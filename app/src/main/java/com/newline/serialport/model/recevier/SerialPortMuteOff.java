package com.newline.serialport.model.recevier;

import android.content.Context;
import android.view.KeyEvent;

import com.newline.serialport.utils.KeyUtils;
import com.newline.serialport.utils.SystemUtils;

public class SerialPortMuteOff extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 09 99 A2 B3 C4 02 FF 0F 01 CF";

    private String returnCodes = "7F 09 99 A2 B3 C4 02 FF 0F 01 01 CF";

    @Override
    public String getReturnCode() {
        return returnCodes;
    }


    @Override
    public void action(Context context) {

        if (SystemUtils.getVolume(context) == 0) {//当前静音 ， 才切换成非静音
            KeyUtils.sendDownKey(KeyEvent.KEYCODE_VOLUME_MUTE);
        }
        SystemUtils.showAudioUI(context);
    }


}
