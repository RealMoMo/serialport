package com.newline.serialport.pool;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.newline.serialport.model.send.SendSerialPortModel;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/22 15:52
 * @describe 串口发送数据包对象缓冲池（添加发送失败重发机制）
 */
public class SerialPortModelPool {
//    private static final String TAG = "SerialPortModelPool";
    private static final String TAG = "newlinePort";
    private static final SerialPortModelPool ourInstance = new SerialPortModelPool();
    private static final int MAX_RETRY_TIMES = 3;
    private static final int MSG_RETRY_SEND = 0X200;
    private static final int SEND_INTERVAL_TIMEMILLS = 200;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(retryTimes >= MAX_RETRY_TIMES){
                clearRetryData();
                startNextSend();
            }else{
                if(currentModel==null){
                    clearRetryData();
                   startNextSend();
                }else{
                    retrySend();
//                    retryTimes++;
//                    currentModel.sendContent();
//                    sendEmptyMessageDelayed(MSG_RETRY_SEND,SEND_INTERVAL_TIMEMILLS);
                }
            }
        }
    };
    public static SerialPortModelPool getInstance() {
        return ourInstance;
    }
    private int count ;
    private SerialPortModelPool() {
    }
    //等待队列
    private Queue<SendSerialPortModel> waitModelQueue = new ConcurrentLinkedQueue<>();
    //重连队列
    private Queue<SendSerialPortModel> tryQueue = new ConcurrentLinkedQueue<>();
    private SendSerialPortModel currentModel;
    private SendSerialPortModel tempModel;
    private String currentContent;

    private int retryTimes = 0;


    public void addSendPortModel(SendSerialPortModel sendSerialPortModel){
        Log.d(TAG,"add pool:"+sendSerialPortModel.getSendContent());
        waitModelQueue.add(sendSerialPortModel);
        if(tryQueue.isEmpty()){
           startSend(waitModelQueue.poll());
        }
    }


    public void removePoolSendSerialPortModel(String content){
        Log.d(TAG,"remove content:"+content);
        if(currentContent == null){
            return;
        }
        if(content.equals(currentContent)){
            clearRetryData();
            startNextSend();
        }
    }

    public void release(){
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }

    private void startSend(SendSerialPortModel sendSerialPortModel){
        retryTimes = 0;
        currentModel = sendSerialPortModel;
        sendSerialPortModel.sendContent();
        count++;
        currentContent = sendSerialPortModel.getSendContent();
        tryQueue.add(currentModel);
        mHandler.sendEmptyMessageDelayed(MSG_RETRY_SEND,SEND_INTERVAL_TIMEMILLS);
    }

    private void startNextSend(){
        tempModel = waitModelQueue.poll();
        if(tempModel == null){
            mHandler.removeCallbacksAndMessages(null);
        }else{
            startSend(tempModel);
        }
    }

    private void clearRetryData(){
        retryTimes = 0;
        currentModel = null;
        currentContent = null;
        tryQueue.clear();
        mHandler.removeMessages(MSG_RETRY_SEND);
        Log.d(TAG,"clear");
    }

    private synchronized void retrySend(){
        if(currentModel ==null){
            clearRetryData();
            startNextSend();
        }else{
            retryTimes++;
            currentModel.sendContent();
            mHandler.sendEmptyMessageDelayed(MSG_RETRY_SEND,SEND_INTERVAL_TIMEMILLS);
        }
    }

}
