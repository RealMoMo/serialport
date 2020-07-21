package com.newline.serialport.dao.observer;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

import com.newline.serialport.dao.KeyEventDAO;
import com.newline.serialport.dao.SerialPortDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/16 16:25
 * @describe
 */
public class SerialPortContentObserver extends ContentObserver {

    public interface SerialPortDAOChangeListener{
        /**
         * 获取最新的按键事件
         * @param keycode
         */
        void getKeyEvent(int keycode);

    }

    private static SerialPortContentObserver mInstance;
    private static Handler mHandler;
    private static Context applicationContext;

    private List<SerialPortDAOChangeListener> observerList;


    public static SerialPortContentObserver getInstance(Context context){
        if(mInstance == null){
            synchronized (SerialPortContentObserver.class){
                if(mInstance == null){
                    applicationContext = context.getApplicationContext();
                    mHandler = new Handler();
                    mInstance = new SerialPortContentObserver(mHandler);
                    applicationContext.getContentResolver().registerContentObserver(SerialPortDAO.SERIAL_PORT_URI,true,mInstance);
                }
            }
        }
        return mInstance;
    }


    public boolean addSerialPortContentObserver(SerialPortDAOChangeListener listener){
       return observerList.add(listener);
    }

    public boolean removeSerialPortContentObserver(SerialPortDAOChangeListener listener){
        return observerList.remove(listener);
    }

    /**
     * 释放资源
     */
    public void release(){
        applicationContext.getContentResolver().unregisterContentObserver(mInstance);
        observerList.clear();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        applicationContext = null;
        mInstance = null;
    }


    private SerialPortContentObserver(Handler handler) {
        super(handler);
        observerList = new ArrayList<>();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        keyEventChange();

    }

    private void keyEventChange(){
        while(true){
            Integer keycode = KeyEventDAO.keyCodeQueue.poll();
            if(keycode == null){
                break;
            }
            for (SerialPortDAOChangeListener listener : observerList) {
                    listener.getKeyEvent(keycode);
            }
        }

    }


}
