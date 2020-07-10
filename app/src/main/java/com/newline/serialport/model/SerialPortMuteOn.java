package com.newline.serialport.model;

import android.content.Context;
import android.view.KeyEvent;

import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.utils.KeyUtils;
import com.newline.serialport.utils.LangoKeyCode;
import com.newline.serialport.utils.SystemUtils;

public class SerialPortMuteOn extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 09 99 A2 B3 C4 02 FF 0F 00 CF";

    private String returnCodes = "7F 09 99 A2 B3 C4 02 FF 0F 00 01 CF";

    @Override
    public String getReturnCode() {
        return returnCodes;
    }


    @Override
    public void action(Context context) {

        if (SystemUtils.getVolume(context) != 0) {//当前非静音 ， 才切换成静音
            KeyUtils.sendDownKey(KeyEvent.KEYCODE_VOLUME_MUTE);
        }
        SystemUtils.showAudioUI(context);
    }


}
