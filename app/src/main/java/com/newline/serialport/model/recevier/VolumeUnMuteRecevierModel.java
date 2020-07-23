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
public class VolumeUnMuteRecevierModel extends RecevierSerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 39 01 CF";

    public VolumeUnMuteRecevierModel(HHTDeviceManager hhtDeviceManager) {
        super(hhtDeviceManager);

    }

    @Override
    public void action() {
        hhtDeviceManager.get().setMute(false);
    }

    @Override
    public String retryContent() {
        return CONTROLLING_CODE;
    }
}
