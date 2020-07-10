package com.newline.serialport.model;

import android.content.Context;

import com.hht.middleware.model.SourceValue;
import com.newline.serialport.utils.Sender;
import com.newline.serialport.utils.Sender;

public class SerialPortHDMIFront extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 0A CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 0A 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        Sender.sendFullSource(context, SourceValue.FRONT_ORDINAL);
    }
}
