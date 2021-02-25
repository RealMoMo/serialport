package com.newline.serialport.model.recevier;

import com.ist.android.tv.IstEventManager;
import com.newline.serialport.setting.HHTDeviceManager;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/21 16:32
 * @describe
 */
public class V811MicMuteRecevierModel extends RecevierSerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 37 00 CF";

    public V811MicMuteRecevierModel(HHTDeviceManager hhtDeviceManager) {
        super(hhtDeviceManager);
        changeAndroidDevice = true;
        changeAndroidFunctionType = MIC_MUTE_FUNCTION_TYPE;
    }


    @Override
    public void action() {
        IstEventManager.getInstance().setMirPhone(false);
    }

    @Override
    public String retryContent() {
        return CONTROLLING_CODE;
    }
}
