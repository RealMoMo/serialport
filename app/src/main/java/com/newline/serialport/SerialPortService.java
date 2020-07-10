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
import com.newline.serialport.ops.SerialPortOPSTOAndroid;
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
    VoiceSerialUtils mVoiceSerialUtils = new VoiceSerialUtils();
    private byte[] mBuffer;
    private Handler handler = new Handler();
    SerialPort serialPort;
    VoiceLocalServerSocket mVoiceLocalServerSocket = new VoiceLocalServerSocket();
    private String s;
    UARTListenerThread mUARTListenerThread = new UARTListenerThread();
    SerialPortOPSTOAndroid mSerialPortOPSTOAndroid = new SerialPortOPSTOAndroid();

    Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x01) {
                boolean micmute = SystemUtils.getMicMuteStatus(SerialPortService.this);
                Logger.i("change micmute = "+micmute);
                SystemUtils.setMicMuteStatus(SerialPortService.this, !micmute);
                Logger.i("changed micmute = "+SystemUtils.getMicMuteStatus(SerialPortService.this));
                mVoiceSerialUtils.changeMicMute();
            } else if (msg.obj instanceof String) {
                String content = (String) msg.obj;
                Logger.i("content = " + content);
                if (content.contains("AppName:")) {
                    String appName = content.split(":")[1];
                    if (mOPSSerialUtils != null) {
                        String command = SerialPortOPSTOAndroid.getSendOpenAppCommand(appName);
                        Logger.i("mOPSSerialUtils command = "+ command);
                        mOPSSerialUtils.sendSerialPort(command);
                    }
                }
            }
        }
    };

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogIntent.d(intent);
            String action = intent.getAction();
            Logger.i("action = " + action);
            if (StartActivityManager.ACTION_SEND_SERIAL_PORT.equals(action)) {
                String content = intent.getStringExtra(StartActivityManager.KEY_CONTENT);
                Message msg = new Message();
                msg.obj = content;
                mHandler.sendMessage(msg);
            } else if (StartActivityManager.ACTION_UPDATE_MIC_GPIO.equals(action)) {
                mHandler.sendEmptyMessage(0x01);
            }
        }
    };

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
            registerReceiver();
        } else {
            openSerialPort();
        }
//        mVoiceLocalServerSocket.openSocket();
        mVoiceSerialUtils.setListener(new VoiceSerialUtils.OnMicMuteListener() {
            @Override
            public void onMute(boolean isMute) {
                Logger.i("isMute11 = "+ isMute);
//                SystemUtils.setMicMuteStatus(SerialPortService.this, isMute);
                Sender.sendMicStatus(SerialPortService.this, isMute);
            }
        });
        mVoiceSerialUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] buffer, int size) {
//                s = SerialPortUtils.bytesToHexString(buffer);
                Log.d("SerialPortService", "进入数据监听事件中。。。" + s);
                //
                //在线程中GPIOUpdateBroadcastReceiver直接操作UI会报异常：ViewRootImpl$CalledFromWrongThreadException
                //解决方法：handler
                //
                mBuffer = buffer;
                String partOffCodes = SerialPortUtils.bytesToHexString(mBuffer).toUpperCase();
                SerialPortModel serialPortModel = SerialPortModel.getSerialPortModelByControllingCode(partOffCodes, size);
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
//                s = SerialPortUtils.bytesToHexString(buffer);
//                Log.d("SerialPortService", "进入数据监听事件中。。。" + s);
                //
                //在线程中直接操作UI会报异常：ViewRootImpl$CalledFromWrongThreadException
                //解决方法：handler
                //
                mBuffer = buffer;
                String partOffCodes = SerialPortUtils.bytesToHexString(mBuffer).toUpperCase();
                mSerialPortOPSTOAndroid.getSerialPortModelByOPSCode(SerialPortService.this, partOffCodes, size, new OnReSendSerialPortListener() {
                    @Override
                    public void onSend(String returnCode) {
                        Logger.i(" send " + returnCode);
                        mOPSSerialUtils.sendSerialPort(returnCode);

                    }
                });
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

    private void registerReceiver() {
        try {
            IntentFilter filter = new IntentFilter(StartActivityManager.ACTION_SEND_SERIAL_PORT);
            filter.addAction(StartActivityManager.ACTION_UPDATE_MIC_GPIO);
            registerReceiver(mReceiver, filter);


            if (mVoiceSerialUtils != null) {
                mVoiceSerialUtils.registerMicrophoneObserver(this);
            }
        } catch (Exception e) {
            Logger.e(e);
        }

    }

    private void unregisterReceiver() {
        try {
            unregisterReceiver(mReceiver);

            if (mVoiceSerialUtils != null) {
                mVoiceSerialUtils.unregisterMicrophoneObserver(this);
            }
        } catch (Exception e) {
            Logger.e(e);
        }
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
            if (mVoiceSerialUtils.serialPortStatus) {
                mVoiceSerialUtils.closeSerialPort();
            }
        } else {
            if (!serialPortUtils.serialPortStatus) {
                serialPortUtils.openSerialPort();
            }

            if (!mOPSSerialUtils.serialPortStatus) {
                mOPSSerialUtils.openSerialPort();
            }

            if (!mVoiceSerialUtils.serialPortStatus) {
                mVoiceSerialUtils.openSerialPort();
            }
        }
    }

    //根据Uart打开串口
    private void openSerialPortByUARTOnOff() {
        try {
            boolean isOn = UniteImpl.isOpenSerialPortListener();
            Logger.i("isOn = "+ isOn);
            if (isOn) {
//                if (mVoiceLocalServerSocket.isOpen) {
//                    mVoiceLocalServerSocket.closeSocket();
//                }
                if (serialPortUtils.serialPortStatus) {
                    serialPortUtils.closeSerialPort();
                }
                if (mOPSSerialUtils.serialPortStatus) {
                    mOPSSerialUtils.closeSerialPort();
                }
                if (mVoiceSerialUtils.serialPortStatus) {
                    mVoiceSerialUtils.closeSerialPort();
                }
            } else {
//                if (!mVoiceLocalServerSocket.isOpen) {
//                    mVoiceLocalServerSocket.openSocket();
//                }
                if (!serialPortUtils.serialPortStatus) {
                    serialPortUtils.openSerialPort();
                }

                if (!mOPSSerialUtils.serialPortStatus) {
                    mOPSSerialUtils.openSerialPort();
                }

                if (!mVoiceSerialUtils.serialPortStatus) {
                    VoiceSerialUtils.bEnableMic = SystemUtils.getMicMuteStatus(this);
                    Logger.i("VoiceSerialUtils.bEnableMic = "+VoiceSerialUtils.bEnableMic);
                    mVoiceSerialUtils.openSerialPort();
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
        unregisterReceiver();
        serialPortUtils.closeSerialPort();
        mOPSSerialUtils.closeSerialPort();
        mVoiceLocalServerSocket.closeSocket();
        if (mUARTListenerThread != null) {
            mUARTListenerThread.interrupt();
            mUARTListenerThread = null;
        }
    }
}
