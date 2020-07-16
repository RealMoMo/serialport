package com.newline.serialport.setting.utils;

import android.support.annotation.IntDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/4/11 10:06
 * @describe
 */
public class HHTPlatformUtils {
    private static final String KEY_SYSTEM_PRODUCT = "persist.sys.hht.Product";
    private static final String KEY_SYSTEM_PLATFORM = "persist.hht.Platforms";

    private static final String KEY_SYSTEM_PLATFORM2 = "persist.hht.Platforms2";

    private static final String KEY_X_VN_MS828_PLATFORM = "persist.type.x9";

    public static final String SYSTEM_PRODUCT_MS848 = "MS848";
    public static final String SYSTEM_PRODUCT_MS828 = "MS828";

    private static final String CLIENT_PREDIA = "PREDIA";
    private static final String CLIENT_GENEE = "GENEE";
    private static final String CLIENT_JECTOR = "JECTOR";//捷达UI 跟随OEM
    private static final String SERIES_OEM = "OEM";
    private static final String SERIES_RS = "RS";

    /**
     * HHTPlatform naming rules
     * series + chip + client(nullable) + NonAndroid(nullable)
     * Interspersed with an underscore
     * If there is a blank in the middle, use the # symbol instead.
     * like: X_MS828 , OEM_MS828_PREDIA ,OEM_MS828_#_NA
     */
    @IntDef({
            HHTPlatform.X_MS828,
            HHTPlatform.VN_MS828,
            HHTPlatform.OEM_MS828, HHTPlatform.OEM_MS828_PREDIA, HHTPlatform.OEM_MS828_GENEE, HHTPlatform.OEM_MS828_JECTOR,
            HHTPlatform.RS_MS828,
            HHTPlatform.VNX_MS848,
            HHTPlatform.OEM_MS848,
            HHTPlatform.RS_MS848,
            HHTPlatform.Z_MS848,
            HHTPlatform.UNKNOWN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HHTPlatform {
        int X_MS828 = 1;
        int VN_MS828 = 10;
        int OEM_MS828 = 20;
        int OEM_MS828_PREDIA = 21;
        int OEM_MS828_GENEE = 22;
        int OEM_MS828_JECTOR = 23;
        int RS_MS828 = 30;
        int VNX_MS848 = 40;
        int OEM_MS848 = 50;
        int RS_MS848 = 60;
        int Z_MS848 = 70;
        int UNKNOWN = 99;
    }

    private static final String X_MS828 = "X_MS828";
    private static final String VN_MS828 = "VN_MS828";
    private static final String OEM_MS828 = "OEM_MS828";
    private static final String OEM_MS828_PREDIA = "OEM_MS828_PREDIA";
    private static final String OEM_MS828_GENEE = "OEM_MS828_GENEE";
    private static final String OEM_MS828_JECTOR = "OEM_MS828_JECTOR";
    private static final String RS_MS828 = "RS_MS828";
    private static final String VN_MS848 = "VN_MS848";
    private static final String OEM_MS848 = "OEM_MS848";
    private static final String RS_MS848 = "RS_MS848";
    private static final String Z_MS848 = "Z_MS848";
    private static final String UNKNOWN = "UNKNOWN";

    private static String generateHHTPlatformType(String series, String chip) {
        if (isOEMSeries(series)) {
            return SERIES_OEM + "_" + chip + "_" + series;
        }else if(UNKNOWN.equals(series)){
            return UNKNOWN;
        } else {
            return series + "_" + chip;
        }
    }


    @Deprecated
    private static boolean isOEMSeries(String series) {
        return CLIENT_PREDIA.equals(series) || CLIENT_GENEE.equals(series) || CLIENT_JECTOR.equals(series);
    }

    /**
     * Keep the method of obsolete judgment vn or x platform,
     * please use getprop "persist.hht.Platforms" type to judge
     * @return
     */
    @Deprecated
    private static String getVNXSeries(){
        String type = (String)SystemPropertiesUtils.getProperty(KEY_X_VN_MS828_PLATFORM,"-1");
        if("1".equals(type)){
            return X_MS828;
        }else if("0".equals(type)){
            return VN_MS828;
        }else{
            return UNKNOWN;
        }
    }

    /**
     * Read a platform systemproperties var  unified  : persist.hht.Platforms
     * @return
     */
    @Deprecated
    @HHTPlatform
    public static int getHHTPlatformType() {
        String series = getSystemPlatformType();
        String chip = getChipType();
        String type = generateHHTPlatformType(series, chip);
        Log.i("HHTLibs", "type = "+type);
       return getHHTPlatformType(type);
    }

    /**
     * Read a platform systemproperties var  unified  : persist.hht.Platforms2
     * @return
     */
    @HHTPlatform
    public static int getHHTPlatform2Type() {
        String type = getSystemPlatform2Type();
        return getHHTPlatformType(type);
    }

    @HHTPlatform
    private static int getHHTPlatformType(String type){
        switch (type) {
            case X_MS828:{
                return HHTPlatform.X_MS828;
            }
            case VN_MS828: {
                return HHTPlatform.VN_MS828;
            }
            case OEM_MS828: {
                return HHTPlatform.OEM_MS828;
            }
            case OEM_MS828_GENEE: {
                return HHTPlatform.OEM_MS828_GENEE;
            }
            case OEM_MS828_JECTOR: {
                return HHTPlatform.OEM_MS828_JECTOR;
            }
            case OEM_MS828_PREDIA: {
                return HHTPlatform.OEM_MS828_PREDIA;
            }
            case RS_MS828: {
                return HHTPlatform.RS_MS828;
            }
            case VN_MS848: {
                return HHTPlatform.VNX_MS848;
            }
            case OEM_MS848: {
                return HHTPlatform.OEM_MS848;
            }
            case RS_MS848: {
                return HHTPlatform.RS_MS848;
            }
            case Z_MS848: {
                return HHTPlatform.Z_MS848;
            }
            case UNKNOWN:
            default: {
                return HHTPlatform.UNKNOWN;
            }
        }
    }


    public static String getChipType(){
        return (String)SystemPropertiesUtils.getProperty(KEY_SYSTEM_PRODUCT,SYSTEM_PRODUCT_MS828);
    }

    @Deprecated
    public static String getSystemPlatformType(){
        return (String)SystemPropertiesUtils.getProperty(KEY_SYSTEM_PLATFORM,UNKNOWN);
    }

    public static String getSystemPlatform2Type(){
        return (String)SystemPropertiesUtils.getProperty(KEY_SYSTEM_PLATFORM2,UNKNOWN);
    }





}
