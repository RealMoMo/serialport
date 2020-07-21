package com.newline.serialport;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;



import com.newline.serialport.dao.observer.SerialPortContentObserver;
import com.newline.serialport.model.recevier.SerialPortModel;
import com.newline.serialport.model.send.DelSendModel;
import com.newline.serialport.model.send.DownSendModel;
import com.newline.serialport.model.send.EnterSendModel;
import com.newline.serialport.model.send.LeftSendModel;
import com.newline.serialport.model.send.Number0SendModel;
import com.newline.serialport.model.send.Number1SendModel;
import com.newline.serialport.model.send.Number2SendModel;
import com.newline.serialport.model.send.Number3SendModel;
import com.newline.serialport.model.send.Number4SendModel;
import com.newline.serialport.model.send.Number5SendModel;
import com.newline.serialport.model.send.Number6SendModel;
import com.newline.serialport.model.send.Number7SendModel;
import com.newline.serialport.model.send.Number8SendModel;
import com.newline.serialport.model.send.Number9SendModel;
import com.newline.serialport.model.send.RightSendModel;
import com.newline.serialport.model.send.SendSerialPortModel;
import com.newline.serialport.model.send.UpSendModel;
import com.newline.serialport.model.send.ZoomInSendModel;
import com.newline.serialport.model.send.ZoomOutSendModel;
import com.newline.serialport.setting.HHTDeviceManager;
import com.newline.serialport.setting.i.StandardDeviceStatusListener;
import com.hht.tools.log.Logger;


import org.jetbrains.annotations.Nullable;


public class SerialPortService extends Service implements SerialPortContentObserver.SerialPortDAOChangeListener, StandardDeviceStatusListener {

//    private static String TAG = "newlinePort";
    private static String TAG = "realmo";

    private SerialPortUtils serialPortUtils = new SerialPortUtils();

    private byte[] mBuffer;
    private Handler handler = new Handler();

    private String s;
    //private UARTListenerThread mUARTListenerThread = new UARTListenerThread();

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

        initSerialPortListener();
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

    private void initSerialPortListener() {
        //TODO realmo 判断串口是否打开
//       openSerialPortByUARTOnOff();

        //TODO temp 直接开启
        serialPortUtils.openSerialPort();


        //串口数据监听事件
        serialPortUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] buffer, int size) {

                s = SerialPortUtils.bytesToHexString(buffer);
                Log.d("realmo", "serialport content:" + s);
                mBuffer = buffer;
                String partOffCodes = SerialPortUtils.bytesToHexString(mBuffer).toUpperCase();
                Log.d("realmo", "content:" + partOffCodes);
                SerialPortModel serialPortModel = SerialPortModel.getSerialPortModelByControllingCode(partOffCodes, size);

                if (serialPortModel != null) {
                    serialPortModel.action(SerialPortService.this);

                    String code = serialPortModel.getReturnCode();
                    if(code != null){
                        serialPortUtils.sendSerialPort(code);
                    }

                }
            }
        });
    }


    //根据Uart打开串口
    private void openSerialPortByUARTOnOff() {
        try {
            boolean isOn = false;
            Logger.i("isOn = " + isOn);
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
        Log.d(TAG, "keycode:" + keycode);
        SendSerialPortModel sendModel = null;
        switch (keycode) {
            case KeyEvent.KEYCODE_0: {
                sendModel = new Number0SendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_1: {
                sendModel = new Number1SendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_2: {
                sendModel = new Number2SendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_3: {
                sendModel = new Number3SendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_4: {
                sendModel = new Number4SendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_5: {
                sendModel = new Number5SendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_6: {
                sendModel = new Number6SendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_7: {
                sendModel = new Number7SendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_8: {
                sendModel = new Number8SendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_9: {
                sendModel = new Number9SendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_DPAD_UP: {
                sendModel = new UpSendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_DPAD_DOWN: {
                sendModel = new DownSendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_DPAD_LEFT: {
                sendModel = new LeftSendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_DPAD_RIGHT: {
                sendModel = new RightSendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_ENTER: {
                sendModel = new EnterSendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_DEL: {
                sendModel = new DelSendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_ZOOM_IN: {
                sendModel = new ZoomInSendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_ZOOM_OUT: {
                sendModel = new ZoomOutSendModel(serialPortUtils);
            }
            break;
            default: {

            }
            break;
        }

        if(sendModel!= null){
            sendModel.sendContent();
        }

    }

    @Override
    public void onMuteChange(boolean mute) {
        //TODO realmo send mute status to ops
        Log.d("realmo","onMuteChange:"+mute);
    }

    @Override
    public void onVolumeChange(int value) {
        //TODO realmo send volume to ops
        Log.d("realmo","onVolumeChange:"+value);
    }

    @Override
    public void onBrightnessChange(int value) {
        //TODO do nothing
    }



//    public class UARTListenerThread extends Thread {
//
//        @Override
//        public void run() {
//            super.run();
//            while (true) {
//                try {
//                    sleep(1500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                openSerialPortByUARTOnOff();
//            }
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serialPortUtils.closeSerialPort();

//        if (mUARTListenerThread != null) {
//            mUARTListenerThread.interrupt();
//            mUARTListenerThread = null;
//        }

        serialPortContentObserver.removeSerialPortContentObserver(this);
        serialPortContentObserver.release();
    }
}
