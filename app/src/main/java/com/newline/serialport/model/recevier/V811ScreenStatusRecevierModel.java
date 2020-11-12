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
public class V811ScreenStatusRecevierModel extends RecevierSerialPortModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 0A 07 CF";

    /**
     * 返回背光状态全命令： 7F 09 99 A2 B3 C4 02 FF 0A 07 XX CF
     * xx 息屏01  亮屏00
     */
    private static final String REAL_CONTROLLING_CODE = "7F 09 99 A2 B3 C4 02 FF 0A 07 XX CF";

    public V811ScreenStatusRecevierModel(HHTDeviceManager hhtDeviceManager) {
        super(hhtDeviceManager);
    }

    @Override
    public void action() {

    }

    @Override
    public String retryContent() {
        return REAL_CONTROLLING_CODE.replace("XX", NewlineDeviceUtils.isSleep()?"01":"00");
    }


}
