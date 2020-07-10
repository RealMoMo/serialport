package com.newline.serialport.model;

import android.content.Context;

import com.hht.middleware.tools.log.SystemPropertiesValue;
import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.utils.KeyUtils;
import com.newline.serialport.utils.LangoKeyCode;
import com.newline.serialport.utils.StartActivityManager;
import com.newline.serialport.utils.SystemUtils;
import com.hht.tools.log.Logger;

public class SerialPortFirmwareVersion extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 3D CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 3D 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {

        String property = (String) SystemPropertiesValue.getProperty("persist.hht.Platforms2", "");
        Logger.i("property = "+property);
        if ("YI_MS8386".equals(property)) {
            Logger.i("YI startSettingsAbout");
            StartActivityManager.startSettingsAbout(context);
            return;
        }
        SystemUtils.getFirmwareVersion();
        new StartActivityManager().startFactoryMenu(context);
    }
}
