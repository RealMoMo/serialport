package com.newline.serialport;

import android.app.Application;

import com.newline.serialport.utils.CrashHandler;


public class HHTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}
