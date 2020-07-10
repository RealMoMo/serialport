package com.newline.serialport.model;

import android.content.Context;

import com.hht.middleware.model.SourceValue;
import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.utils.SystemUtils;
import com.hht.tools.log.Logger;
import com.newline.serialport.chip.UniteImpl;
import com.newline.serialport.utils.SystemUtils;

public class SerialPortCurrentSignalSource extends SerialPortModel {
    public static final int LANGO_HDMI1 = 0;
    public static final int LANGO_HDMI2 = 1;
    public static final int LANGO_HDMI3 = 2;
    public static final int LANGO_HDMI4 = 3;
    public static final int LANGO_OPS = 4;

    public static final String QUERYING_CODE = "7F 08 99 A2 B3 C4 02 FF 01 50 CF";
    //HDMI rear 1 = 1F
    //HDMI Rear 2 = 1E
    //HDMI Rear 3 = 18
    //HDMI Rear 4 = 17
    //OPS = 16


    //● Smart system = 30
    //● PC = 17
    //● DP = 20
    //● HDMI rear 1 = 1F
    //● HDMI Rear 2 = 1E
    //● VGA = 00
    //● HDMI Front = 19
    //● Type c = 18
    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 01 50 XX CF";

    public static String sourceHexStr = "";

    @Override
    public String getReturnCode() {
        return returnCode.replace("XX", sourceHexStr);
    }

    @Override
    public void action(Context context) {
        sourceHexStr = getCurrentSignal(context);
    }

    public static String getCurrentSignal(Context context) {
        String sourceHexStr = "30";
        try {
            int currentSource = UniteImpl.getCurrentSource(context);
            if (currentSource == -1) {
                return sourceHexStr;
            }
            Logger.i("currentSource = "+currentSource);
            int middleSource = SourceValue.getMiddleOrdinal(currentSource);
            Logger.i("middleSource  = "+middleSource);
            switch (middleSource) {
                case LANGO_HDMI1:sourceHexStr = "1F";break;
                case LANGO_HDMI2:sourceHexStr = "1E";break;
                case LANGO_HDMI3:sourceHexStr = "18";break;
                case LANGO_HDMI4:sourceHexStr = "17";break;
                case LANGO_OPS:sourceHexStr = "16";break;


                case SourceValue.OPS_ORDINAL:sourceHexStr = "17";break;
                case SourceValue.DP_ORDINAL:sourceHexStr = "20";break;

                case SourceValue.HDMI1_ORDINAL:sourceHexStr = "1F";break;
                case SourceValue.HDMI2_ORDINAL:sourceHexStr = "1E";break;

                case SourceValue.VGA_ORDINAL:sourceHexStr = "00";break;
                case SourceValue.FRONT_ORDINAL:sourceHexStr = "19";break;
                case SourceValue.TYPEC_ORDINAL:
                    if (SystemUtils.isZ5()) {
                        sourceHexStr = "18";
                    } else {
                        sourceHexStr = "22";
                    }

                    break;
                default:sourceHexStr = "30";break;
            }
        } catch (Exception e) {
            Logger.e(e);
        }

        return sourceHexStr;
    }

}
