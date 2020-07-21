package com.newline.serialport.model.recevier;

import android.content.Context;

import com.newline.serialport.utils.Sender;
import com.hht.tools.log.Logger;

public class SerialPortSwitchBackLightOn extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 09 99 A2 B3 C4 02 FF 0E 01 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 0E 01 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {

            Logger.i(" source only off");
            Sender.sendSoundOnlyOff(context);

    }
}
