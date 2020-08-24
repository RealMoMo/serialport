package com.newline.serialport.model.recevier;

import com.newline.serialport.setting.HHTDeviceManager;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/8/24 10:21
 * @describe  设备型号
 */
public class ModelTypeRecevierModel extends HexStringRecevierModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 0A 7E CF";


    public ModelTypeRecevierModel(HHTDeviceManager hhtDeviceManager) {
        super(hhtDeviceManager);
    }



    @Override
    String getRawData() {
        //TODO 获取设备类型
        return "";
    }

    @Override
    String getQueryType() {
        return QUERY_TYPE_MODEL_TYPE;
    }
}
