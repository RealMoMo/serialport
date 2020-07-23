package com.newline.serialport.model.send;

import com.newline.serialport.SerialPortUtils;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/20 17:38
 * @describe
 */
public class DelSendModel extends SendSerialPortModel {

    public DelSendModel(SerialPortUtils serialPort) {
        super(serialPort);
    }

    @Override
    public String getSendContent() {
        return "7F 09 99 A2 B3 C4 02 FF 15 6C 01 CF";
    }
}
