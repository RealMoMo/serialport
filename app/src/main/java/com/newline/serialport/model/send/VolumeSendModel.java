package com.newline.serialport.model.send;

import com.newline.serialport.SerialPortUtils;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/20 17:39
 * @describe
 */
public class VolumeSendModel extends SendSerialPortModel {

    private int volume;
    private String volumeHexStr;
    //XX为音量值 范围0-100对应的16进制值
    private String sendContent = "7F 09 99 A2 B3 C4 02 FF 05 XX 01 CF";

    public VolumeSendModel(SerialPortUtils serialPort,int currentVolume) {
        super(serialPort);
        volume = currentVolume;
        volumeHexStr = Integer.toHexString(volume);
        if(volumeHexStr.length() == 1){
            volumeHexStr = "0"+volumeHexStr;
        }
    }

    @Override
    String getSendContent() {

        return sendContent.replace("XX", volumeHexStr);
    }
}
