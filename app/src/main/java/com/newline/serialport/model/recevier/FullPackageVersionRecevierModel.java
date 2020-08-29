package com.newline.serialport.model.recevier;

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
public class FullPackageVersionRecevierModel extends HexStringRecevierModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 0A 8A CF";


    public FullPackageVersionRecevierModel(HHTDeviceManager hhtDeviceManager) {
        super(hhtDeviceManager);
    }


    @Override
    String getRawData() {
        //集成包版本号
        return NewlineDeviceUtils.getFullPackageVersion();
    }

    @Override
    String getQueryType() {
        return QUERY_TYPE_FULL_PCAKAGE_VERSION;
    }
}
