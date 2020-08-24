package com.newline.serialport.model.recevier;

import com.newline.serialport.setting.HHTDeviceManager;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/8/24 10:21
 * @describe
 */
public class OttVersionRecevierModel extends HexStringRecevierModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 0A 7A CF";


    public OttVersionRecevierModel(HHTDeviceManager hhtDeviceManager) {
        super(hhtDeviceManager);
    }


    @Override
    String getRawData() {
        //TODO 获取OTA版本号
        return "";
    }

    @Override
    String getQueryType() {
        return QUERY_TYPE_OTT_VERSION;
    }
}
