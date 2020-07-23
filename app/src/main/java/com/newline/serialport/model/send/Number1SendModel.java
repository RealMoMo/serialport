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
public class Number1SendModel extends SendSerialPortModel {

    public Number1SendModel(SerialPortUtils serialPort) {
        super(serialPort);
    }

    @Override
    public String getSendContent() {
        return "7F 09 99 A2 B3 C4 02 FF 15 41 01 CF";
    }
}
