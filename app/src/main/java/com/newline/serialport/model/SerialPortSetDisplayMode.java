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

public class SerialPortSetDisplayMode extends SerialPortModel {

    //7F 08 99 A2 B3 C4 02 FF 06 XX CF
    //XX indicates the mode. 00=Standard, 01=Eco, 02=Custom 03=Auto
    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 06";

    protected String returnCode = "7F 08 99 A2 B3 C4 02 FF 06 XX 01 CF";
    public static String ecomode;


    @Override
    public String getReturnCode() {
        return returnCode.replace("XX",  ecomode);
    }

    @Override
    public void action(Context context) {

        Sender.sendDisplayModeShow(context, Integer.parseInt(ecomode));
    }
}
