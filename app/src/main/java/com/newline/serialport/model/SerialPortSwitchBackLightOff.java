package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.utils.KeyUtils;
import com.newline.serialport.utils.LangoKeyCode;
import com.newline.serialport.utils.Sender;
import com.hht.tools.log.Logger;
import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.utils.KeyUtils;
import com.newline.serialport.utils.LangoKeyCode;
import com.newline.serialport.utils.Sender;

public class SerialPortSwitchBackLightOff extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 09 99 A2 B3 C4 02 FF 0E 00 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 0E 00 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {

            Logger.i(" source only on");
            Sender.sendSoundOnlyOn(context);

    }
}
