package com.newline.serialport.setting;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.DisplayMetrics;

import com.hht.middleware.OnSourceSignalListener;
import com.hht.middleware.entity.EntitySourceInformation;
import com.hht.middleware.model.HHTTime;
import com.hht.sdk.audio.HHTAudioManager;
import com.hht.sdk.client.APIManager;
import com.hht.sdk.display.HHTDisplayManager;
import com.hht.sdk.network.HHTNetworkManager;
import com.hht.sdk.source.HHTSourceManager;
import com.hht.sdk.system.HHTSystemManager;
import com.newline.serialport.setting.i.FullStatusListener;
import com.newline.serialport.setting.i.HHTDeviceStatusListener;
import com.newline.serialport.setting.i.StandardDeviceStatusListener;
import com.newline.serialport.setting.tools.source.SourceManager;
import com.newline.serialport.setting.utils.HHTConstant;
import com.newline.serialport.setting.utils.SystemSettingsUtils;


import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;


/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/4/10 10:33
 * @describe
 */
abstract  class HHTDeviceDelegateImplBase extends HHTDeviceDelegate {

    Context mContext;

    StandardDeviceStatusListener mStandardDeviceStatusListener;
    HHTDeviceStatusListener mHHTDeviceStatusListener;

    AudioManager mAudiomanager;

    HHTAudioManager mHHTAudioManager;
    HHTDisplayManager mHHTDisplayManager;
    HHTNetworkManager mHHTNetworkManager;
    HHTSourceManager mHHTSourceManager;
    HHTSystemManager mHHTSystemManager;


    private DeviceStatusManager deviceStatusManager;

    HHTDeviceDelegateImplBase(Context mContext) {
        this.mContext = mContext;
        mAudiomanager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    }

    HHTDeviceDelegateImplBase(Context context, FullStatusListener fullStatusListener){
        this(context);
        setOnFullStatusListener(fullStatusListener);

    }

    @Override
    void setOnStandardDeviceStatusListener(StandardDeviceStatusListener standardDeviceStatusListener){
        mStandardDeviceStatusListener = standardDeviceStatusListener;
        initDeviceStatusManager();
        deviceStatusManager.setStandardDeviceStatusListener(mStandardDeviceStatusListener);
    }

    @Override
    void setOnHHTDeviceStatusListener(HHTDeviceStatusListener hhtDeviceStatusListener) {
        mHHTDeviceStatusListener = hhtDeviceStatusListener;
        initDeviceStatusManager();
        deviceStatusManager.setHhtDeviceStatusListener(mHHTDeviceStatusListener);
    }

    @Override
    void setOnFullStatusListener(FullStatusListener fullStatusListener) {
        setOnStandardDeviceStatusListener(fullStatusListener);
        setOnHHTDeviceStatusListener(fullStatusListener);
    }

    private void initDeviceStatusManager(){
        if(deviceStatusManager == null){
            deviceStatusManager = new DeviceStatusManager(this,mContext);
        }
    }

    @Override
    void setVolume(int targetVolume) {
        this.setVolume(AudioManager.STREAM_MUSIC,targetVolume);
    }

    @Override
    void setVolume(int streamType, int targetVolume) {
        this.setVolume(streamType,targetVolume,AudioManager.FLAG_SHOW_UI);
    }

    @Override
    void setVolume(int streamType, int targetVoume, int flag) {
        mAudiomanager.setStreamVolume(streamType,targetVoume,flag);
    }

    @Override
    int getVolume() {
        return this.getVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    int getVolume(int streamType) {
        return mAudiomanager.getStreamVolume(streamType);
    }

    @Override
    int getMaxVolume(int streamType) {
        return mAudiomanager.getStreamMaxVolume(streamType);
    }

    @Override
    void setVolumeUp(int streamType, boolean showUi) {
        mAudiomanager.adjustStreamVolume(streamType,AudioManager.ADJUST_RAISE,showUi?AudioManager.FLAG_SHOW_UI:0);
    }

    @Override
    void setVolumeDown(int streamType, boolean showUi) {
        mAudiomanager.adjustStreamVolume(streamType,AudioManager.ADJUST_LOWER,showUi?AudioManager.FLAG_SHOW_UI:0);
    }

    @Override
    void setVolumeUp(boolean showUi) {
        setVolumeUp(AudioManager.STREAM_MUSIC,showUi);
    }

    @Override
    void setVolumeDown(boolean showUi) {
        setVolumeDown(AudioManager.STREAM_MUSIC,showUi);
    }

    /**
     * @param mPowerManager
     * @return get system min screen bright
     */
    @Override
    int getSystemMinimumScreenBright(PowerManager mPowerManager) {
        Class<?> c = null;

        try {
            c = Class.forName("android.os.PowerManager");

            Method m1 = c.getMethod("getMinimumScreenBrightnessSetting");

            return (int) (m1.invoke(mPowerManager));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * @param mPowerManager
     * @return get system max screen bright
     */
    @Override
    int getSystemMaximumScreenBright(PowerManager mPowerManager) {
        Class<?> c = null;

        try {
            c = Class.forName("android.os.PowerManager");

            Method m1 = c.getMethod("getMaximumScreenBrightnessSetting");

            return (int) (m1.invoke(mPowerManager));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * @param value screen bright value
     */
    @Override
    void setBright(int value) {
        Settings.System.putInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, value);
    }

    /**
     * @return screen bright
     */
    @Override
    int getBright() {
        try {
            return Settings.System.getInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return 100;
    }


    @Override
    String getScreenResolution() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();

        return displayMetrics.widthPixels+"*"+displayMetrics.heightPixels;
    }

    @Override
    int getScreenWidthResolution() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    @Override
    int getScreenHeightResolution() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    @Override
    Locale getLocale() {
        return mContext.getResources().getConfiguration().locale;
    }

    /**
     *
     * @return
     * like: fr
     */
    @Override
    String getLanguage() {

        return getLocale().getLanguage();
    }

    /**
     *
     * @return
     * like: français
     */
    @Override
    String getDisplayLanguage() {
        return getLocale().getDisplayLanguage();
    }

    /**
     *
     * @return
     * like: FR
     */
    @Override
    String getCountry() {
        return getLocale().getCountry();
    }

    /**
     *
     * @return
     * like: France
     */
    @Override
    String getDisplayCountry() {
        return getLocale().getDisplayCountry();
    }

    /**
     *
     * @return
     * like: français (France)
     */
    @Override
    String getDisplayName() {
        return getLocale().getDisplayName();
    }

    /**
     *
     * @return
     * like: fr-FR
     */
    @Override
    String getLanguageTag() {
        return getLocale().toLanguageTag();
    }

    @Override
    void setSystemLanguage(Locale locale) {
        try {
            Object objIActMag, objActMagNative;
            Class clzIActMag = Class.forName("android.app.IActivityManager");
            Class clzActMagNative = Class.forName("android.app.ActivityManagerNative");
            Method mtdActMagNative$getDefault = clzActMagNative.getDeclaredMethod("getDefault");
            // IActivityManager iActMag = ActivityManagerNative.getDefault();
            objIActMag = mtdActMagNative$getDefault.invoke(clzActMagNative);
            // Configuration config = iActMag.getConfiguration();
            Method mtdIActMag$getConfiguration = clzIActMag.getDeclaredMethod("getConfiguration");
            Configuration config = (Configuration) mtdIActMag$getConfiguration.invoke(objIActMag);
            config.locale = locale;
            // iActMag.updateConfiguration(config);
            // 此处需要声明权限:android.permission.CHANGE_CONFIGURATION
            // 会重新调用 onCreate();
            Class[] clzParams = { Configuration.class };
            Method mtdIActMag$updateConfiguration = clzIActMag.getDeclaredMethod(
                    "updateConfiguration", clzParams);
            mtdIActMag$updateConfiguration.invoke(objIActMag, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseRes() {
        if(deviceStatusManager!=null){
            deviceStatusManager.releaseRes();
            if(mStandardDeviceStatusListener != null){
                mStandardDeviceStatusListener = null;
            }
            if(mHHTDeviceStatusListener != null){
                mHHTDeviceStatusListener =null;
            }
        }
        mContext = null;

    }

    private class BrightObserver extends ContentObserver {

        public BrightObserver(ContentResolver handler) {
            super(new Handler(Looper.getMainLooper()));
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            if(mStandardDeviceStatusListener != null){
                mStandardDeviceStatusListener.onBrightnessChange(getBright());
            }

        }
    }



    //hht function
    @Override
    void startFloatBar() {

    }

    @Override
    void stopFloatBar() {

    }

    @Override
    void showFloatBar() {

    }

    @Override
    void hideFloatBar() {

    }

    @Override
    void openSoundOnly() {

    }

    @Override
    void closeSoundOnly() {

    }

    @Override
    void openFreezeScreen() {
        if(getFreezeStatus()){
           return;
        }
        doFreeze();
    }

    @Override
    void closeFreezeScreen() {
        if(getFreezeStatus()){
            doFreeze();
        }
    }

    @Override
    boolean getFreezeStatus() {
        return SystemSettingsUtils.isFrozenScreen(mContext);
    }

    @Override
    void doFreeze() {
        Intent intent = new Intent(HHTConstant.ACTION_FREEZE);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setComponent(new ComponentName(HHTConstant.FREEZE_PACKAGE, HHTConstant.FREEZE_RECEIVER_CLASS));
        mContext.sendBroadcast(intent);
    }

    @Override
    void openBlueLightFilter() {

    }

    @Override
    void closeBlueLightFilter() {

    }

    @Override
    void updatedBlueLightFilterLevel(int level) {

    }

    @Override
    void openChildLock() {

    }

    @Override
    void closeChildLock() {

    }

    @Override
    void openWhiteBoard() {

    }

    @Override
    void openAnnotation() {

    }

    @Override
    void closeAnnotation() {

    }

    @Override
    void killWhiteBoard() {

    }

    @Override
    Bitmap screenShot() {
        return null;
    }

    @Override
    void clearWhiteBoardScreenShotImg() {

    }

    @Override
    void openFullSource(int ordinal) {

    }

    @Override
    void sleep() {

    }

    @Override
    void wakeup() {

    }

    @Override
    boolean getMuteStatus() {
        return mAudiomanager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0;
    }

    @Override
    void setMute(boolean mute) {

    }

    @Override
    boolean getBeepSoundEnable() {
        return false;
    }

    @Override
    void setBeepSoundEnable(boolean enable) {

    }

    @Override
    void setNotificationSoundEnable(boolean enable) {

    }

    @Override
    void setTouchSoundEnable(boolean enable) {

    }


    //==========midware=======


    @Override
    void bindHHTMiddleware(boolean initAllMiddleService) {
        APIManager.connectionService(mContext);
        if(initAllMiddleService){
            initHHTMiddlerwareService();
        }
    }

    @Override
    void unbindHHTMiddleware() {
        APIManager.disconnectService(mContext);
    }

    @Override
    void initHHTMiddlerwareService() {
        initHHTAudioManager();
        initHHTDisplayManager();
        initHHTNetworkManager();
        initHHTSourceManager();
        initHHTSystemManager();
    }

    @Override
    void initHHTSourceManager() {
        mHHTSourceManager = new HHTSourceManager(mContext);
    }

    @Override
    void initHHTSystemManager() {
        mHHTSystemManager = new HHTSystemManager(mContext);
    }

    @Override
    void initHHTAudioManager() {
        mHHTAudioManager = new HHTAudioManager(mContext);
    }

    @Override
    void initHHTDisplayManager() {
        mHHTDisplayManager = new HHTDisplayManager(mContext);
    }

    @Override
    void initHHTNetworkManager() {
        mHHTNetworkManager = new HHTNetworkManager(mContext);
    }

    @Override
    int getTopSourceOrdinal() {
        return 0;
    }

    @Override
    String getTopSourceType() {
        return SourceManager.getSourceTypeBySourceEnumOrdinal(getTopSourceOrdinal());

    }

    @Override
    boolean hasSourceSignal(int ordinal) {
        return false;
    }

    @Override
    void openSource(String sourceType) {

    }

    @Override
    void openSource(int sourceOrdinal) {

    }

    @Override
    List<String> getSourceTypeList() {
        return null;
    }

    @Override
    boolean changeTPSource(int ordinal, int pid) {
        return false;
    }

    @Override
    boolean changeUSBSource(int ordinal) {
        return false;
    }

    @Override
    EntitySourceInformation getSourceInformation(int ordinal) {
        return null;
    }

    @Override
    void registerSourceSignalListener(OnSourceSignalListener listener) {

    }

    @Override
    void unregisterSourceSignalListener(OnSourceSignalListener listener) {

    }

    @Override
    void setFullScreenSource() {

    }

    @Override
    void setPreviewSource(int left, int top, int right, int bottom) {

    }

    @Override
    void setSourceEnableTouch(int pid) {

    }

    @Override
    void setSourceDisableTouch(int pid) {

    }

    @Override
    boolean setSourceMute(boolean mute) {
        return false;
    }

    @Override
    boolean getSourceMuteState() {
        return false;
    }

    @Override
    boolean adjustVGAImage() {
        return false;
    }

    @Override
    int[] getPanelSize() {
        return new int[0];
    }

    @Override
    void applyEyeMode() {

    }

    @Override
    boolean hasAnySourceConnection() {
        return false;
    }

    @Override
    void startOpsApp(String appName) {

    }

    @Override
    int[] setTvosCommonCommand(String command) {
        return new int[0];
    }

    @Override
    boolean setTvosInterfaceCommand(String command) {
        return false;
    }

    @Override
    String getEnvironment(String command) {
        return null;
    }

    @Override
    void setEnvironment(String command1, String command2) {

    }

    @Override
    HHTTime getAutoPowerOnTime() {
        return null;
    }

    @Override
    HHTTime getAutoPowerOffTime() {
        return null;
    }

    @Override
    void setAutoPowerOnTime(HHTTime time, boolean[] weekdayArr) {

    }

    @Override
    void setAutoPowerOffTime(HHTTime time, boolean[] weekdayArr) {

    }

    @Override
    void setAutoPowerOnTimeEnable(boolean enable) {

    }

    @Override
    void setAutoPowerOffTimeEnable(boolean enable) {

    }

    @Override
    boolean getAutoPowerOnTimeEnable() {
        return false;
    }

    @Override
    boolean getAutoPowerOffTimeEnable() {
        return false;
    }

    @Override
    void closeGpioDeviceStatus() {

    }

    @Override
    boolean isTopCamera() {
        return false;
    }

    @Override
    void setTopCamera() {

    }

    @Override
    void setBottomCamera() {

    }

    @Override
    void setNetWakeUpStatus(boolean isWakeUp) {

    }

    @Override
    boolean isNetWakeUp() {
        return false;
    }

    @Override
    void setMicOnOff(boolean open) {

    }

    @Override
    boolean isMicOff() {
        return false;
    }

    @Override
    void setMicMute(boolean mute) {
        mAudiomanager.setMicrophoneMute(mute);
    }

    @Override
    boolean isMicMute() {
        return mAudiomanager.isMicrophoneMute();
    }

    @Override
    void updateSystemPrepare() {

    }

    @Override
    String getPowerOSource() {
        return null;
    }

    @Override
    boolean getCecAutoPowerOn() {
        return false;
    }

    @Override
    boolean getCecAutoPowerOff() {
        return false;
    }

    @Override
    void setCecAutoPowerOn(boolean on) {

    }

    @Override
    void setCecAutoPowerOff(boolean off) {

    }

    @Override
    int getCECAutoSwitchSource() {
        return 0;
    }

    @Override
    void setCECAutoSwitchSource(int value) {

    }

    @Override
    int getEyeWarmStatus() {
        return 0;
    }

    @Override
    void setEyeWarm(int value) {

    }

    @Override
    int getEyeWriteStatus() {
        return 0;
    }

    @Override
    void setEyeWrite(int value) {

    }

    @Override
    int getOPSUsbStatus() {
        return 0;
    }

    @Override
    int getNLStatus() {
        return 0;
    }

    @Override
    int getOpsPlugged() {
        return 0;
    }

    @Override
    void setMuteOn() {

    }

    @Override
    void setMuteOff() {

    }

    @Override
    void setHDMIOutType(String param) {

    }

    @Override
    int getAmbientLightValue() {
        return 0;
    }

    @Override
    int getAmbientTempValue() {
        return 0;
    }

    @Override
    int getAmbientCO2Value() {
        return 0;
    }





}
