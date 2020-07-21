package com.newline.serialport.model.send;

import com.newline.serialport.SerialPortUtils;

import java.lang.ref.WeakReference;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/20 17:27
 * @describe
 */
public abstract class SendSerialPortModel {


    WeakReference<SerialPortUtils> serialPort;

    public SendSerialPortModel(SerialPortUtils serialPort) {
        this.serialPort = new WeakReference(serialPort);
    }

    abstract String getSendContent();

    public void sendContent(){
        if(serialPort.get()== null){
            return;
        }
        serialPort.get().sendSerialPort(getSendContent());
    }
} 
