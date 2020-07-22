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
public class V811MicMuteBroadcast extends BroadcastReceiver {

    public interface MicStatusListener{
        void micMuteStatusChanged(boolean isMute);
    }



    private static final String ACTION_MIC_MUTE_STATUS_CHANGE = "com.ist.launcher.microphone_state";
    //boolean extra
    private static final String EXTRA_MIC_STATUS =  "action";

    private MicStatusListener micStatusListener;

    public V811MicMuteBroadcast(MicStatusListener listener){
        micStatusListener = listener;
    }

    public IntentFilter getIntentFilter(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_MIC_MUTE_STATUS_CHANGE);
        return intentFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean isMute = !intent.getBooleanExtra(EXTRA_MIC_STATUS,true);
        micStatusListener.micMuteStatusChanged(isMute);
    }

    public void release(){
        micStatusListener = null;
    }
}
