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
 * @describe
 */
public class SerialPortModelPool {

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
                retryTimes++;
                currentModel.sendContent();
                Log.d("realmo","retry:"+retryTimes);
                Log.d("realmo","retry content:"+currentModel.getSendContent());
                sendEmptyMessageDelayed(MSG_RETRY_SEND,SEND_INTERVAL_TIMEMILLS);
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
        Log.d("realmo","add pool");
        waitModelQueue.add(sendSerialPortModel);
        if(tryQueue.isEmpty()){
           startSend(waitModelQueue.poll());
        }
    }


    public void removePoolSendSerialPortModel(String content){
        Log.d("realmo","remove content:"+content);
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
//        Log.d("realmo","start init:"+currentModel.getSendContent());
//        Log.d("realmo","start content:"+currentContent);
        Log.d("realmo","send count:"+count);
    }

    private void startNextSend(){
        tempModel = waitModelQueue.poll();
        if(tempModel == null){
            Log.d("realmo","end");
            mHandler.removeCallbacksAndMessages(null);
        }else{
            startSend(tempModel);
            Log.d("realmo","start next:"+currentModel.getSendContent());
        }
    }

    private void clearRetryData(){
        retryTimes = 0;
        currentModel = null;
        currentContent = null;
        tryQueue.clear();
        mHandler.removeMessages(MSG_RETRY_SEND);
        Log.d("realmo","clear");
    }

}
