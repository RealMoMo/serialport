package com.newline.serialport;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;


import com.ist.android.tv.IstEventManager;
import com.newline.serialport.broadcast.V811MicMuteBroadcast;
import com.newline.serialport.dao.observer.SerialPortContentObserver;
import com.newline.serialport.model.KeyEventBean;
import com.newline.serialport.model.PersisentStatus;
import com.newline.serialport.model.recevier.RecevierSerialPortModel;
import com.newline.serialport.model.send.DelSendModel;
import com.newline.serialport.model.send.DownSendModel;
import com.newline.serialport.model.send.EnterSendModel;
import com.newline.serialport.model.send.LeftSendModel;
import com.newline.serialport.model.send.MicMuteSendModel;
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
import com.newline.serialport.model.send.VolumeMuteSendModel;
import com.newline.serialport.model.send.VolumeSendModel;
import com.newline.serialport.model.send.ZoomInSendModel;
import com.newline.serialport.model.send.ZoomOutSendModel;
import com.newline.serialport.pool.SerialPortModelPool;
import com.newline.serialport.setting.HHTDeviceManager;
import com.newline.serialport.setting.i.StandardDeviceStatusListener;


import org.jetbrains.annotations.Nullable;


public class SerialPortService extends Service implements SerialPortContentObserver.SerialPortDAOChangeListener, StandardDeviceStatusListener, V811MicMuteBroadcast.MicStatusListener {

    private static String TAG = "newlinePort";

    private SerialPortUtils serialPortUtils = new SerialPortUtils();
    private byte[] mBuffer;

    private static final int MSG_WHAT_VOLUME = 0X100;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_WHAT_VOLUME: {
                    SendSerialPortModel model = new VolumeSendModel(serialPortUtils, persisentStatus.volume);
                    serialPortModelPool.addSendPortModel(model);
                }
                break;
            }
        }
    };

    private SerialPortContentObserver serialPortContentObserver;

    private HHTDeviceManager hhtDeviceManager;

    private V811MicMuteBroadcast v811MicMuteBroadcast;

    private SerialPortModelPool serialPortModelPool;

    private PersisentStatus persisentStatus;
    /**
     * 是否为自己主动改变状态的标记
     */
    private boolean selfChange = false;


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
        initV811PlatformStatusListener();

        initPersistenceStatus();

        serialPortModelPool = SerialPortModelPool.getInstance();

    }

    /**
     * 初始化设备持久化状态信息
     */
    private void initPersistenceStatus() {
        persisentStatus = PersisentStatus.getInstance();
        persisentStatus.volume = hhtDeviceManager.getVolume();
        persisentStatus.volumeMute = hhtDeviceManager.getMuteStatus();
        persisentStatus.micMute = !IstEventManager.getInstance().isMirPhoneOpen();
    }

    /**
     * 监听V811平台 部分特殊功能状态变化
     */
    private void initV811PlatformStatusListener() {
        v811MicMuteBroadcast = new V811MicMuteBroadcast(this);
        this.registerReceiver(v811MicMuteBroadcast, v811MicMuteBroadcast.getIntentFilter());
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

        //TODO temp 直接开启，应该需判断uart口状态
        serialPortUtils.openSerialPort();


        //串口数据监听事件
        serialPortUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] buffer, int size) {


                mBuffer = buffer;
                String content = SerialPortUtils.bytesToHexString(mBuffer).toUpperCase();
                Log.d(TAG, "receiver content:" + content);
                RecevierSerialPortModel recevierSerialPortModel = RecevierSerialPortModel.getSerialPortModelByControllingCode(content, size, hhtDeviceManager);

                if (recevierSerialPortModel != null) {
                    selfChange = true;
                    recevierSerialPortModel.action();

                    //回复答应
                    serialPortUtils.sendSerialPort(recevierSerialPortModel.retryContent());
                } else {
                    //处理对方答应信息，移除在重发队列
                    serialPortModelPool.removePoolSendSerialPortModel(RecevierSerialPortModel.getTargetCode(content, size));
                }

            }
        });
    }

    /**
     * 处理按键转发
     * @param keyEventBean
     */
    @Override
    public void getKeyEvent(KeyEventBean keyEventBean) {
        Log.d(TAG,"getkeyevent:"+keyEventBean.toString());
        SendSerialPortModel sendModel = null;
        switch (keyEventBean.getKeycode()) {
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
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER: {
                sendModel = new EnterSendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_FORWARD_DEL:
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

        if (sendModel != null) {
            //ToastUtils.toast(this,""+keycode,Toast.LENGTH_SHORT);
            serialPortModelPool.addSendPortModel(sendModel);
        }

    }


    @Override
    public void onMuteChange(boolean mute) {
        if (isSelfChange()) {
            return;
        }
        if (persisentStatus.volumeMute == mute) {
            return;
        }
        persisentStatus.volumeMute = mute;
        SendSerialPortModel model = new VolumeMuteSendModel(serialPortUtils, mute);
        serialPortModelPool.addSendPortModel(model);

    }

    @Override
    public void onVolumeChange(int value) {
        if (isSelfChange()) {
            return;
        }
        if (persisentStatus.volume == value) {
            return;
        }
        persisentStatus.volume = value;
        handler.removeMessages(MSG_WHAT_VOLUME);
        handler.sendEmptyMessageDelayed(MSG_WHAT_VOLUME, 100);

    }

    @Override
    public void onBrightnessChange(int value) {
        //不需处理，亮度不需与ops同步
    }

    @Override
    public void micMuteStatusChanged(boolean isMute) {
        if (isSelfChange()) {
            return;
        }
        if (persisentStatus.micMute == isMute) {
            return;
        }
        persisentStatus.micMute = isMute;
        SendSerialPortModel model = new MicMuteSendModel(serialPortUtils, isMute);
        serialPortModelPool.addSendPortModel(model);
    }


    private boolean isSelfChange() {
        if (selfChange) {
            selfChange = false;
            return true;
        }
        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        handler.removeCallbacksAndMessages(null);
        handler = null;

        serialPortUtils.closeSerialPort();

        serialPortContentObserver.removeSerialPortContentObserver(this);
        serialPortContentObserver.release();
        unregisterReceiver(v811MicMuteBroadcast);
        serialPortModelPool.release();
        v811MicMuteBroadcast.release();
    }


}
