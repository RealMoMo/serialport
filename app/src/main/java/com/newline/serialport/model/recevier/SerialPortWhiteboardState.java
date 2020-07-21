package com.newline.serialport.model.recevier;

import android.content.Context;

import com.newline.serialport.utils.SystemUtils;
import com.hht.tools.log.Logger;

public class SerialPortWhiteboardState extends SerialPortModel {

    public static final String QUERYING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 83 CF";

    //● XX = 01 indicates the whiteboard state.
    //● XX = 00 indicates non-whiteboard state.
    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 83 XX CF";

    public static String stateHexStr = "";

    @Override
    public String getReturnCode() {
        return returnCode.replace("XX", stateHexStr);
    }

    @Override
    public void action(Context context) {
        stateHexStr = getState(context);
    }

    public static String getState(Context context) {
        boolean isWhiteboardOn = SystemUtils.isWhiteboardOnForeground(context) || SystemUtils.isTWWhiteboadOnForeground(context) || SystemUtils.isTWWhiteboadNewOnForeground(context);
        String stateHexStr = isWhiteboardOn? "01" : "00";
        Logger.i("stateHexStr = "+stateHexStr);
        return stateHexStr;
    }
}
