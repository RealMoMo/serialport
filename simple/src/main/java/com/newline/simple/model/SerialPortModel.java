package com.newline.simple.model;

import android.content.Context;
import android.util.Log;

import com.hht.tools.log.Logger;

public class SerialPortModel {

    private static String targetCode = "";

    //Set xx 未处理
    public static SerialPortModel getSerialPortModelByControllingCode(String controllingCode, int size) {
        char[] codes = controllingCode.toCharArray();
        Log.i("HWL", " targetCode codes[0] = "+codes[0]+"  codes[1] = "+codes[1]);
        if (codes[0] == '7' && codes[1] == 'F') {
            targetCode = "";
        } else {
            targetCode += " ";
        }
        for (int i = 0; i < size * 2; i++) {
            targetCode += codes[i];
            if (i!= 0 && i % 2 != 0 && i!=(size*2 -1)) {
                targetCode += " ";
            }
        }
        Log.i("HWL", " targetCode "+targetCode);
        switch (targetCode) {
            case SerialPortMuteUnMute.CONTROLLING_CODE: {//无法做
                targetCode = "";
                return new SerialPortMuteUnMute();
            }
        }
        Logger.i(" length =  "+ targetCode.length());

        return null;
    }

    private static String catchKeyValue(String targetCode) {
        String keyStr = targetCode.substring(27, 29);
        Logger.i("keyStr =" +keyStr);
        return keyStr;
    }

    public String getReturnCode() {
        return "";
    }

    public void action(Context context) {
        Log.i(SerialPortModel.class.getSimpleName(), " do noting ");
    }
}
