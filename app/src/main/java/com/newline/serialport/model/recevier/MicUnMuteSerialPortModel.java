package com.newline.serialport.model.recevier;

import android.content.Context;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/21 16:32
 * @describe
 */
public class MicUnMuteSerialPortModel extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 37 01 CF";


    @Override
    public void action(Context context) {
        //TODO realmo to do unmute mic
    }
}
