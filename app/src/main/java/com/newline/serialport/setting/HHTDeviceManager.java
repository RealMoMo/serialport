package com.newline.serialport.setting;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;

import com.hht.middleware.OnSourceSignalListener;
import com.hht.middleware.entity.EntitySourceInformation;
import com.hht.middleware.model.HHTTime;
import com.newline.serialport.setting.i.FullStatusListener;
import com.newline.serialport.setting.i.HHTDeviceStatusListener;
import com.newline.serialport.setting.i.StandardDeviceStatusListener;


import java.util.List;
import java.util.Locale;


/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/4/10 10:03
 * @describe
 */
public class HHTDeviceManager {


    private HHTDeviceDelegate mHHTDeviceDelegate;


    public HHTDeviceManager(@NonNull Context context) {
        mHHTDeviceDelegate = HHTDeviceDelegate.create(context);
    }

    public HHTDeviceManager(@NonNull Context context, @NonNull FullStatusListener listener) {
        mHHTDeviceDelegate = HHTDeviceDelegate.create(context, listener);
    }

    //=============device listener ====================
    public void setOnStandardDeviceStatusListener(@NonNull StandardDeviceStatusListener listener) {
        mHHTDeviceDelegate.setOnStandardDeviceStatusListener(listener);
    }

    public void setOnHHTDeviceStatusListener(@NonNull HHTDeviceStatusListener listener){
        mHHTDeviceDelegate.setOnHHTDeviceStatusListener(listener);
    }

    public void setOnFullStatusListener(@NonNull FullStatusListener listener){
        mHHTDeviceDelegate.setOnFullStatusListener(listener);
    }


    //=========volume setting =============
    public void setVolume(int type, int targetVolume, int flag) {
        mHHTDeviceDelegate.setVolume(type, targetVolume, flag);
    }

    public void setVolume(int type, int targetVolume) {
        mHHTDeviceDelegate.setVolume(type, targetVolume);
    }

    public void setVolume(int targetVolume) {
        mHHTDeviceDelegate.setVolume(targetVolume);
    }

    public int getVolume() {
        return mHHTDeviceDelegate.getVolume();
    }

    public int getVolume(int type) {
        return mHHTDeviceDelegate.getVolume(type);
    }

    public int getMaxVolume(int type) {
        return mHHTDeviceDelegate.getMaxVolume(type);
    }

    public void setVolumeUp(int streamType, boolean showUi) {
        mHHTDeviceDelegate.setVolumeUp(streamType, showUi);
    }

    public void setVolumeDown(int streamType, boolean showUi) {
        mHHTDeviceDelegate.setVolumeDown(streamType, showUi);
    }

    public void setVolumeUp(boolean showUi) {
        mHHTDeviceDelegate.setVolumeUp(showUi);
    }

    public void setVolumeDown(boolean showUi) {
        mHHTDeviceDelegate.setVolumeDown(showUi);
    }


    public boolean getMuteStatus() {

        return mHHTDeviceDelegate.getMuteStatus();
    }

    public void setMute(boolean mute) {
        mHHTDeviceDelegate.setMute(mute);
    }


    public boolean getBeepSoundEnable() {
        return mHHTDeviceDelegate.getBeepSoundEnable();
    }


    public void setBeepSoundEnable(boolean enable) {
        mHHTDeviceDelegate.setBeepSoundEnable(enable);
    }

    public void setNotificationSoundEnable(boolean enable) {
        mHHTDeviceDelegate.setNotificationSoundEnable(enable);
    }

    public void setTouchSoundEnable(boolean enable) {
        mHHTDeviceDelegate.setTouchSoundEnable(enable);
    }

    //========bright setting ===================
    public int getBright() {
        return mHHTDeviceDelegate.getBright();
    }

    @RequiresPermission(Manifest.permission.WRITE_SETTINGS)
    public void setBright(int value) {
        mHHTDeviceDelegate.setBright(value);
    }


    public int getSystemMaximumScreenBright(PowerManager powerManager) {
        return mHHTDeviceDelegate.getSystemMaximumScreenBright(powerManager);
    }

    public int getSystemMinimumScreenBright(PowerManager powerManager) {
        return mHHTDeviceDelegate.getSystemMinimumScreenBright(powerManager);
    }

    //=======screen resolution info ============

    /**
     * @return screen resolution.
     * format: 1920*1080
     */
    public String getScreenResolution() {
        return mHHTDeviceDelegate.getScreenResolution();
    }

    public int getScreenWidthResolution() {
        return mHHTDeviceDelegate.getScreenWidthResolution();
    }


    public int getScreenHeightResolution() {
        return mHHTDeviceDelegate.getScreenHeightResolution();
    }

    //=======Locale info ===============
    public Locale getLocale() {
        return mHHTDeviceDelegate.getLocale();
    }

    public String getLanguage() {
        return mHHTDeviceDelegate.getLanguage();
    }

    public String getDisplayLanguage() {
        return mHHTDeviceDelegate.getDisplayLanguage();
    }

    public String getCountry() {
        return mHHTDeviceDelegate.getCountry();
    }

    public String getDisplayCountry() {
        return mHHTDeviceDelegate.getDisplayCountry();
    }

    public String getDisplayName() {
        return mHHTDeviceDelegate.getDisplayName();
    }

    public String getLanguageTag() {
        return mHHTDeviceDelegate.getLanguageTag();
    }

    //==========Android other function========
    @RequiresPermission(Manifest.permission.CHANGE_CONFIGURATION)
    public void setSystemLanguage(Locale locale){
        mHHTDeviceDelegate.setSystemLanguage(locale);
    }



    //======hht function============
    public void startFloatBar() {
        mHHTDeviceDelegate.startFloatBar();
    }

    public void stopFloatBar() {
        mHHTDeviceDelegate.stopFloatBar();
    }

    public void showFloatBar() {
        mHHTDeviceDelegate.showFloatBar();
    }

    public void hideFloatBar() {
        mHHTDeviceDelegate.hideFloatBar();
    }

    public void openSoundOnly() {
        mHHTDeviceDelegate.openSoundOnly();
    }

    public void closeSoundOnly() {
        mHHTDeviceDelegate.closeSoundOnly();
    }

    //冻屏
    public void openFreezeScreen() {
        mHHTDeviceDelegate.openFreezeScreen();
    }
    //解冻屏
    public void closeFreezeScreen() {
        mHHTDeviceDelegate.closeFreezeScreen();
    }

    //获取冻屏状态
    public boolean getFreezeStatus(){
        return  mHHTDeviceDelegate.getFreezeStatus();
    }

    //执行冻屏或解冻屏
    public void doFreeze(){
        mHHTDeviceDelegate.doFreeze();
    }

    public void openBlueLightFilter() {
        mHHTDeviceDelegate.openBlueLightFilter();
    }

    public void closeBlueLightFilter() {
        mHHTDeviceDelegate.closeBlueLightFilter();
    }

    public void updatedBlueLightFilterLevel(int level) {
        mHHTDeviceDelegate.updatedBlueLightFilterLevel(level);
    }

    public void openChildLock() {
        mHHTDeviceDelegate.openChildLock();
    }

    public void closeChildLock() {
        mHHTDeviceDelegate.closeChildLock();
    }

    public void openWhiteBoard() {
        mHHTDeviceDelegate.openWhiteBoard();
    }

    public void openAnnotation() {
        mHHTDeviceDelegate.openAnnotation();
    }

    public void closeAnnotation() {
        mHHTDeviceDelegate.closeAnnotation();
    }

    public void killWhiteBoard() {
        mHHTDeviceDelegate.killWhiteBoard();
    }

    public Bitmap screenShot() {

        return mHHTDeviceDelegate.screenShot();
    }

    //just oem rs had this function
    public void clearWhiteBoardScreenShotImg() {
        mHHTDeviceDelegate.clearWhiteBoardScreenShotImg();
    }

    public void openFullSource(int ordinal) {
        mHHTDeviceDelegate.openFullSource(ordinal);
    }

    public void sleep() {
        mHHTDeviceDelegate.sleep();
    }

    public void wakeup() {
        mHHTDeviceDelegate.wakeup();
    }


    public void releaseRes() {
        mHHTDeviceDelegate.releaseRes();
    }



    //===============midware================
    public void bindHHTMiddleware(boolean initAllMiddleService) {
        mHHTDeviceDelegate.bindHHTMiddleware(initAllMiddleService);
    }


    public void unbindHHTMiddleware() {
        mHHTDeviceDelegate.unbindHHTMiddleware();
    }

    public void initHHTMiddlerwareService() {
        mHHTDeviceDelegate.initHHTMiddlerwareService();
    }


    public void initHHTSourceManager() {
        mHHTDeviceDelegate.initHHTSourceManager();
    }


    public void initHHTSystemManager() {
        mHHTDeviceDelegate.initHHTSystemManager();
    }


    public void initHHTAudioManager() {
        mHHTDeviceDelegate.initHHTAudioManager();
    }


    public void initHHTDisplayManager() {
       mHHTDeviceDelegate.initHHTDisplayManager();
    }


    public void initHHTNetworkManager() {
        mHHTDeviceDelegate.initHHTNetworkManager();
    }



    //获取顶端信号源ordinal
    public int getTopSourceOrdinal() {
        return mHHTDeviceDelegate.getTopSourceOrdinal();
    }

    //获取顶端信号源Name
    public String getTopSourceType() {
        return mHHTDeviceDelegate.getTopSourceType();
    }

    //判断信号源是否有信号
    public boolean hasSourceSignal(int ordinal) {
        return mHHTDeviceDelegate.hasSourceSignal(ordinal);
    }

    public void openSource(String sourceType){
        mHHTDeviceDelegate.openSource(sourceType);
    }

    public void openSource(int sourceOrdinal){
        mHHTDeviceDelegate.openSource(sourceOrdinal);
    }

    public List<String> getSourceTypeList(){
        return mHHTDeviceDelegate.getSourceTypeList();
    }

    //切换TOUCH POINT
    public boolean changeTPSource(int ordinal, int pid) {
        return mHHTDeviceDelegate.changeTPSource(ordinal, pid);
    }



    //切换USB Source
    public boolean changeUSBSource(int ordinal) {
        return mHHTDeviceDelegate.changeUSBSource(ordinal);
    }



    //信号源信息
    public EntitySourceInformation getSourceInformation(int ordinal) {
        return mHHTDeviceDelegate.getSourceInformation(ordinal);
    }



    //监听信号源完成显示
    public void registerSourceSignalListener(OnSourceSignalListener listener) {
        mHHTDeviceDelegate.registerSourceSignalListener(listener);
    }



    //停止监听信号源完成显示
    public void unregisterSourceSignalListener(OnSourceSignalListener listener) {
        mHHTDeviceDelegate.unregisterSourceSignalListener(listener);
    }



    //设置全屏信号源
    public void setFullScreenSource() {
        mHHTDeviceDelegate.setFullScreenSource();
    }



    //设置预览信号源
    public void setPreviewSource(int left, int top, int right, int bottom) {
        mHHTDeviceDelegate.setPreviewSource(left, top, right, bottom);
    }



    //开启全区域触控
    public void setSourceEnableTouch(int pid) {
        mHHTDeviceDelegate.setSourceEnableTouch(pid);
    }



    //关闭全区域触控
    public void setSourceDisableTouch(int pid) {
        mHHTDeviceDelegate.setSourceDisableTouch(pid);
    }



    //设置静音
    public boolean setSourceMute(boolean mute) {
        return mHHTDeviceDelegate.setSourceMute(mute);
    }



    //获取静音
    public boolean getSourceMuteState() {
        return mHHTDeviceDelegate.getSourceMuteState();
    }



    //调整 VGA画面
    public boolean adjustVGAImage() {
        return mHHTDeviceDelegate.adjustVGAImage();
    }



    //获取信号源宽高
    public int[] getPanelSize() {
        return mHHTDeviceDelegate.getPanelSize();
    }



    //信号源色温、色调
    public void applyEyeMode() {
        mHHTDeviceDelegate.applyEyeMode();
    }



    //RS判断存在至少一个信号源连接上
    public boolean hasAnySourceConnection() {
        return mHHTDeviceDelegate.hasAnySourceConnection();
    }



    //打开OPS 应用
    public void startOpsApp(String appName) {
        mHHTDeviceDelegate.startOpsApp(appName);
    }



    //设置常用命令
    public int[] setTvosCommonCommand(String command) {
        return mHHTDeviceDelegate.setTvosCommonCommand(command);
    }



    //设置接口命令
    public boolean setTvosInterfaceCommand(String command) {
        return mHHTDeviceDelegate.setTvosInterfaceCommand(command);
    }



    //获取环境变量
    public String getEnvironment(String command) {
        return mHHTDeviceDelegate.getEnvironment(command);
    }



    //设置环境变量
    public void setEnvironment(String command1, String command2) {
        mHHTDeviceDelegate.setEnvironment(command1, command2);
    }



    public HHTTime getAutoPowerOnTime() {
        return mHHTDeviceDelegate.getAutoPowerOnTime();
    }



    public HHTTime getAutoPowerOffTime() {
        return mHHTDeviceDelegate.getAutoPowerOffTime();
    }



    public void setAutoPowerOnTime(HHTTime time, boolean[] weekdayArr) {
        mHHTDeviceDelegate.setAutoPowerOnTime(time, weekdayArr);
    }



    public void setAutoPowerOffTime(HHTTime time, boolean[] weekdayArr) {
        mHHTDeviceDelegate.setAutoPowerOffTime(time, weekdayArr);
    }



    public void setAutoPowerOnTimeEnable(boolean enable) {
        mHHTDeviceDelegate.setAutoPowerOnTimeEnable(enable);
    }



    public void setAutoPowerOffTimeEnable(boolean enable) {
        mHHTDeviceDelegate.setAutoPowerOffTimeEnable(enable);
    }



    public boolean getAutoPowerOnTimeEnable() {
        return mHHTDeviceDelegate.getAutoPowerOnTimeEnable();
    }



    public boolean getAutoPowerOffTimeEnable() {
        return mHHTDeviceDelegate.getAutoPowerOffTimeEnable();
    }



    public void closeGpioDeviceStatus() {
        mHHTDeviceDelegate.closeGpioDeviceStatus();
    }



    public boolean isTopCamera() {
        return mHHTDeviceDelegate.isTopCamera();
    }



    public void setTopCamera() {
        mHHTDeviceDelegate.setTopCamera();
    }



    public void setBottomCamera() {
        mHHTDeviceDelegate.setBottomCamera();
    }



    public void setNetWakeUpStatus(boolean isWakeUp) {
        mHHTDeviceDelegate.setNetWakeUpStatus(isWakeUp);
    }



    public boolean isNetWakeUp() {
        return mHHTDeviceDelegate.isNetWakeUp();
    }



    public void setMicOnOff(boolean open) {
        mHHTDeviceDelegate.setMicOnOff(open);
    }



    public boolean isMicOff() {
        return mHHTDeviceDelegate.isMicOff();
    }



    public void updateSystemPrepare() {
        mHHTDeviceDelegate.updateSystemPrepare();
    }



    public String getPowerOSource() {
        return mHHTDeviceDelegate.getPowerOSource();
    }



    public boolean getCecAutoPowerOn() {
        return mHHTDeviceDelegate.getCecAutoPowerOn();
    }



    public boolean getCecAutoPowerOff() {
        return mHHTDeviceDelegate.getCecAutoPowerOff();
    }



    public void setCecAutoPowerOn(boolean on) {
        mHHTDeviceDelegate.setCecAutoPowerOn(on);
    }



    public void setCecAutoPowerOff(boolean off) {
        mHHTDeviceDelegate.setCecAutoPowerOff(off);
    }



    //cec 是否打开 1：打开 0：关闭
    public int getCECAutoSwitchSource() {
        return mHHTDeviceDelegate.getCECAutoSwitchSource();
    }



    //设置cec 功能 1：打开 0：关闭
    public void setCECAutoSwitchSource(int value) {
        mHHTDeviceDelegate.setCECAutoSwitchSource(value);
    }



    //护眼模式 1：开启 0：关闭
    public int getEyeWarmStatus() {
        return mHHTDeviceDelegate.getEyeWarmStatus();
    }



    //设置护眼模式
    public void setEyeWarm(int value) {
        mHHTDeviceDelegate.setEyeWarm(value);
    }



    //护眼书写
    public int getEyeWriteStatus() {
        return mHHTDeviceDelegate.getEyeWriteStatus();
    }



    //设置护眼书写
    public void setEyeWrite(int value) {
        mHHTDeviceDelegate.setEyeWrite(value);
    }



    //获取OPS 状态
    public int getOPSUsbStatus() {
        return mHHTDeviceDelegate.getOPSUsbStatus();
    }



    //newline 助手是否打开
    public int getNLStatus() {
        return mHHTDeviceDelegate.getNLStatus();
    }



    //获取ops插入状态
    public int getOpsPlugged() {
        return mHHTDeviceDelegate.getOpsPlugged();
    }



    //硬静音，不会显示静音图标
    public void setMuteOn() {
        mHHTDeviceDelegate.setMuteOn();
    }



    //解除硬件静音
    public void setMuteOff() {
        mHHTDeviceDelegate.setMuteOff();
    }



    //设置HDMI out分辨率 HDMI_TX_RESOLUTION_480p / HDMI_TX_RESOLUTION_720p/ HDMI_TX_RESOLUTION_1080p / HDMI_TX_RESOLUTION_2160p
    public void setHDMIOutType(String param) {
        mHHTDeviceDelegate.setHDMIOutType(param);
    }



    public int getAmbientLightValue() {
        return mHHTDeviceDelegate.getAmbientLightValue();
    }



    public int getAmbientTempValue() {
        return mHHTDeviceDelegate.getAmbientTempValue();
    }



    public int getAmbientCO2Value() {
        return mHHTDeviceDelegate.getAmbientCO2Value();
    }




}
