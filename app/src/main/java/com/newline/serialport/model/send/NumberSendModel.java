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
public class NumberSendModel extends SendSerialPortModel {

    public NumberSendModel(SerialPortUtils serialPort) {
        super(serialPort);
    }

    @Override
    String getSendContent() {
        return null;
    }
}
