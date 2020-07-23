package com.newline.serialport;


import android.os.SystemClock;
import android.util.Log;



import com.newline.serialport.utils.DataUtils;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import android_serialport_api.SerialPort;




public class SerialPortUtils {

    protected String TAG = "realmo";
    protected String path = "/dev/ttyAMA4";
//    protected String path = "/dev/ttyS0";
    protected int baudrate = 115200;
    public boolean serialPortStatus = false; //是否打开串口标志
    public boolean threadStatus; //线程状态，为了安全终止线程

    public SerialPort serialPort = null;
    public InputStream inputStream = null;
    public OutputStream outputStream = null;


    /**
     * 打开串口
     * @return serialPort串口对象
     */
    public SerialPort openSerialPort(){
        try {
            serialPort = new SerialPort(new File(path),baudrate,0);
            this.serialPortStatus = true;
            threadStatus = false; //线程状态

            //获取打开的串口中的输入输出流，以便于串口数据的收发
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
            Log.d(TAG,"inputStream:"+(inputStream==null));
            Log.d(TAG,"outputStream:"+(outputStream==null));
            new ReadThread().start(); //开始线程监控是否有数据要接收
        } catch (IOException e) {
            Log.e(TAG, "openSerialPort failed " + e.toString());
            return serialPort;
        }
        Log.d(TAG, "openSerialPort success");
        return serialPort;
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort(){
        try {
            inputStream.close();
            outputStream.close();

            this.serialPortStatus = false;
            this.threadStatus = true; //线程状态
            serialPort.close();
        } catch (IOException e) {
            Log.e(TAG, "closeSerialPort wrong"+e.toString());
            return;
        }
        Log.d(TAG, "closeSerialPort success");
    }

    /**
     * 发送串口指令（字符串）
     * @param data String数据指令
     */
    public void sendSerialPort(String data){
        Log.d(TAG, "sendSerialPort:"+data.replace(" ", ""));

        try {
            byte[] sendData = DataUtils.toBytes(data.replace(" ", ""));
            if (sendData.length > 0) {
                outputStream.write(sendData);
                outputStream.flush();

            }
        } catch (IOException e) {
            Log.e(TAG, "sendSerialPort wrong："+e.toString());
        }
        SystemClock.sleep(10);

    }

    /**
     * 发送串口指令（字符串）
     */
    public void sendSerialPort(byte[] sendData){

        try {
            if (sendData.length > 0) {
                outputStream.write(sendData);
                outputStream.flush();
            }
        } catch (IOException e) {
            Log.e(TAG, "sendSerialPort wrong:"+e.toString());
        }
        SystemClock.sleep(10);

    }

    /**
     * 单开一线程，来读数据
     */
    protected class ReadThread extends Thread{
        @Override
        public void run() {
            super.run();
            //判断进程是否在运行，更安全的结束进程
            while (!threadStatus){
                try {
                    sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //64   1024
                byte[] buffer = new byte[64];
                int size; //读取数据的大小
                try {
                    size = inputStream.read(buffer);
                    if (size > 0){

                        if (onDataReceiveListener != null) {
                            onDataReceiveListener.onDataReceive(buffer, size);
                        }
                    }
                } catch (IOException e) {
                    Log.e(TAG, "read data wrong：" +e.toString());
                }
            }

        }
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param bytes 要转换的字节数组
     * @return 转换后的结果
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //这是写了一监听器来监听接收数据
    public OnDataReceiveListener onDataReceiveListener = null;
    public interface OnDataReceiveListener {
        void onDataReceive(byte[] buffer, int size);
    }
    public void setOnDataReceiveListener(OnDataReceiveListener dataReceiveListener) {
        onDataReceiveListener = dataReceiveListener;
    }

}
