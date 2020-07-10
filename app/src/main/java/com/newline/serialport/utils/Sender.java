package com.newline.serialport.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

import com.hht.middleware.model.SourceValue;
import com.newline.serialport.chip.UniteImpl;
import com.hht.tools.device.ProcessUtils;
import com.hht.tools.log.Logger;
import com.hht.tools.log.SystemPropertiesValue;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;

public class Sender {
    private static final String ACTION_START_WHITEBOARD = "com.hht.floatbar.whiteboard";
    private static final String ACTION_START_ANNOTATION = "com.hht.floatbar.glassboard";
    private static final String ACTION_SOUND_ONLY_ON = "COM.IST.SINGLE.MODE.ON";
    private static final String ACTION_SOUND_ONLY_OFF = "COM.IST.SINGLE.MODE.OFF";
    private static final String ACTION_FREEZE = "android.intent.action.view.shot.change";
    private static final String ACTION_AUTO_VGA = "com.mstar.android.intent.action.HHT_AUTO_VGA";
    private static final String ACTION_SWITCH_D_MODE = "com.hht.set.backlightswitch";
    private static final String ACTION_PRE_SHUTDOWN = "android.intent.action.ACTION_PRESHUTDOWN";
    private static final String ACTION_SHUTDOWN = "com.android.hht.action.ACTION_SHUTDOWN";
    private static final String ACTION_SOURCE_INFO = "android.intent.action.hht.source.info";
    public static final String ACTION_HIDE_SOURCE_VIEW = "com.hht.action.Hide.SourceView";

    //倒计时关机广播
    public static final String ACTION_TIME_TO_SLEEP = "com.mstar.android.intent.action.hht.timetosleep";
    public static final String ACTION_SCREENSHOT = "com.hht.intent.action.Screenshots";

    public static final String ACTION_FULL_SOURCE  = "com.hht.action.Source.FULL_SOURCE";
    public static final String KEY_SOURCE_FLAG = "source_flag";

    private static final String LAUNCHER =  "com.hht.launcher/com.hht.launcher.LauncherViewActivity";
    private static final String IS_SWITCHING_TO_LAUNCHER = "com.hht.action.Switching.launcher";

    private static final String ACTION_SET_BLIGHT_VALUE = "com.hht.set.backlightset";
    private static final String KEY_ECO_MODE = "ecomode";
    private static final String ACTION_CHILD_LOCK_ON = "com.mstar.android.intent.action.childrenlock.on";
    private static final String ACTION_CHILD_LOCK_OFF = "com.mstar.android.intent.action.childrenlock.off";
    public static final int KEYCODE_828_TV_PRE_SHUTDOWN = 287;
    public static final int KEYCODE_848_TV_PRE_SHUTDOWN = 322;
    private static final String ACTION_OPS_NEWLINE_CHANGED = "com.hht.ops.action.newline.icon.remove";
    private static final String ACTION_LEFT_FLOATBAR_SHOW_HIDE = "com.android.settings.action.left";
    private static final String ACTION_RIGHT_FLOATBAR_SHOW_HIDE = "com.android.settings.action.right";

    public static final String ACTION_UPDATE_MIC_STATUS = "com.hht.action.GPIO.MIC.STATUS";
    public static final String KEY_UPDATE_MIC_STATUS = "isMute";

    public static void sendMicStatus(Context context, boolean isMute) {
        Intent intent = new Intent();
        intent.setAction(ACTION_UPDATE_MIC_STATUS);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra(KEY_UPDATE_MIC_STATUS, isMute);
        context.sendBroadcast(intent);
    }

    public static void sendOPSNewlineChanged(Context context) {
        Logger.i("send broadcast action = " + ACTION_OPS_NEWLINE_CHANGED);
        context.sendBroadcast(new Intent(ACTION_OPS_NEWLINE_CHANGED));
    }

    private static void sendBroadcast(Context context, String action) {
        Logger.i("send broadcast action = " + action);
        Intent intent = new Intent();
        intent.setAction(action);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.sendBroadcast(intent);
    }

    /**
     * 隐藏Launcher source view;
     * @param context
     */
    public static void sendHideSourceView(Context context) {
        String mForegroundActivity = ProcessUtils.getForegroundActivity(context);
        Logger.d("launcher = "+mForegroundActivity);
        if (LAUNCHER.equals(mForegroundActivity)) {
            context.sendBroadcast(new Intent(ACTION_HIDE_SOURCE_VIEW));
        }
    }

    public static void sendFullSource(Context context, int ordinal) {
        Intent intent = new Intent(ACTION_FULL_SOURCE);
//        intent.setComponent(new ComponentName("com.hht.hhtmiddleware","com.hht.hhtmiddleware.broadcast.FullSourceOpenBroadcastReceiver"));
        intent.putExtra(KEY_SOURCE_FLAG, ordinal);
        context.sendBroadcast(intent);
    }

    public static void sendTimeToSleep(Context context) {
        sendBroadcast(context, ACTION_TIME_TO_SLEEP);
    }

    //打开白板
    public static void sendStartWhiteBoard(Context context) {
        if (SourceValue.isMS848()) {
            Logger.d("start Taiwan Whiteboard by broadcast");
            Intent intent = new Intent(ACTION_START_WHITEBOARD);
            intent.setComponent(new ComponentName("tw.com.hitevision.whiteboard","tw.com.hitevision.whiteboard.android.receivers.WhiteboardBroadcastReceiver"));
            context.sendBroadcast(intent);
        } else {
            sendBroadcast(context, ACTION_START_WHITEBOARD);
        }
    }

    //打开批注
    public static void sendStartAnnotation(Context context) {
        if (SourceValue.isMS848()) {
            Logger.d("start Taiwan Whiteboard by broadcast");
            Intent intent = new Intent(ACTION_START_ANNOTATION);
            intent.setComponent(new ComponentName("tw.com.hitevision.whiteboard","tw.com.hitevision.whiteboard.android.receivers.WhiteboardBroadcastReceiver"));
            context.sendBroadcast(intent);
        } else {
            sendBroadcast(context, ACTION_START_ANNOTATION);
        }
    }

    public static void sendSoundOnlyOn(Context context) {
        sendBroadcast(context, ACTION_SOUND_ONLY_ON);
    }

    public static void sendSoundOnlyOff(Context context) {
        sendBroadcast(context, ACTION_SOUND_ONLY_OFF);
    }

    public static void sendFreeze(Context context) {
//        Intent intent = new Intent(ACTION_FREEZE);
//        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//        intent.setComponent(new ComponentName("com.hht.freezescreen","com.hht.freezescreen.FreezeScreenReceiver"));
//        context.sendBroadcast(intent);

        Intent service = new Intent();
        service.setComponent(new ComponentName("com.hht.freezescreen","com.hht.freezescreen.service.ProcessFreezeScreenService"));
        context.startService(service);
    }


    public static void sendScreenShot(Context context) {
        Intent intent = new Intent();
        intent.setPackage("com.hht.screenshot");
        intent.setAction("com.hht.action.Service.Screenshots");
        context.startService(intent);
    }

    public static void sendSourceVGAAuto(Context context) {
        sendBroadcast(context, ACTION_AUTO_VGA);
    }

    public static void sendSwitchDMode(Context context) {
        sendBroadcast(context, ACTION_SWITCH_D_MODE);
    }

    public static void sendPreShutDown(Context context) {


            if (SourceValue.isMS848()) {
                KeyUtils.sendSyncKey(KEYCODE_848_TV_PRE_SHUTDOWN);
            } else {
                KeyUtils.sendSyncKey(KEYCODE_828_TV_PRE_SHUTDOWN);
            }

    }

    //关机命令
    private static final String ACTION_848_SHUTDOWN = "android.intent.action.ACTION_SHUTDOWN";// 848 修改
    private static final String ACTION_828_SHUTDOWN = "com.android.hht.action.ACTION_SHUTDOWN";//828 捷达apk 记录关机时间
    private static final String PACKAGE_SHUTDOWN = "com.Mstar.tv.service";
    private static final String RECEIVER_CLASS_SHUTDOWN = "com.Mstar.tv.service.ShutdownReceiver";

    public static void sendShutDownBroadcast(Context context) {
        if (SourceValue.isMS848()) {
            Logger.i(PACKAGE_SHUTDOWN + "  :: " + RECEIVER_CLASS_SHUTDOWN);
            Intent intent = new Intent(ACTION_848_SHUTDOWN);
            intent.setComponent(new ComponentName(PACKAGE_SHUTDOWN, RECEIVER_CLASS_SHUTDOWN));
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND | Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            context.sendBroadcast(intent);
        } else {
            Logger.i(  "action = " + ACTION_828_SHUTDOWN);
            context.sendBroadcast(new Intent(ACTION_828_SHUTDOWN));
        }
    }

    public static void sendSourceInfo(Context context) {

            sendBroadcast(context, ACTION_SOURCE_INFO);

    }

    public static void sendVGAOrHDMI3(Context context) {
        String property= (String) SystemPropertiesValue.getProperty("persist.hht.VNXseria","false");
        Logger.i("sendVGAOrHDMI3 = "+property);
        if("true".equals(property)) {
            sendFullSource(context, SourceValue.HDMI3_ORDINAL);
        } else {
            sendFullSource(context, SourceValue.VGA_ORDINAL);
        }
    }

    public static void sendISChangingToLauncherToast(Context context) {
        sendBroadcast(context, IS_SWITCHING_TO_LAUNCHER);
    }

    public static void sendDisplayModeShow(Context context, int ecomode) {
        Intent intent = new Intent(ACTION_SET_BLIGHT_VALUE);
        intent.putExtra(KEY_ECO_MODE, ecomode);
        context.sendBroadcast(intent);
    }

    public static void sendChildLockOn(Context context) {
        try {
            TvManager.getInstance().setTvosCommonCommand("SetChildrenLock 1");
        } catch (TvCommonException e) {
            e.printStackTrace();
        }
        sendBroadcast(context, ACTION_CHILD_LOCK_ON);
    }

    public static void sendChildLockOff(Context context) {
        try {
            TvManager.getInstance().setTvosCommonCommand("SetChildrenLock 0");
        } catch (TvCommonException e) {
            e.printStackTrace();
        }
        sendBroadcast(context, ACTION_CHILD_LOCK_OFF);
    }

    public static void sendZ5ToolBarBroadcast(Context context) {
        Intent left = new Intent(ACTION_LEFT_FLOATBAR_SHOW_HIDE);
        context.sendBroadcast(left);

        Intent right = new Intent(ACTION_RIGHT_FLOATBAR_SHOW_HIDE);
        context.sendBroadcast(right);
    }

    public static void sendWOTSettingsMenu(Context context) {
        Logger.i("start action = com.hht.wotmenu.SETTINGS");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.hht.wotmenu","com.hht.wotmenu.receiver.MuneReceiver"));
        intent.setAction("com.hht.wotmenu.SETTINGS");
        context.sendBroadcast(intent);
    }

    public static void sendOTAUpdate(Context context) {
        Logger.i("start sendOTAUpdate ");
        context.sendBroadcast(new Intent("com.hht.action.OTA.UPDATE"));
    }

    public static void sendWOTMenuFocus(Context context) {
        Logger.i("send com.hht.action.focus  action_mode=1");
        Intent intent = new Intent();
        intent.setAction("com.hht.action.focus");
        intent.putExtra("action_mode", 1);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.sendBroadcast(intent);
    }

    public static void sendRestart() {
        try {
            TvManager.getInstance().setEnvironment("force_reboot","true");
        } catch (TvCommonException e) {
            e.printStackTrace();
        }
        TvCommonManager.getInstance().rebootSystem("reboot");
    }
}
