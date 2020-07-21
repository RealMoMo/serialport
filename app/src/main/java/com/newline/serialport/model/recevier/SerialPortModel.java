package com.newline.serialport.model.recevier;

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
            case SerialPortPowerOff.CONTROLLING_CODE: {//发送关机广播
                targetCode = "";
                return new SerialPortPowerOff();
            }
            case SerialPortPageUp.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_PAGE_UP
                targetCode = "";
                return new SerialPortPageUp();
            }
            case SerialPortPageDown.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_PAGE_DOWN
                targetCode = "";
                return new SerialPortPageDown();
            }
            case SerialPortMenu.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_MENU
                targetCode = "";
                return new SerialPortMenu();
            }
            case SerialPortReturn.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_BACK
                targetCode = "";
                return new SerialPortReturn();
            }
            case SerialPortOK.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_ENTER
                targetCode = "";
                return new SerialPortOK();
            }
            case SerialPortLeft.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_DPAD_LEFT
                targetCode = "";
                return new SerialPortLeft();
            }
            case SerialPortRight.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_DPAD_RIGHT
                targetCode = "";
                return new SerialPortRight();
            }
            case SerialPortUp.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_DPAD_UP
                targetCode = "";
                return new SerialPortUp();
            }
            case SerialPortDown.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_DPAD_DOWN
                targetCode = "";
                return new SerialPortDown();
            }
            case SerialPortMuteOn.CONTROLLING_CODE: {//打开静音 新增 0426.xlsx
                targetCode = "";
                return new SerialPortMuteOn();
            }
            case SerialPortMuteOff.CONTROLLING_CODE: {//关闭静音 新增 0426.xlsx
                targetCode = "";
                return new SerialPortMuteOff();
            }
        }

        return null;
    }



    public String getReturnCode() {
        return null;
    }

    public void action(Context context) {
    }
}
