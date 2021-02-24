package com.newline.serialport.model.send;

import com.newline.serialport.SerialPortUtils;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2021/2/1 11:01
 * @describe 心跳包数据
 */
public class LiveSendModel extends SendSerialPortModel {


    private String sendContent = "7F 09 99 A2 B3 C4 02 FF 01 01 01 CF";

    public LiveSendModel(SerialPortUtils serialPort) {
        super(serialPort);
    }

    @Override
    public String getSendContent() {
        return sendContent;
    }
}
