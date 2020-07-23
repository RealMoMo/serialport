package com.newline.serialport.model.recevier;

import android.util.Log;

import com.newline.serialport.setting.HHTDeviceManager;
import java.lang.ref.WeakReference;
import java.math.BigInteger;

public abstract class RecevierSerialPortModel {

    private static String targetCode = "";

    WeakReference<HHTDeviceManager> hhtDeviceManager;

    public RecevierSerialPortModel(HHTDeviceManager hhtDeviceManager) {
        this.hhtDeviceManager = new WeakReference(hhtDeviceManager);
    }

    //Set xx 未处理
    public static RecevierSerialPortModel getSerialPortModelByControllingCode(String controllingCode, int size, HHTDeviceManager hhtDeviceManager) {
        char[] codes = controllingCode.toCharArray();
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

        switch (targetCode) {
            case V811MicMuteRecevierModel.CONTROLLING_CODE:{
                return  new V811MicMuteRecevierModel(hhtDeviceManager);
            }
            case V811MicUnMuteRecevierModel.CONTROLLING_CODE:{
                return  new V811MicUnMuteRecevierModel(hhtDeviceManager);
            }
            case VolumeMuteRecevierModel.CONTROLLING_CODE:{
                return  new VolumeMuteRecevierModel(hhtDeviceManager);
            }
            case VolumeUnMuteRecevierModel.CONTROLLING_CODE:{
                return new VolumeUnMuteRecevierModel(hhtDeviceManager);
            }
            default:{

            }break;
        }

        if(targetCode.length() == 32) {
            if (targetCode.contains(VolumeRecevierModel.CONTROLLING_CODE)) {
                return new VolumeRecevierModel(hhtDeviceManager, hexStringtoInt(catchKeyValue(targetCode)));
            }
        }

        return null;
    }


    public abstract void action();

    public abstract String retryContent();


    public static String getTargetCode(String controllingCode, int size){
        char[] codes = controllingCode.toCharArray();
        String temp ="";
        if (codes[0] == '7' && codes[1] == 'F') {
            temp = "";
        } else {
            temp += " ";
        }
        for (int i = 0; i < size * 2; i++) {
            temp += codes[i];
            if (i!= 0 && i % 2 != 0 && i!=(size*2 -1)) {
                temp += " ";
            }
        }
        return temp;
    }

    /**
     * 获取指令倒数第二段内容
     * @param targetCode
     * @return
     */
    private static String catchKeyValue(String targetCode) {
        String value = targetCode.substring(27, 29);
        return value;
    }

    private static int hexStringtoInt(String hexCount){
        BigInteger big = new BigInteger(hexCount, 16);
        return big.intValue();
    }
}
