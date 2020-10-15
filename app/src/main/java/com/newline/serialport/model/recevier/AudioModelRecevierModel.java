package com.newline.serialport.model.recevier;

import com.newline.serialport.setting.HHTDeviceManager;
import com.newline.serialport.utils.NewlineDeviceUtils;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/8/24 10:21
 * @describe  音频版型号信息
 */
public class AudioModelRecevierModel extends HexStringRecevierModel {

    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 0A 9A CF";


    public AudioModelRecevierModel(HHTDeviceManager hhtDeviceManager) {
        super(hhtDeviceManager);
    }



    @Override
    String getRawData() {
        //获取音频版型号
        return NewlineDeviceUtils.getAudioModel();
    }

    @Override
    String getQueryType() {
        return QUERY_TYPE_AUDIO_MODEL;
    }
}
