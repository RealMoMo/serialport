package com.newline.serialport.model.recevier;

import com.newline.serialport.setting.HHTDeviceManager;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/22 10:53
 * @describe
 */
public class VolumeMuteRecevierModel extends RecevierSerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 39 00 CF";

    public VolumeMuteRecevierModel(HHTDeviceManager hhtDeviceManager) {
        super(hhtDeviceManager);
        changeAndroidDevice = true;
        changeAndroidFunctionType = VOLUME_MUTE_FUNCTION_TYPE;

    }

    @Override
    public void action() {
        hhtDeviceManager.get().setMute(true);
    }

    @Override
    public String retryContent() {
        return CONTROLLING_CODE;
    }
}
