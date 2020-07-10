package com.newline.serialport.utils;

import android.app.Instrumentation;
import android.view.KeyEvent;

import com.newline.serialport.chip.UniteImpl;
import com.hht.tools.log.Logger;

public class KeyUtils {

    public static void sendDownKey(final int keyCode) {
        new Thread(new Runnable() {

            public void run() {
                try {
                    Instrumentation inst=new Instrumentation();
                    inst.sendKeySync(new KeyEvent(KeyEvent.ACTION_DOWN , keyCode));
                } catch (Exception e) {
                   Logger.e(e);
                }
            }
        }).start();
    }

    public static void sendUpKey(final int keyCode) {
        new Thread(new Runnable() {

            public void run() {
                try {
                    Instrumentation inst=new Instrumentation();
                    inst.sendKeySync(new KeyEvent(KeyEvent.ACTION_UP , keyCode));
                } catch (Exception e) {
                    Logger.e(e);
                }
            }
        }).start();
    }

    public static void sendSyncKey(final int keyCode) {

        new Thread(new Runnable() {

            public void run() {
                try {
                    Instrumentation inst=new Instrumentation();
                    inst.sendKeyDownUpSync(keyCode);
                } catch (Exception e) {
                    Logger.e(e);
                }
            }
        }).start();
    }
}
