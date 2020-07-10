package com.newline.serialport.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.PowerManager;
import android.provider.Settings;

import com.hht.middleware.model.SourceValue;
import com.hht.middleware.tools.SystemPropertiesUtils;
import com.newline.serialport.chip.UniteImpl;
import com.hht.tools.device.ProcessUtils;
import com.hht.tools.log.Logger;
import com.hht.tools.log.SystemPropertiesValue;
import com.mstar.android.tv.TvCommonManager;

import java.lang.reflect.Method;

public class SystemUtils {
    private static final String SOUND_ONLY_SHOWING_FLAG = "sound_only_showing_flag";
    public static final String MIC_MUTE_STATUS = "mic_mute_status_flag";//静音 = 1 非静音 = 0
    private static final String SYS_IS_UPDATE = "persist.sys.is.update";//系统是否有更新

    public static boolean isZ5() {
        return SystemPropertiesUtils.getSysPlatforms().equals("Z5");
    }

    public static boolean isYI() {
        String property = (String) com.hht.middleware.tools.log.SystemPropertiesValue.getProperty("persist.hht.Platforms2", "");
        Logger.i("property = "+property);
        return "YI_MS8386".equals(property);
    }
    /**
     * 获取系统是否有更新
     *
     * @return 返回值等于1为有更新显示图标，返回值为0隐藏图标
     */
    private static String getSysIsUpdate() {
        String temp = (String) SystemPropertiesValue.getProperty(SYS_IS_UPDATE, "0");
        Logger.i(SYS_IS_UPDATE + " = "+temp);
        return temp;
    }

    public static boolean isSysUpdate() {
        return "1".equals(getSysIsUpdate());
    }

    public static void setMicMuteStatus(Context context, boolean isMute) {
        Settings.System.putInt(context.getContentResolver(), MIC_MUTE_STATUS, isMute ? 0: 1);
    }

    public static boolean getMicMuteStatus(Context context) {
        Logger.i("Settings.System.getInt(context.getContentResolver(), MIC_MUTE_STATUS, 0) "+Settings.System.getInt(context.getContentResolver(), MIC_MUTE_STATUS, 0));
       return Settings.System.getInt(context.getContentResolver(), MIC_MUTE_STATUS, 0) == 0;
    }

    public static String getFirmwareVersion() {
        String firmwareVersion = (String) SystemPropertiesValue.getProperty("ro.build.description", "");
        Logger.i("firmwareVersion " + firmwareVersion);
        return firmwareVersion;
    }


    /**
     *
     * @param mPowerManager
     * @return get system min screen bright
     */
    private static int getSystemMinimumScreenBright(PowerManager mPowerManager){
        Class<?> c = null;

        try {
            c = Class.forName("android.os.PowerManager");

            Method m1 = c.getMethod("getMinimumScreenBrightnessSetting");

            return  (int)(m1.invoke(mPowerManager));
        }  catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * @param mPowerManager
     * @return get system max screen bright
     */
    private static int getSystemMaxScreenBright(PowerManager mPowerManager){
        Class<?> c = null;

        try {
            c = Class.forName("android.os.PowerManager");

            Method m1 = c.getMethod("getMaximumScreenBrightnessSetting");

            return  (int)(m1.invoke(mPowerManager));
        }  catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }




    public static void setVolume(Context context, int volume) {
        Logger.i("volume = "+volume);
        if (volume > 100) {
            volume = 100;
        } else if (volume < 0) {
            volume = 0;
        }
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI);
    }

    public static void showAudioUI(Context context) {
//        AudioManager audioManager = (AudioManager) context.getSystemService(Service.AUDIO_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            audioManager.isStreamMute(AudioManager.STREAM_MUSIC);
//        }
    }

    public static int getVolume(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Logger.i("volume = "+volume);
        return volume;
    }

    public static boolean is848Mute(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0;
    }

    public static boolean isMute(Context context) {
        if (SourceValue.isMS848() ) {
            return is848Mute(context);
        }

        return UniteImpl.isMute();
    }

    public static void setMute(Context context, boolean mute) {
        Class<?> c = null;
        try {
            c = Class.forName("android.media.AudioManager");

            Method m1 = c.getMethod("setMasterMute", boolean.class);

            m1.invoke(c, mute);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isChildLockOn(Context context) {
        return (boolean) SystemPropertiesValue.getProperty("persist.sys.hht.lock", false);
    }

    public static boolean isWhiteboardOnForeground(Context context) {
        String mForegroundActivity = ProcessUtils.getForegroundActivity(context);
        Logger.i("mForegroundActivity = "+mForegroundActivity);
        return mForegroundActivity.contains("com.paike.zjc");
    }

    public static boolean isTWWhiteboadOnForeground(Context context) {
        String mForegroundActivity = ProcessUtils.getForegroundActivity(context);
        Logger.i("mForegroundActivity = "+mForegroundActivity);
        return mForegroundActivity.contains("tw.com.hitevision.whiteboard");
    }

    public static boolean isTWWhiteboadNewOnForeground(Context context) {
        String mForegroundActivity = ProcessUtils.getForegroundActivity(context);
        Logger.i("mForegroundActivity = "+mForegroundActivity);
        return mForegroundActivity.contains("tw.com.newline.whiteboard");
    }

    public static boolean isFrozenScreen(Context context) {
        String isFrozen = Settings.System.getString(context.getContentResolver(),"isFrozen");
        Logger.i("isFrozen = "+isFrozen);
        return"true".equals(isFrozen);
    }

    public static boolean isPkgInstalled(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        android.content.pm.ApplicationInfo info = null;
        try {
            info = context.getPackageManager().getApplicationInfo(packageName, 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void setHDMIEDID(int inputSource, int hdmiEdidVersion) {
        TvCommonManager.getInstance().setHdmiEdidVersionBySource(inputSource, hdmiEdidVersion);
    }

    public static int getHDMIEDID(int inputSource) {
       return TvCommonManager.getInstance().getHdmiEdidVersionBySource(inputSource);
    }


    private static final String KEY_OPS_STATUS = "persist.hht.OPS_status";
    private static final String KEY_OPS_ON = "OPS_ON";
    private static final String KEY_OPS_OFF = "OPS_OFF";
    public static boolean isFakeShutDown() {
        String opsStatus = (String) SystemPropertiesValue.getProperty(KEY_OPS_STATUS,KEY_OPS_OFF);
        Logger.i("opsStatus = "+opsStatus);
        return opsStatus.equals(KEY_OPS_ON);
    }

    public static void setFakeShutDown(boolean isFakeShutDown) {
        SystemPropertiesValue.setProperty(KEY_OPS_STATUS, isFakeShutDown ? KEY_OPS_ON : KEY_OPS_OFF);
    }


    public static void resetAll(Context context, boolean saveLan) {
        Logger.i("save Lan "+ saveLan);
        if (saveLan) {
        }
        setFactory(context);
    }

    public static final String ACTION_FACTORY_RESET = "android.intent.action.FACTORY_RESET";
    public static final String EXTRA_REASON = "android.intent.extra.REASON";
    public static final String EXTRA_WIPE_EXTERNAL_STORAGE = "android.intent.extra.WIPE_EXTERNAL_STORAGE";
    private static void setFactory(Context context){
        Intent intent = new Intent(ACTION_FACTORY_RESET);
        intent.setPackage("android");
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.putExtra(EXTRA_REASON, "MasterClearConfirm");
        intent.putExtra(EXTRA_WIPE_EXTERNAL_STORAGE, true);
        context.sendBroadcast(intent);
    }

    private static final String  STANDBY_STATUS_FLAG = "hht_standby";
    public static boolean isStandbyOn(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), STANDBY_STATUS_FLAG,0) == 1;//打开假待机
    }

    public static boolean isYISleep(Context context) {
        String foregroundActivity = ProcessUtils.getForegroundActivity(context);
        Logger.d("foregroundActivity = "+ foregroundActivity);
        return isYI() && "com.hht.standby/com.hht.standby.MainActivity".equals(foregroundActivity);
    }
}
