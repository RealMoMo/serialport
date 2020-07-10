package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.utils.Sender;
import com.newline.serialport.utils.StartActivityManager;
import com.newline.serialport.utils.SystemUtils;
import com.hht.tools.log.Logger;
import com.hht.tools.log.SystemPropertiesValue;

public class SerialPortEnableWhiteboard extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 07 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 07 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        String packageName = (String) SystemPropertiesValue.getProperty("persist.hht.whiteboard","tw.com.hitevision.whiteboard");
        if (SystemUtils.isYI()) {
            packageName = "tw.com.newline.whiteboard";
        }
        Logger.i("packageName = "+ packageName);
        if (SystemUtils.isPkgInstalled(context, packageName)) {
            StartActivityManager.startTWWhiteboard(context);
        } else {
            Sender.sendStartWhiteBoard(context);
        }
    }
}
