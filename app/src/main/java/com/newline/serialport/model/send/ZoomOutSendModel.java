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
public class ZoomOutSendModel extends BaseKeyEventSendModel {


    public ZoomOutSendModel(SerialPortUtils serialPort, int keyIntent) {
        super(serialPort, keyIntent);
    }



    @Override
    String getKeyDownContent() {
        return "7F 09 99 A2 B3 C4 02 FF 15 69 01 CF";
    }

    @Override
    String getKeyRepeatContent() {
        return "7F 09 99 A2 B3 C4 02 FF 15 69 20 CF";
    }

    @Override
    String getKeyUpContent() {
        return "";
    }

    @Override
    String getKeyPressContent() {
        return "";
    }

    @Override
    String getKeyLongPressContent() {
        return "";
    }
}
