package com.newline.serialport.model.recevier;

import android.content.Context;

import com.hht.middleware.model.SourceValue;
import com.newline.serialport.utils.Sender;

public class SerialPortDP extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 56 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 56 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        Sender.sendFullSource(context, SourceValue.DP_ORDINAL);
    }
}
