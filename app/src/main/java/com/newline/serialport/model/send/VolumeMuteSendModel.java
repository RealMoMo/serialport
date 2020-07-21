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
public class VolumeMuteSendModel extends SendSerialPortModel {

    private boolean mute;

    //XX: 00==mute 01==unmute
    private String sendContent = "7F 09 99 A2 B3 C4 02 FF 39 XX 01 CF";

    public VolumeMuteSendModel(SerialPortUtils serialPort,boolean isMute) {
        super(serialPort);
        mute = isMute;

    }

    @Override
    String getSendContent() {
        return sendContent.replace("XX",mute?"00":"01");
    }
}
