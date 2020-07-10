package com.newline.serialport.model;

import android.content.Context;
import android.view.KeyEvent;

import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.utils.KeyUtils;
import com.hht.tools.log.Logger;

/**
 * 打开mic mute off
 */
public class SerialPortMicMuteOff extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 8E CF";

    protected String returnCode = "7F 08 99 A2 B3 C4 02 FF 01 8E XX CF";
    //
    //XX=01 Set mute on success
    //XX=00 Set mute on failed
    public static String stateHexStr = "";
    @Override
    public String getReturnCode() {
        return returnCode.replace("XX", stateHexStr);
    }

    @Override
    public void action(Context context) {
        if (UniteImpl.isMicMute()) {//非micmute 时才打开mic
            Logger.i("send keyCode " + KeyEvent.KEYCODE_MUTE);
            KeyUtils.sendSyncKey(KeyEvent.KEYCODE_MUTE);
        }
        stateHexStr = !UniteImpl.isMicMute() ? "01" : "00";
    }
}
