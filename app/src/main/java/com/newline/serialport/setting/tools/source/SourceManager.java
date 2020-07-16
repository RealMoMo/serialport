package com.newline.serialport.setting.tools.source;

import android.content.Context;
import android.provider.Settings;

import com.hht.middleware.model.SourceValue;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Realmo
 * @version 1.0.0
 * @name hht setting lib
 * @email momo.weiye@gmail.com
 * @time 2019/7/23 15:13
 * @describe
 */
public class SourceManager {
    public static final String SOURCE_FULLSCREEN_ACTION = "com.hht.action.Source.FULL_SOURCE";
    private static final String FULL_SCREEN_SHOWING_FLAG = "full_screen_showing_flag";
    public static final String SOURCE_FLAG= "source_flag";

    //828
//    public static final int OPS_ORDINAL = 29;//TvOsType.EnumInputSource.E_INPUT_SOURCE_DVI.ordinal();
//    public static final int FRONT_ORDINAL = 25;// TvOsType.EnumInputSource.E_INPUT_SOURCE_HDMI3.ordinal();
//    public static final int HDMI1_ORDINAL = 31;//TvOsType.EnumInputSource.E_INPUT_SOURCE_DVI3.ordinal();
//    public static final int HDMI2_ORDINAL = 30;//TvOsType.EnumInputSource.E_INPUT_SOURCE_DVI2.ordinal();
//    public static final int HDMI3_ORDINAL = 24;//TvOsType.EnumInputSource.E_INPUT_SOURCE_HDMI2.ordinal();
//    public static final int DP_ORDINAL = 32;//TvOsType.EnumInputSource.E_INPUT_SOURCE_DVI4.ordinal();
//    public static final int VGA_ORDINAL = 0;//TvOsType.EnumInputSource.E_INPUT_SOURCE_VGA.ordinal();

    //848
//    public static final int OPS_ORDINAL = 23;//TvOsType.EnumInputSource.E_INPUT_SOURCE_HDMI.ordinal();
//    public static final int FRONT_ORDINAL = 25;//TvOsType.EnumInputSource.E_INPUT_SOURCE_HDMI3.ordinal();
//    public static final int HDMI1_ORDINAL = 30;//TvOsType.EnumInputSource.E_INPUT_SOURCE_DVI2.ordinal();
//    public static final int HDMI2_ORDINAL = 31;//TvOsType.EnumInputSource.E_INPUT_SOURCE_DVI3.ordinal();
//    public static final int HDMI3_ORDINAL = 24;//TvOsType.EnumInputSource.E_INPUT_SOURCE_HDMI2.ordinal();
//    public static final int DP_ORDINAL = 29;//TvOsType.EnumInputSource.E_INPUT_SOURCE_DVI.ordinal();
//    public static final int VGA_ORDINAL = 0;//TvOsType.EnumInputSource.E_INPUT_SOURCE_VGA.ordinal();
//    public static final int ANDROID_ORDINAL = 34;//TvOsType.EnumInputSource.E_INPUT_SO 3URCE_STORAGE.ordinal();


    public static int getSourceEnumOrdinalBySourceType(String sourceType) {
        switch (sourceType) {
            case SourceValue.OPS:
                return SourceValue.OPS_ORDINAL;
            case SourceValue.FRONT:
                return SourceValue.FRONT_ORDINAL;
            case SourceValue.HDMI1:
                return SourceValue.HDMI1_ORDINAL;
            case SourceValue.HDMI2:
                return SourceValue.HDMI2_ORDINAL;
            case SourceValue.HDMI3:
                return SourceValue.HDMI3_ORDINAL;
            case SourceValue.DP:
                return SourceValue.DP_ORDINAL;
            case SourceValue.VGA:
                return SourceValue.VGA_ORDINAL;
            case SourceValue.TYPEC:
                return SourceValue.TYPEC_ORDINAL;
            default:
                return -1;
        }

    }


    public static String getSourceTypeBySourceEnumOrdinal(int sourceOrdinal) {
        switch (sourceOrdinal) {
            case SourceValue.OPS_ORDINAL:
                return SourceValue.OPS;
            case SourceValue.FRONT_ORDINAL:
                return SourceValue.FRONT;
            case SourceValue.HDMI1_ORDINAL:
                return SourceValue.HDMI1;
            case SourceValue.HDMI2_ORDINAL:
                return SourceValue.HDMI2;
            case SourceValue.HDMI3_ORDINAL:
                return SourceValue.HDMI3;
            case SourceValue.DP_ORDINAL:
                return SourceValue.DP;
            case SourceValue.VGA_ORDINAL:
                return SourceValue.VGA;
            case SourceValue.TYPEC_ORDINAL:
                return SourceValue.TYPEC;
            default:
                return "Android";
        }
    }



    public static List<String> get828VNSourceList(){
        List<String> list = new ArrayList<>();
        list.add(SourceType.OPS);
        list.add(SourceType.HDMI_FRONT);
        list.add(SourceType.HDMI1);
        list.add(SourceType.HDMI2);
        list.add(SourceType.HDMI3);
        list.add(SourceType.DP);
        return list;
    }


    public static List<String> get828XSourceList(){
        return get828VNSourceList();
    }

    public static List<String> get828OEMSourceList(){
        List<String> list = new ArrayList<>();
        list.add(SourceType.OPS);
        list.add(SourceType.HDMI_FRONT);
        list.add(SourceType.HDMI1);
        list.add(SourceType.HDMI2);
        list.add(SourceType.VGA);
        list.add(SourceType.DP);
        return list;
    }

    public static List<String> get828RSSourceList(){
        return get828OEMSourceList();
    }


    public static List<String> get848OEMSourceList(){
        return get828OEMSourceList();
    }

    public static List<String> get848RSSourceList(){

        return get848OEMSourceList();
    }






}
