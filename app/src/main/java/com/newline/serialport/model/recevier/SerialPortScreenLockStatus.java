package com.newline.serialport.model.recevier;

import android.content.Context;

import com.newline.serialport.utils.SystemUtils;
import com.hht.tools.log.Logger;

public class SerialPortScreenLockStatus extends SerialPortModel {

    public static final String QUERYING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 84 CF";

    //● XX = 01 indicates the child safety lock is on.
    //● XX = 00 indicates the child safety lock is off.
    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 84 XX CF";
    public static String stateHexString = "";

    @Override
    public String getReturnCode() {
        return returnCode.replace("XX", stateHexString);
    }

    @Override
    public void action(Context context) {
        stateHexString = getStatus(context);
    }

    public static String getStatus(Context context) {
        boolean isLock = SystemUtils.isChildLockOn(context);
        Logger.i("isLock = "+isLock);
        String stateHexString = isLock? "01" : "00";
        Logger.i("stateHexString = "+stateHexString);
        return stateHexString;
    }
}
