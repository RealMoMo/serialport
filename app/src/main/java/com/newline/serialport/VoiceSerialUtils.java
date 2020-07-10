package com.newline.serialport;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.hht.middleware.tools.SystemPropertiesUtils;
import com.hht.middleware.tools.log.Logger;
import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.utils.SystemUtils;
import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.utils.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import android_serialport_api.SerialPort;

import static com.newline.serialport.HHTApplication.timeStamp;
import static com.newline.serialport.HHTApplication.timeTotalStamp;

public class VoiceSerialUtils extends SerialPortUtils {
    public static boolean bEnableMic;//enable = mic open  disable = mic close;
    protected String TAG = "VoiceSerialUtils";
    protected String path = "/dev/ttyUSB0";
    protected int baudrate = 19200;

    /**
     * 打开串口
     * @return serialPort串口对象
     */
    @Override
    public SerialPort openSerialPort() {
        if (!SystemPropertiesUtils.getSysPlatforms().equals("Z5")) {
            return null;
        }
        try {
            serialPort = new SerialPort(new File(path),baudrate,0);
            this.serialPortStatus = true;
            threadStatus = false; //线程状态

            //获取打开的串口中的输入输出流，以便于串口数据的收发
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
            new VoiceReadThread().start(); //开始线程监控是否有数据要接收
            UniteImpl.isMicMute();
        } catch (IOException e) {
            Log.e(TAG, "openSerialPort: 打开串口异常：" + e.toString());
            return serialPort;
        }
        Log.d(TAG, "openSerialPort: 打开串口");
        return serialPort;
    }


    //micrphone status ContentObserver
    private VoiceSerialUtils.MicrophoneObserver microphoneObserver;
    public void registerMicrophoneObserver(Context context) {
        microphoneObserver = new VoiceSerialUtils.MicrophoneObserver(context.getContentResolver(), context);
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor(SystemUtils.MIC_MUTE_STATUS)
                , true, microphoneObserver);
    }

    public void unregisterMicrophoneObserver(Context context) {
        context.getContentResolver().unregisterContentObserver(microphoneObserver);
    }

        public class MicrophoneObserver extends ContentObserver {

        Context context;

        public MicrophoneObserver(ContentResolver handler, Context context) {
            super(new Handler());
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            boolean enableMic = SystemUtils.getMicMuteStatus(context);
            Log.i("MicrophoneObserver","changed mic mute enableMic "+enableMic);
            bEnableMic = enableMic;
            changeMicMute();
            UniteImpl.isMicMute();
        }
    }

    /**
     关于MIC Mute相关指令格式如下：
     1.麦克风阵列模组开启静音
     控制码：7F 08 99 A2 B3 C4 02 FF 01 8D CF
     返回码：7F 09 99 A2 B3 C4 02 FF 01 8D zz CF  (zz为返回状态码：01开启成功，00开启失败)
     2.麦克风阵列模组关闭静音
     控制码：7F 08 99 A2 B3 C4 02 FF 01 8E CF
     返回码：7F 09 99 A2 B3 C4 02 FF 01 8E zz CF  (zz为返回状态码：01关闭成功，00关闭失败)
     3.麦克风阵列模组静音状态查询(新增)
     控制码：7F 08 99 A2 B3 C4 02 FF 01 8F CF
     返回码：7F 09 99 A2 B3 C4 02 FF 01 8F zz CF（zz为返回状态码：01 MIC处于静音,00 MIC处于非静音）
     */

    byte[] muteBuf = {(byte)0x7F, (byte)0x08, (byte)0x99, (byte)0xA2, (byte)0xB3, (byte)0xC4, (byte)0x02, (byte)0xFF, (byte)0x01, (byte)0x8D, (byte)0xCF};
    byte[] muteFeedback = {(byte)0x7F, (byte)0x09, (byte)0x99, (byte)0xA2, (byte)0xB3, (byte)0xC4, (byte)0x02, (byte)0xFF, (byte)0x01, (byte)0x8D, (byte)0x01, (byte)0xCF};//开启Mute返回控制码 01：mute 00:unmute

    byte[] unmuteBuf = {(byte)0x7F, (byte)0x08, (byte)0x99, (byte)0xA2, (byte)0xB3, (byte)0xC4, (byte)0x02, (byte)0xFF, (byte)0x01, (byte)0x8E, (byte)0xCF};
    byte[] unMuteFeedback = {(byte)0x7F, (byte)0x08, (byte)0x99, (byte)0xA2, (byte)0xB3, (byte)0xC4, (byte)0x02, (byte)0xFF, (byte)0x01, (byte)0x8E, (byte)0x01, (byte)0xCF}; //关闭Mute返回控制码 01:unmute 00:mute

    byte[] queryMuteStatusBuf = {(byte)0x7F, (byte)0x08, (byte)0x99, (byte)0xA2, (byte)0xB3, (byte)0xC4, (byte)0x02, (byte)0xFF, (byte)0x01, (byte)0x8F, (byte)0xCF};
    byte[] queryMuteStatusFeedback = {(byte)0x7F, (byte)0x08, (byte)0x99, (byte)0xA2, (byte)0xB3, (byte)0xC4, (byte)0x02, (byte)0xFF, (byte)0x01, (byte)0x8F, (byte)0x01, (byte)0xCF};//查询码返回01:mute 00:unmute
    private static final byte[] header = {(byte)0x99, (byte)0xA2, (byte)0xB3, (byte)0xC4, (byte)0x02, (byte)0xFF};

    public void changeMicMute() {
        Log.i(TAG, "changed mic mute " +outputStream + " bEnableMic "+bEnableMic);
        try {
            if (outputStream != null) {
                if (bEnableMic) {//current unmute
                    outputStream.write(unmuteBuf);
                } else {
                    outputStream.write(muteBuf);
                }
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkMicStatus() {
        Log.i(TAG, "check mic mute " +outputStream);
        try {
            if (outputStream != null) {
                outputStream.write(queryMuteStatusBuf);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    OnMicMuteListener listener;

    public void setListener(OnMicMuteListener listener) {
        this.listener = listener;
    }

    public interface OnMicMuteListener {

        void onMute(boolean isMute);
    }

    /**
     * 单开一线程，来读数据
     */
    protected class VoiceReadThread extends Thread{
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
                        byte[] checkHeader = new byte[6];
                        System.arraycopy(buffer, 2, checkHeader, 0, 6);
                        if (buffer[0] == (byte)0x7F && buffer[size - 1] == (byte)0xCF && Arrays.equals(checkHeader, header)) {
                            Logger.i("header true");
                            if (buffer[8] == (byte) 0x01 && buffer[9] == (byte) 0x8D) {//mic mute
                                if (buffer[10] == (byte)0x01) {
                                    Log.d(TAG, "mic mute");
                                    bEnableMic = false;
                                }
                                if (listener != null) {
                                    listener.onMute(!bEnableMic);
                                }
                            } else if (buffer[8] == (byte) 0x01 && buffer[9] == (byte) 0x8E) {//mic ummute
                                if (buffer[10] == (byte)0x01) {
                                    Log.d(TAG, "mic unmute");
                                    bEnableMic = true;
                                }
                                if (listener != null) {
                                    listener.onMute(!bEnableMic);
                                }
                            } else if (buffer[8] == (byte) 0x01 && buffer[9] == (byte) 0x8F) {//mic status
                                bEnableMic = buffer[10] != (byte)0x01;//01 MIC处于静音,00 MIC处于非静音
                                Log.d(TAG, "is mic mute " + bEnableMic);
                                UniteImpl.isMicMute();
                                if (listener != null) {
                                    listener.onMute(!bEnableMic);
                                }
                            }
                        }
                        if (HHTApplication.timeTotalStamp == 0) {
                            HHTApplication.timeTotalStamp = System.currentTimeMillis();
                        }
                        HHTApplication.timeStamp = System.currentTimeMillis();
                        if (onDataReceiveListener != null) {
                            onDataReceiveListener.onDataReceive(buffer,size);
                        }
                    }
                } catch (IOException e) {
                    Log.e(TAG, "run: 数据读取异常：" +e.toString());
                }
            }

        }
    }
}
