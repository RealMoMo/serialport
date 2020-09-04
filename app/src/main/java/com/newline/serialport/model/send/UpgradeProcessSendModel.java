package com.newline.serialport.model.send;

import com.newline.serialport.SerialPortUtils;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/9/2 16:16
 * @describe 升级过程发送数据包
 */
public class UpgradeProcessSendModel extends SendSerialPortModel {

    //XX: 00==fail 升级失败 01==prepare upgrade 准备升级
    private String sendContent = "7F 09 99 A2 B3 C4 02 FF 3A XX 01 CF";

    private boolean type;

    public UpgradeProcessSendModel(SerialPortUtils serialPort, boolean type) {
        super(serialPort);
        this.type = type;
    }

    @Override
    public String getSendContent() {
        return sendContent.replace("XX",type?"01":"00");
    }
}
