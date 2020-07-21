package com.newline.serialport.chip;

import android.content.Context;
import com.hht.tools.log.Logger;


public class UniteImpl {

    public static boolean isMute() {

            return Mstar.isMute();

    }

    public static boolean isOpenSerialPortListener() {

            try {
                return Mstar.isUARTOnOff();
            } catch (Exception e) {
                e.printStackTrace();
            }


        return false;
    }

    public static int getCurrentSource(Context context) {

            try {
                return Mstar.getCurrentSource(context);
            } catch (Exception e) {
                Logger.e(e);
            }



        return -1;
    }

    public static void setEnvironment(String content) {

        Mstar.setEnvironment(content);

    }


    public static String getEnvironment() {
            return Mstar.getEnvironment();

    }
}
