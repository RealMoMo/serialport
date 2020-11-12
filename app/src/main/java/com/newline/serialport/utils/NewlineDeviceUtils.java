package com.newline.serialport.utils;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

import com.ist.android.tv.IstBoardInfo;
import com.ist.android.tv.IstCommonManager;
import com.ist.android.tv.util.IstConstant;
import com.newline.serialport.setting.utils.SystemPropertiesUtils;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/8/27 16:49
 * @describe
 */
public class NewlineDeviceUtils {


    /**
     *
     * @return 产品版本号
     */
    public static String getProductVersion(){
       return (String) SystemPropertiesUtils.getProperty("ro.product.version", "");
    }


    /**
     *
     * @return 版控信息
     */
    public static String getVersionControlInfo(){
        return (String) SystemPropertiesUtils.getProperty("persist.sys.versionCustomer", "");
    }


    /**
     *
     * @return 获取序列号
     */
    public static String getSerialNumber(){
        return IstBoardInfo.getInstance().getProductImei("hht");
    }


    /**
     *
     * @return 获取集成包版本号
     */
    public static String getFullPackageVersion(){
        return (String) SystemPropertiesUtils.getProperty("persist.product.version", "");
    }



    /**
     * 设置集成包版本号
     */
    public static void setFullPackageVersion(String version){
        SystemPropertiesUtils.setProperty("persist.product.version", version);
    }

    /**
     *
     * @return 获取大屏型号
     */
    public static String getModelType(){
        return (String) SystemPropertiesUtils.getProperty("persist.sys.product.model", "");
    }


    /**
     *
     * @return 获取音频版版本号
     */
    public static String getAudioVersion(){
        //"CP-A08-HT01-V1.0.x"  型号+版本号
        String fullInfo = (String) SystemPropertiesUtils.getProperty("persist.cenpo.ver", "");


        int i = fullInfo.lastIndexOf("-");
        if(i == -1){
           return "";
        }else{
            return fullInfo.substring(i+1, fullInfo.length());
        }
    }

    /**
     *
     * @return 获取音频版型号
     */
    public static String getAudioModel(){
        //"CP-A08-HT01-V1.0.x"  型号+版本号
        String fullInfo = (String) SystemPropertiesUtils.getProperty("persist.cenpo.ver", "");

        int i = fullInfo.lastIndexOf("-");

        if(i == -1){
            return "";
        }else{
            return fullInfo.substring(0, i);
        }
    }

    /**
     * 唤醒Android大屏
     * @param context
     */
    public static void wakeupDevice(Context context){
        if (IstCommonManager.getInstance().isSystemSleep()) {
            Intent intent = new Intent(IstConstant.ACTION_SYSTEM_WAKE);
            context.sendBroadcast(intent);
        }
    }


    /**
     * 息屏Android
     * @param context
     */
    public static void sleepDevice(Context context){
        if (!IstCommonManager.getInstance().isSystemSleep()) {
            Intent intent = new Intent(IstConstant.ACTION_SYSTEM_SLEEP);
            context.sendBroadcast(intent);
        }
    }

    /**
     * 是否息屏状态
     * @return
     */
    public static boolean isSleep(){
        return IstCommonManager.getInstance().isSystemSleep();
    }

    /**
     * 通知关机
     * @param context
     */
    public static void sendPowerOffMsg(Context context){
        Intent intent = new Intent("com.hht.tencent_shutdown");
        context.sendBroadcast(intent);
    }


    /**
     * 通知重启
     * @param context
     */
    public static void sendRebootMsg(Context context){
        Intent intent = new Intent("com.hht.tencent_reboot");
        context.sendBroadcast(intent);
    }




}
