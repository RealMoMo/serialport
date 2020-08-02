package com.newline.serialport.model.send;

import android.support.annotation.Nullable;

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
//    //重发次数
//    public int tryCount = 3;
    public SendSerialPortModel(SerialPortUtils serialPort) {
        this.serialPort = new WeakReference(serialPort);
    }

    public abstract String getSendContent();

    public void sendContent(){
        if(serialPort.get()== null){
            return;
        }
        serialPort.get().sendSerialPort(getSendContent());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null){
            return false;
        }
        if(obj == this){
            return true;
        }
        if(obj instanceof String){
            return this.getSendContent().equals(obj);
        }
        if(obj instanceof SendSerialPortModel){
            return this.getSendContent().equals(((SendSerialPortModel) obj).getSendContent());
        }

        return super.equals(obj);
    }
}
