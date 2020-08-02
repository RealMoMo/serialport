package com.newline.serialport.model.recevier;

import com.newline.serialport.setting.HHTDeviceManager;


/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/21 16:32
 * @describe
 */
public class SyncStatusRecevierModel extends RecevierSerialPortModel {


    public static final String CONTROLLING_CODE = "";

    public SyncStatusRecevierModel(HHTDeviceManager hhtDeviceManager) {
        super(hhtDeviceManager);
    }

    @Override
    public void action() {

    }

    @Override
    public String retryContent() {
        return CONTROLLING_CODE;
    }
}
