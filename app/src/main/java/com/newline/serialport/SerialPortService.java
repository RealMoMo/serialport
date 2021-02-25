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
import com.newline.serialport.broadcast.V811ScreenStatusBroadcast;
import com.newline.serialport.dao.SerialPortDAO;
import com.newline.serialport.dao.observer.SerialPortContentObserver;
import com.newline.serialport.model.KeyEventBean;
import com.newline.serialport.model.PersisentStatus;
import com.newline.serialport.model.recevier.RecevierSerialPortModel;
import com.newline.serialport.model.recevier.SyncStatusRecevierModel;
import com.newline.serialport.model.send.AndroidUpgradeProcessSendModel;
import com.newline.serialport.model.send.AudioUpgradeResultSendModel;
import com.newline.serialport.model.send.DelSendModel;
import com.newline.serialport.model.send.DownSendModel;
import com.newline.serialport.model.send.EnterSendModel;
import com.newline.serialport.model.send.KeyPlusSendModel;
import com.newline.serialport.model.send.LeftSendModel;
import com.newline.serialport.model.send.LiveSendModel;
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
import com.newline.serialport.model.send.ScreenStatusSendModel;
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


public class SerialPortService extends Service implements SerialPortContentObserver.SerialPortDAOChangeListener, StandardDeviceStatusListener, V811MicMuteBroadcast.MicStatusListener, V811ScreenStatusBroadcast.ScreenStatusListener {

    private static String TAG = "newlinePort";

    private SerialPortUtils serialPortUtils = new SerialPortUtils();
    private byte[] mBuffer;

    private static final int MSG_WHAT_VOLUME = 0X100;
    private static final int MSG_WHAT_LIVE_PACKGET = 0X101;
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
                case MSG_WHAT_LIVE_PACKGET:{
                    serialPortModelPool.addSendPortModel(liveSendModel);
                    //10s间隔
                    sendEmptyMessageDelayed(MSG_WHAT_LIVE_PACKGET,10000);
                }break;
            }
        }
    };

    private SerialPortContentObserver serialPortContentObserver;

    private HHTDeviceManager hhtDeviceManager;

    private V811MicMuteBroadcast v811MicMuteBroadcast;
    private V811ScreenStatusBroadcast v811ScreenBroadcast;

    private SerialPortModelPool serialPortModelPool;

    private PersisentStatus persisentStatus;
    /**
     * 是否为自己主动改变Android对应功能状态的标记
     */
    private boolean selfChangeVolume = false;
    private boolean selfChangeVolumeMute = false;
    private boolean selfChangeMicMute = false;

    private SendSerialPortModel liveSendModel;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"service oncreate");
        initSerialPortListener();
        initSerialPortDAOListener();
        initStandardAndroidStatusListener();
        initV811PlatformStatusListener();

        initPersistenceStatus();
        initLivePacket();
        serialPortModelPool = SerialPortModelPool.getInstance();


        //justTest();
    }


    private void justTest() {

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
     * 初始化心跳数据包
     */
    private void initLivePacket() {
        liveSendModel = new LiveSendModel(serialPortUtils);
        handler.sendEmptyMessageDelayed(MSG_WHAT_LIVE_PACKGET,3000);
    }

    /**
     * 监听V811平台 部分特殊功能状态变化
     */
    private void initV811PlatformStatusListener() {
        v811MicMuteBroadcast = new V811MicMuteBroadcast(this);
        this.registerReceiver(v811MicMuteBroadcast, v811MicMuteBroadcast.getIntentFilter());

        v811ScreenBroadcast = new V811ScreenStatusBroadcast(this);
        this.registerReceiver(v811ScreenBroadcast,v811ScreenBroadcast.getIntentFilter());
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

        //直接开启，正确流程应该需判断uart口状态
        serialPortUtils.openSerialPort();


        //串口数据监听事件
        serialPortUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] buffer, int size) {


                mBuffer = buffer;
                String content = SerialPortUtils.bytesToHexString(mBuffer).toUpperCase();
                Log.d(TAG, "receiver content:" + content);
                RecevierSerialPortModel recevierSerialPortModel = RecevierSerialPortModel.getSerialPortModelByControllingCode(content, size, SerialPortService.this,hhtDeviceManager);
                if (recevierSerialPortModel != null) {
                    if(recevierSerialPortModel.changeAndroidDevice){
                        switch (recevierSerialPortModel.changeAndroidFunctionType){
                            case RecevierSerialPortModel.MIC_MUTE_FUNCTION_TYPE:{
                                selfChangeMicMute = true;
                            }break;
                            case RecevierSerialPortModel.VOLUME_MUTE_FUNCTION_TYPE:{
                                selfChangeVolumeMute = true;
                            }break;
                            case RecevierSerialPortModel.VOLUME_CHANGED_FUNCTION_TYPE:{
                                selfChangeVolume = true;
                            }break;
                        }
                    }
                    recevierSerialPortModel.action();

                    //回复答应
                    serialPortUtils.sendSerialPort(recevierSerialPortModel.retryContent());
                    //处理同步设备状态
                    if(recevierSerialPortModel instanceof SyncStatusRecevierModel){
                        syncDeviceStatusToOps();
                    }
                } else {
                    //处理对方答应信息，移除在重发队列
                    serialPortModelPool.removePoolSendSerialPortModel(RecevierSerialPortModel.getTargetCode(content, size));
                }

            }
        });
    }



    private void syncDeviceStatusToOps() {
        SendSerialPortModel model = new VolumeSendModel(serialPortUtils, persisentStatus.volume);
        serialPortModelPool.addSendPortModel(model);


        model = new MicMuteSendModel(serialPortUtils, persisentStatus.micMute);
        serialPortModelPool.addSendPortModel(model);

        model = new VolumeMuteSendModel(serialPortUtils, persisentStatus.volumeMute);
        serialPortModelPool.addSendPortModel(model);


    }


    /**
     * 处理按键转发
     * @param keyEventBean
     */
    @Override
    public void getKeyEvent(KeyEventBean keyEventBean) {
        Log.d(TAG,"getkeyevent:"+keyEventBean.toString());
        SendSerialPortModel sendModel = null;

        switch (keyEventBean.getKeyIntent()){
            case SerialPortDAO.KeyInent.REPEAT: {
                dealRepeatKey(keyEventBean.getKeycode());
                return;
            }
            case SerialPortDAO.KeyInent.DOWN:{
                dealSingleKey(keyEventBean.getKeycode());
                return;
            }
            case SerialPortDAO.KeyInent.CUSTOM:{
                dealCustomKey(keyEventBean.getKeycode());
                return ;
            }
            default:{

            }break;
        }

    }

    /**
     * 处理特殊消息(通常非按键真实发送，主要用于跟Android系统 特殊业务的数据通信)
     * @param keycode 非原意的键值，下述注释说明意图
     */
    private void dealCustomKey(int keycode) {
        SendSerialPortModel sendModel = null;
        switch (keycode) {
            //Android 系统准备升级
            case KeyEvent.KEYCODE_STEM_1:{
                sendModel = new AndroidUpgradeProcessSendModel(serialPortUtils,true);
            }break;
            //Android 系统升级失败
            case KeyEvent.KEYCODE_STEM_2:{
                sendModel = new AndroidUpgradeProcessSendModel(serialPortUtils,false);
            }break;
            //音频版升级成功
            case KeyEvent.KEYCODE_STEM_PRIMARY:{
                sendModel = new AudioUpgradeResultSendModel(serialPortUtils,true);
            }break;
            //音频版升级失败
            case KeyEvent.KEYCODE_STEM_3:{
                sendModel = new AudioUpgradeResultSendModel(serialPortUtils,false);
            }break;
            default: {

            }
            break;
        }

        if (sendModel != null) {
            serialPortModelPool.addSendPortModel(sendModel);
        }
    }

    /**
     * 处理短按按键消息
     * @param keycode
     */
    private void dealSingleKey(int keycode){
        SendSerialPortModel sendModel = null;
        switch (keycode) {
            case KeyEvent.KEYCODE_0: {
                sendModel = new Number0SendModel(serialPortUtils,SerialPortDAO.KeyInent.DOWN);
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
                sendModel = new UpSendModel(serialPortUtils,SerialPortDAO.KeyInent.DOWN);
            }
            break;
            case KeyEvent.KEYCODE_DPAD_DOWN: {
                sendModel = new DownSendModel(serialPortUtils,SerialPortDAO.KeyInent.DOWN);
            }
            break;
            case KeyEvent.KEYCODE_DPAD_LEFT: {
                sendModel = new LeftSendModel(serialPortUtils,SerialPortDAO.KeyInent.DOWN);
            }
            break;
            case KeyEvent.KEYCODE_DPAD_RIGHT: {
                sendModel = new RightSendModel(serialPortUtils,SerialPortDAO.KeyInent.DOWN);
            }
            break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER: {
                sendModel = new EnterSendModel(serialPortUtils,SerialPortDAO.KeyInent.DOWN);
            }
            break;
            case KeyEvent.KEYCODE_FORWARD_DEL:
            case KeyEvent.KEYCODE_DEL: {
                sendModel = new DelSendModel(serialPortUtils);
            }
            break;
            case KeyEvent.KEYCODE_ZOOM_IN: {
                sendModel = new ZoomInSendModel(serialPortUtils,SerialPortDAO.KeyInent.DOWN);
            }
            break;
            case KeyEvent.KEYCODE_ZOOM_OUT: {
                sendModel = new ZoomOutSendModel(serialPortUtils,SerialPortDAO.KeyInent.DOWN);
            }
            break;
            //TODO just test new function to remove this
            //Setting
            case 465:{
                Log.d(TAG,"continue live package");
                handler.sendEmptyMessageDelayed(MSG_WHAT_LIVE_PACKGET,3000);
            }break;
            //菜单
            case 467:{
                Log.d(TAG,"stop live package");
                handler.removeCallbacksAndMessages(null);
            }break;
//            case KeyEvent.KEYCODE_PLUS:{
//                sendModel = new KeyPlusSendModel(serialPortUtils,SerialPortDAO.KeyInent.DOWN);
//            }break;
            default: {

            }
            break;
        }

        if (sendModel != null) {
            serialPortModelPool.addSendPortModel(sendModel);
        }
    }


    /**
     * 处理长按按键消息
     * @param keycode
     */
    private void dealRepeatKey(int keycode){
        SendSerialPortModel sendModel = null;
        switch (keycode){
            case KeyEvent.KEYCODE_0: {
                sendModel = new Number0SendModel(serialPortUtils,SerialPortDAO.KeyInent.REPEAT);
            }
            break;
            case KeyEvent.KEYCODE_DPAD_UP: {
                sendModel = new UpSendModel(serialPortUtils,SerialPortDAO.KeyInent.REPEAT);
            }
            break;
            case KeyEvent.KEYCODE_DPAD_DOWN: {
                sendModel = new DownSendModel(serialPortUtils,SerialPortDAO.KeyInent.REPEAT);
            }
            break;
            case KeyEvent.KEYCODE_DPAD_LEFT: {
                sendModel = new LeftSendModel(serialPortUtils,SerialPortDAO.KeyInent.REPEAT);
            }
            break;
            case KeyEvent.KEYCODE_DPAD_RIGHT: {
                sendModel = new RightSendModel(serialPortUtils,SerialPortDAO.KeyInent.REPEAT);
            }
            break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER: {
                sendModel = new EnterSendModel(serialPortUtils,SerialPortDAO.KeyInent.REPEAT);
            }
            break;
            case KeyEvent.KEYCODE_ZOOM_IN: {
                sendModel = new ZoomInSendModel(serialPortUtils,SerialPortDAO.KeyInent.REPEAT);
            }
            break;
            case KeyEvent.KEYCODE_ZOOM_OUT: {
                sendModel = new ZoomOutSendModel(serialPortUtils,SerialPortDAO.KeyInent.REPEAT);
            }
            break;
            case KeyEvent.KEYCODE_PLUS:{
                sendModel = new KeyPlusSendModel(serialPortUtils,SerialPortDAO.KeyInent.REPEAT);
            }break;
            default:{

            }break;
        }
        if(sendModel!= null){
            sendModel.sendContent();
        }

    }


    @Override
    public void onMuteChange(boolean mute) {
        Log.d(TAG,"onMuteChange:"+mute);
        Log.d(TAG,"onMuteChange isslef:"+ selfChangeVolumeMute);
        if (isSelfChangeAndroidFunction(RecevierSerialPortModel.VOLUME_MUTE_FUNCTION_TYPE)) {
            persisentStatus.volumeMute = mute;
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
        Log.d(TAG,"onVolumeChange:"+value);
        Log.d(TAG,"onVolumeChange isslef:"+ selfChangeVolume);
        if (isSelfChangeAndroidFunction(RecevierSerialPortModel.VOLUME_CHANGED_FUNCTION_TYPE)) {
            persisentStatus.volume = value;
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
        if (isSelfChangeAndroidFunction(RecevierSerialPortModel.MIC_MUTE_FUNCTION_TYPE)) {
            persisentStatus.micMute = isMute;
            return;
        }
        if (persisentStatus.micMute == isMute) {
            return;
        }
        persisentStatus.micMute = isMute;
        SendSerialPortModel model = new MicMuteSendModel(serialPortUtils, isMute);
        serialPortModelPool.addSendPortModel(model);
    }


    @Override
    public void screenStatusChanged(boolean isSleep) {
        Log.d(TAG,"screenStatusChanged:"+isSleep);
        SendSerialPortModel model = new ScreenStatusSendModel(serialPortUtils, isSleep);
        serialPortModelPool.addSendPortModel(model);
    }


    private boolean isSelfChangeAndroidFunction(int functionType) {
        switch (functionType){
            case RecevierSerialPortModel.MIC_MUTE_FUNCTION_TYPE:{
                if(selfChangeMicMute){
                    selfChangeMicMute = false;
                    return true;
                }

            }
            case RecevierSerialPortModel.VOLUME_MUTE_FUNCTION_TYPE:{
                if(selfChangeVolumeMute){
                    selfChangeVolumeMute = false;
                    return true;
                }
            }
            case RecevierSerialPortModel.VOLUME_CHANGED_FUNCTION_TYPE:{
                if(selfChangeVolume){
                    selfChangeVolume = false;
                    return true;
                }
            }
        }

        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"service ondestory");
        handler.removeCallbacksAndMessages(null);
        handler = null;

        serialPortUtils.closeSerialPort();

        serialPortContentObserver.removeSerialPortContentObserver(this);
        serialPortContentObserver.release();
        unregisterReceiver(v811MicMuteBroadcast);
        unregisterReceiver(v811ScreenBroadcast);
        serialPortModelPool.release();
        v811MicMuteBroadcast.release();
        v811ScreenBroadcast.release();

        Intent intent = new Intent(this,SerialPortService.class);
        startService(intent);
    }



}
