package com.newline.serialport.model.recevier;

import android.util.Log;

import com.newline.serialport.setting.HHTDeviceManager;
import com.newline.serialport.utils.NewlineDeviceUtils;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/8/24 10:21
 * @describe
 */
public class SerialNumberRecevierModel extends HexStringRecevierModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 0A 7C CF";


    public SerialNumberRecevierModel(HHTDeviceManager hhtDeviceManager) {
        super(hhtDeviceManager);
    }


    @Override
    String getRawData() {
        //获取设备序列号
        Log.d("realmo","sn:"+NewlineDeviceUtils.getSerialNumber());
        return NewlineDeviceUtils.getSerialNumber();
    }

    @Override
    String getQueryType() {
        return QUERY_TYPE_SERIAL_NUMBER;
    }
}
