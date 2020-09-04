package com.newline.serialport.utils;

import com.ist.android.tv.IstBoardInfo;
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
    public static String getSystemInfo(){
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




}
