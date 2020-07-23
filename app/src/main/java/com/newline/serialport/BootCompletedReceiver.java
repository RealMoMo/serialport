package com.newline.serialport;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.newline.serialport.utils.StartActivityManager;
import com.hht.tools.log.LogIntent;
import com.newline.serialport.utils.StartActivityManager;

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        StartActivityManager.startSerialPort(context);
    }
}
