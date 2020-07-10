package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.utils.KeyUtils;
import com.newline.serialport.utils.LangoKeyCode;
import com.newline.serialport.utils.Sender;
import com.newline.serialport.utils.StartActivityManager;
import com.newline.serialport.utils.SystemUtils;
import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.utils.KeyUtils;
import com.newline.serialport.utils.LangoKeyCode;
import com.newline.serialport.utils.Sender;
import com.newline.serialport.utils.SystemUtils;

public class SerialPortFreezeOn extends SerialPortModel {

        public static final String CONTROLLING_CODE = "7F 09 99 A2 B3 C4 02 FF 0B 00 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 0B 00 01 CF ";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {

        if (!SystemUtils.isFrozenScreen(context)) {//判断当前冻屏是关闭的，才需要打开
            if (SystemUtils.isZ5()) {
                StartActivityManager.openPause(context);
            } else {
                Sender.sendFreeze(context);
            }
        }
    }
}
