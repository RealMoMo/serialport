package com.newline.serialport.model.recevier;

import com.newline.serialport.setting.HHTDeviceManager;
import com.newline.serialport.utils.NewlineDeviceUtils;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/8/24 10:21
 * @describe  设备型号信息
 */
public class ProductModelRecevierModel extends HexStringRecevierModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 0A 8C CF";


    public ProductModelRecevierModel(HHTDeviceManager hhtDeviceManager) {
        super(hhtDeviceManager);
    }



    @Override
    String getRawData() {
        //型号信息
        return NewlineDeviceUtils.getModelType();
    }

    @Override
    String getQueryType() {
        return QUERY_TYPE_MODEL_VERSION;
    }
}
