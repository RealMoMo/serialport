package com.newline.serialport;

import android.util.Log;

import com.hht.middleware.model.SourceValue;
import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.chip.UniteImpl;

import java.io.File;
import java.io.IOException;

import android_serialport_api.SerialPort;

public class OPSSerialUtils extends SerialPortUtils {

    protected String TAG = "OPSSerialUtils";
    protected String path = "/dev/ttyS1";
    protected String path848 = "/dev/ttyS2";
    protected int baudrate = 115200;

    /**
     * 打开串口
     * @return serialPort串口对象
     */
    @Override
    public SerialPort openSerialPort(){
        try {
            serialPort = new SerialPort(new File(SourceValue.isMS848() ? path848 : path),baudrate,0);
            this.serialPortStatus = true;
            threadStatus = false; //线程状态

            //获取打开的串口中的输入输出流，以便于串口数据的收发
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();

            new ReadThread().start(); //开始线程监控是否有数据要接收
        } catch (IOException e) {
            Log.e(TAG, "openSerialPort: 打开串口异常：" + e.toString());
            return serialPort;
        }
        Log.d(TAG, "openSerialPort: 打开串口");
        return serialPort;
    }
}
