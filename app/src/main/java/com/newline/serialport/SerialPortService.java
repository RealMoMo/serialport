package com.newline.serialport;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;


import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.dao.observer.SerialPortContentObserver;
import com.newline.serialport.model.recevier.SerialPortModel;
import com.newline.serialport.setting.HHTDeviceManager;
import com.newline.serialport.setting.i.StandardDeviceStatusListener;
import com.hht.tools.log.Logger;


import org.jetbrains.annotations.Nullable;


public class SerialPortService extends Service implements SerialPortContentObserver.SerialPortDAOChangeListener, StandardDeviceStatusListener {

    private static String TAG = "newlinePort";

    private SerialPortUtils serialPortUtils = new SerialPortUtils();

    private byte[] mBuffer;
    private Handler handler = new Handler();

    private String s;
    private UARTListenerThread mUARTListenerThread = new UARTListenerThread();

    private SerialPortContentObserver serialPortContentObserver;
    private HHTDeviceManager hhtDeviceManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initSerialPort();
        initSerialPortDAOListener();
        initStandardAndroidStatusListener();

    }

    /**
     * 监听Android标准平台 通用状态变化
     */
    private void initStandardAndroidStatusListener() {
        hhtDeviceManager = new HHTDeviceManager(this);
        hhtDeviceManager.setOnStandardDeviceStatusListener(this);
    }

    /**
     * 监听SerialPortContentProvider 数据变化
     */
    private void initSerialPortDAOListener() {
        serialPortContentObserver = SerialPortContentObserver.getInstance(this);
        serialPortContentObserver.addSerialPortContentObserver(this);
    }

    private void initSerialPort() {
        //TODO realmo 判断串口是否打开
//       openSerialPortByUARTOnOff();
//       openSerialPort();


        //串口数据监听事件
        serialPortUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] buffer, int size) {

                s = SerialPortUtils.bytesToHexString(buffer);
                Log.d("realmo","serialport content:"+s);
                mBuffer = buffer;
                String partOffCodes = SerialPortUtils.bytesToHexString(mBuffer).toUpperCase();
                SerialPortModel serialPortModel = SerialPortModel.getSerialPortModelByControllingCode(partOffCodes, size);

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
                    Logger.i("size："+ mBuffer.length+"数据监听："+ s);
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

        } else {
            if (!serialPortUtils.serialPortStatus) {
                serialPortUtils.openSerialPort();
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


            } else {

                if (!serialPortUtils.serialPortStatus) {
                    serialPortUtils.openSerialPort();
                }

            }
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    @Override
    public void getKeyEvent(int keycode) {
        //TODO realmo send event to ops
        Log.d(TAG,"keycode:"+keycode);

        switch (keycode){
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_8:
            case KeyEvent.KEYCODE_9:
            default:{

            }break;
        }

    }

    @Override
    public void onMuteChange(boolean mute) {
        //TODO realmo send mute status to ops
    }

    @Override
    public void onVolumeChange(int value) {
        //TODO realmo send volume to ops
    }

    @Override
    public void onBrightnessChange(int value) {
        //TODO do nothing
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

        if (mUARTListenerThread != null) {
            mUARTListenerThread.interrupt();
            mUARTListenerThread = null;
        }

        serialPortContentObserver.removeSerialPortContentObserver(this);
        serialPortContentObserver.release();
    }
}
