package com.newline.serialport.model.recevier;

import android.content.Context;

import com.newline.serialport.setting.HHTDeviceManager;
import com.newline.serialport.utils.NewlineDeviceUtils;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/21 16:32
 * @describe
 */
public class V811PowerOffRecevierModel extends RecevierSerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 3C 01 CF";
    private Context mContext;

    public V811PowerOffRecevierModel(HHTDeviceManager hhtDeviceManager, Context context) {
        super(hhtDeviceManager);
        changeAndroidDevice = true;
        mContext = context;

    }


    @Override
    public void action() {
        NewlineDeviceUtils.sendPowerOffMsg(mContext);
    }

    @Override
    public String retryContent() {
        return CONTROLLING_CODE;
    }
}
