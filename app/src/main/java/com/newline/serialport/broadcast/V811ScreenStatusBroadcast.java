package com.newline.serialport.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/22 9:27
 * @describe
 */
public class V811ScreenStatusBroadcast extends BroadcastReceiver {

    public interface ScreenStatusListener{
        void screenStatusChanged(boolean isSleep);
    }


    //息屏广播
    private static final String ACTION_V811_SLEEP = "com.ist.broadcast.action.systemsleep";
    //亮屏广播
    private static final String ACTION_V811_WAKEUP = "com.ist.broadcast.action.systemwake";


    private ScreenStatusListener screenStatusListener;

    public V811ScreenStatusBroadcast(ScreenStatusListener listener){
        screenStatusListener = listener;
    }

    public IntentFilter getIntentFilter(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_V811_SLEEP);
        intentFilter.addAction(ACTION_V811_WAKEUP);
        return intentFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action  = intent.getAction();
        if(action == null){
            return ;
        }
        switch (action){
            case ACTION_V811_SLEEP:{
                screenStatusListener.screenStatusChanged(true);
            }break;
            case ACTION_V811_WAKEUP:{
                screenStatusListener.screenStatusChanged(false);
            }break;
        }

    }

    public void release(){
        screenStatusListener = null;
    }
}
