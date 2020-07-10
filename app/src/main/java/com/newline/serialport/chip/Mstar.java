package com.newline.serialport.chip;

import android.content.Context;
import android.provider.Settings;

import com.hht.middleware.command.CommandConstant;
import com.newline.serialport.VoiceSerialUtils;
import com.hht.tools.log.Logger;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.EnumMuteStatusType;

import static com.newline.serialport.VoiceSerialUtils.bEnableMic;

public class Mstar {

    public static boolean isMute() {
        try {
            return TvManager.getInstance().getAudioManager().isMuteEnabled(EnumMuteStatusType.MUTE_STATUS_BBYUSERAUDIOMUTE);
        } catch (Exception e) {
            Logger.e(e);
        }
        return false;
    }

    public static boolean   isUARTOnOff() throws Exception {
        return "on".equals(TvManager.getInstance().getEnvironment("UARTOnOff"));
    }

    public static int getCurrentSource(Context context) throws Exception {
        return getFullScreenOnForeground(context);
    }


    public static int getFullScreenOnForeground(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "full_screen_showing_flag", 34);
    }

    public static void setEnvironment(String content) {
        try {
            TvManager.getInstance().setEnvironment("m_serialNumbe", content);
        } catch (Exception e) {
           Logger.e(e);
        }
    }

    public static boolean isOpsPlugged() {
        try {
            return TvManager.getInstance().setTvosCommonCommand(CommandConstant.GET_OPS_PLUGGED)[0] == 1;
        } catch (Exception e) {
            Logger.e(e);
        }

        return false;
    }



    //#define  GPIO_MIC_MUTE_LED       281
    private static final int GPIO_MIC_MUTE = 281;
    //1 mute
    //0 unmute
    public static boolean isMicMute() {
        short[] shorts = new short[0];
        try {
//            shorts = TvManager.getInstance().setTvosCommonCommand("GetMicroPhoneStatus");
//            Logger.i("shorts = "+shorts);
//            String cmd = shorts[0] == 0 ? "SetMicroPhoneMute" : "SetMicroPhoneUnmute";
            boolean isMute = false;
            boolean isRed = isMute = !VoiceSerialUtils.bEnableMic;
            Logger.i("isRed = "+ isRed);
            TvManager.getInstance().setGpioDeviceStatus(GPIO_MIC_MUTE, isRed);
            return isMute;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getEnvironment() {
        try {
            return TvManager.getInstance().getEnvironment("m_serialNumbe");
        } catch (Exception e) {
            Logger.e(e);
        }

        return "";
    }
}
