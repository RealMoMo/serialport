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
public class Number9SendModel extends SendSerialPortModel {

    public Number9SendModel(SerialPortUtils serialPort) {
        super(serialPort);
    }

    @Override
    String getSendContent() {
        return "7F 09 99 A2 B3 C4 02 FF 15 49 01 CF";
    }
}
