package com.newline.serialport.chip;

import android.content.Context;
import android.provider.Settings;

import com.hht.middleware.command.CommandConstant;
import com.hht.tools.log.Logger;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.vo.EnumMuteStatusType;


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


    public static String getEnvironment() {
        try {
            return TvManager.getInstance().getEnvironment("m_serialNumbe");
        } catch (Exception e) {
            Logger.e(e);
        }

        return "";
    }
}
