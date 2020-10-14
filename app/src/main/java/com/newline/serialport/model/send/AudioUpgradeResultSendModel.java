package com.newline.serialport.model.send;

import com.newline.serialport.SerialPortUtils;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/9/2 16:16
 * @describe 音频版升级结果发送数据包
 */
public class AudioUpgradeResultSendModel extends SendSerialPortModel {

    //XX: 00==fail 升级失败 01==success 升级成功
    private String sendContent = "7F 09 99 A2 B3 C4 02 FF 4A XX 01 CF";

    private boolean result;

    public AudioUpgradeResultSendModel(SerialPortUtils serialPort, boolean result) {
        super(serialPort);
        this.result = result;
    }

    @Override
    public String getSendContent() {
        return sendContent.replace("XX",result?"01":"00");
    }
}
