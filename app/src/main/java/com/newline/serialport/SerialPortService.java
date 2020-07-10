package com.newline.serialport;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.hht.middleware.model.SourceValue;
import com.hht.middleware.tools.SystemPropertiesUtils;
import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.model.SerialPortModel;
import com.newline.serialport.ops.OnReSendSerialPortListener;
import com.newline.serialport.utils.Sender;
import com.newline.serialport.utils.StartActivityManager;
import com.newline.serialport.utils.SystemUtils;
import com.hht.tools.log.LogIntent;
import com.hht.tools.log.Logger;


import org.jetbrains.annotations.Nullable;

import android_serialport_api.SerialPort;

public class SerialPortService extends Service {
    SerialPortUtils serialPortUtils = new SerialPortUtils();
    OPSSerialUtils mOPSSerialUtils = new OPSSerialUtils();

    private byte[] mBuffer;
    private Handler handler = new Handler();

    private String s;
    UARTListenerThread mUARTListenerThread = new UARTListenerThread();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.i(" create serialPort " +this.getApplicationInfo().nativeLibraryDir);
//        mUARTListenerThread.start();

        if (SourceValue.isMS848() || !"".equals(SystemPropertiesUtils.getSysPlatforms())) {
            openSerialPortByUARTOnOff();
        } else {
            openSerialPort();
        }
//        mVoiceLocalServerSocket.openSocket();

        //串口数据监听事件
        serialPortUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] buffer, int size) {

                s = SerialPortUtils.bytesToHexString(buffer);

                Log.d("SerialPortService", "进入数据监听事件中。。。" + s);
                //
                //在线程中直接操作UI会报异常：ViewRootImpl$CalledFromWrongThreadException
                //解决方法：handler
                //
                mBuffer = buffer;
                String partOffCodes = SerialPortUtils.bytesToHexString(mBuffer).toUpperCase();
                SerialPortModel serialPortModel = SerialPortModel.getSerialPortModelByControllingCode(partOffCodes, size, SystemUtils.isYISleep(SerialPortService.this));
                Log.i("SerialPortService"," serialPortModel = "+serialPortModel);
                if (serialPortModel != null) {
                    serialPortModel.action(SerialPortService.this);

                    String code = serialPortModel.getReturnCode();
                    Logger.i("code = "+ code);
                    serialPortUtils.sendSerialPort(code);

                }
                handler.post(runnable);
            }
            //开线程更新UI
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Logger.i("size："+ String.valueOf(mBuffer.length)+"数据监听："+ s);
                }
            };
        });

        mOPSSerialUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] buffer, int size) {

                mBuffer = buffer;
                handler.post(runnable);
            }
            //开线程更新UI
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
//                    Logger.i("mOPSSerial size："+ String.valueOf(mBuffer.length)+"数据监听："+ s.substring(0, 128));
                }
            };
        });

    }





    private void openSerialPort() {
        Logger.i(" open serial for lango");
        boolean isOn = true;
        if (isOn) {
            if (serialPortUtils.serialPortStatus) {
                serialPortUtils.closeSerialPort();
            }
            if (mOPSSerialUtils.serialPortStatus) {
                mOPSSerialUtils.closeSerialPort();
            }

        } else {
            if (!serialPortUtils.serialPortStatus) {
                serialPortUtils.openSerialPort();
            }

            if (!mOPSSerialUtils.serialPortStatus) {
                mOPSSerialUtils.openSerialPort();
            }


        }
    }

    //根据Uart打开串口
    private void openSerialPortByUARTOnOff() {
        try {
            boolean isOn = UniteImpl.isOpenSerialPortListener();
            Logger.i("isOn = "+ isOn);
            if (isOn) {

                if (serialPortUtils.serialPortStatus) {
                    serialPortUtils.closeSerialPort();
                }
                if (mOPSSerialUtils.serialPortStatus) {
                    mOPSSerialUtils.closeSerialPort();
                }

            } else {

                if (!serialPortUtils.serialPortStatus) {
                    serialPortUtils.openSerialPort();
                }

                if (!mOPSSerialUtils.serialPortStatus) {
                    mOPSSerialUtils.openSerialPort();
                }

            }
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    public class UARTListenerThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                openSerialPortByUARTOnOff();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serialPortUtils.closeSerialPort();
        mOPSSerialUtils.closeSerialPort();

        if (mUARTListenerThread != null) {
            mUARTListenerThread.interrupt();
            mUARTListenerThread = null;
        }
    }
}
