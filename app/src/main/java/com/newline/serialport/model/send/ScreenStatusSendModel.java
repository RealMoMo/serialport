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
public class ScreenStatusSendModel extends SendSerialPortModel {

    private boolean isSleep;

    //XX: 01==息屏 00==亮屏
    private String sendContent = "7F 09 99 A2 B3 C4 02 FF 3E XX 01 CF";

    public ScreenStatusSendModel(SerialPortUtils serialPort, boolean isSleep) {
        super(serialPort);
        this.isSleep = isSleep;

    }

    @Override
    public String getSendContent() {
        return sendContent.replace("XX",isSleep?"01":"00");
    }
}
