package com.newline.serialport;


import android.util.Log;



import com.newline.serialport.utils.DataUtils;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import android_serialport_api.SerialPort;



/**
 * Created by WangChaowei on 2017/12/7.
 */

public class SerialPortUtils {

    protected String TAG = "SerialPortUtils";
    protected String path = "/dev/ttyS0";
    protected int baudrate = 19200;
    public boolean serialPortStatus = false; //是否打开串口标志
    public String data_;
    public boolean threadStatus; //线程状态，为了安全终止线程

    public SerialPort serialPort = null;
    public InputStream inputStream = null;
    public OutputStream outputStream = null;
//    public ChangeTool changeTool = new ChangeTool();

    public void openUSBLocalSocket() {
    }

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

            new ReadThread().start(); //开始线程监控是否有数据要接收
        } catch (IOException e) {
            Log.e(TAG, "openSerialPort: 打开串口异常：" + e.toString());
            return serialPort;
        }
        Log.d(TAG, "openSerialPort: 打开串口");
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
            Log.e(TAG, "closeSerialPort: 关闭串口异常："+e.toString());
            return;
        }
        Log.d(TAG, "closeSerialPort: 关闭串口成功");
    }

    /**
     * 发送串口指令（字符串）
     * @param data String数据指令
     */
    public void sendSerialPort(String data){
        Log.d(TAG, "sendSerialPort: 发送数据");

        try {
            byte[] sendData = DataUtils.toBytes(data.replace(" ", ""));
            this.data_ = new String(sendData); //byte[]转string
            if (sendData.length > 0) {
                outputStream.write(sendData);
//                outputStream.write('\n');
                //outputStream.write('\r'+'\n');
                outputStream.flush();
                long temp = System.currentTimeMillis();
                HHTApplication.totalCommander++;
                Log.d(TAG, "sendSerialPort: 串口数据发送成功"+ temp+"("+(temp - HHTApplication.timeStamp)+"ms)"+"("+(temp - HHTApplication.timeTotalStamp)+"ms)"+"("+ HHTApplication.totalCommander+"次)");
                if (this.data_.contains("AF A1")) {
                    HHTApplication.timeTotalStamp = 0;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "sendSerialPort: 串口数据发送失败："+e.toString());
        }

    }

    /**
     * 发送串口指令（字符串）
     */
    public void sendSerialPort(byte[] sendData){
        Log.d(TAG, "sendSerialPort: 发送数据");

        try {
            this.data_ = new String(sendData); //byte[]转string
            if (sendData.length > 0) {
                outputStream.write(sendData);
                outputStream.flush();
                Log.d(TAG, "sendSerialPort: 串口数据发送成功");
            }
        } catch (IOException e) {
            Log.e(TAG, "sendSerialPort: 串口数据发送失败："+e.toString());
        }

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
                Log.d(TAG, "进入线程run" + HHTApplication.timeStamp);
                //64   1024
                byte[] buffer = new byte[64];
                int size; //读取数据的大小
                try {
                    size = inputStream.read(buffer);
                    if (size > 0){
                        Log.d(TAG, "run: 接收到了数据：" + bytesToHexString(buffer));
                        Log.d(TAG, "run: 接收到了数据大小：" + String.valueOf(size));

                        if (HHTApplication.timeTotalStamp == 0) {
                            HHTApplication.timeTotalStamp = System.currentTimeMillis();
                        }
                        HHTApplication.timeStamp = System.currentTimeMillis();
                        if (onDataReceiveListener != null) {
                            onDataReceiveListener.onDataReceive(buffer, size);
                        }
                    }
                } catch (IOException e) {
                    Log.e(TAG, "run: 数据读取异常：" +e.toString());
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
//            Log.i("HWL", "bytes[i] = "+bytes[i] +"  i = "+i);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //这是写了一监听器来监听接收数据
    public OnDataReceiveListener onDataReceiveListener = null;
    public static interface OnDataReceiveListener {
        public void onDataReceive(byte[] buffer, int size);
    }
    public void setOnDataReceiveListener(OnDataReceiveListener dataReceiveListener) {
        onDataReceiveListener = dataReceiveListener;
    }

}
