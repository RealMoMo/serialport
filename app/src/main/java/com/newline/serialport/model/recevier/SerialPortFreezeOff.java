package com.newline.serialport.model.recevier;

import android.content.Context;

import com.newline.serialport.utils.Sender;
import com.newline.serialport.utils.SystemUtils;

public class SerialPortFreezeOff extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 09 99 A2 B3 C4 02 FF 0B 01 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 0B 01 01 CF ";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {

        if (SystemUtils.isFrozenScreen(context)) {//判断当前冻屏是打开的，才需要关闭

            Sender.sendFreeze(context);

        }
    }
}
