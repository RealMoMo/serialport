package com.newline.serialport.setting;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.NonNull;

import com.hht.middleware.OnSourceSignalListener;
import com.hht.middleware.entity.EntitySourceInformation;
import com.hht.middleware.model.HHTTime;
import com.newline.serialport.setting.i.FullStatusListener;
import com.newline.serialport.setting.i.HHTDeviceStatusListener;
import com.newline.serialport.setting.i.ReleaseRes;
import com.newline.serialport.setting.i.StandardDeviceStatusListener;
import com.newline.serialport.setting.utils.HHTPlatformUtils;


import java.util.List;
import java.util.Locale;


/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/4/10 10:25
 * @describe
 */
public abstract class HHTDeviceDelegate implements ReleaseRes {

    public static HHTDeviceDelegate create(Context context){
        if(Build.VERSION.SDK_INT >=26){
            return new HHTDeviceDelegateImplO(context);
        }else{
            return new HHTDeviceDelegateImplV21(context);
        }

    }


    public static HHTDeviceDelegate create(@NonNull Context context , @NonNull FullStatusListener statusListener){

        if(Build.VERSION.SDK_INT >=26){
            return new HHTDeviceDelegateImplO(context, statusListener);
        }else{
            return new HHTDeviceDelegateImplV21(context, statusListener);
        }

    }

    abstract void setOnStandardDeviceStatusListener(StandardDeviceStatusListener standardDeviceStatusListener);

    abstract void setOnHHTDeviceStatusListener(HHTDeviceStatusListener hhtDeviceStatusListener);

    abstract void setOnFullStatusListener(FullStatusListener fullStatusListener);
    //======sound setting============

    abstract void setVolume(int targetVolume);

    abstract void setVolume(int streamType,int targetVolume);

    abstract void setVolume(int streamType,int targetVolume,int flag);

    abstract int getVolume();

    abstract int getVolume(int streamType);

    abstract int getMaxVolume(int streamType);

    abstract void setVolumeUp(int streamType,boolean showUi);

    abstract void setVolumeDown(int streamType,boolean showUi);

    abstract void setVolumeUp(boolean showUi);

    abstract void setVolumeDown(boolean showUi);

    abstract boolean getMuteStatus();

    abstract void setMute(boolean mute);

    abstract boolean getBeepSoundEnable();

    abstract void setBeepSoundEnable(boolean enable);

    abstract void setNotificationSoundEnable(boolean enable);

    abstract void setTouchSoundEnable(boolean enable);

    //======bright setting =======================

    abstract int getBright();

    abstract void setBright(int value);

    abstract int getSystemMaximumScreenBright(PowerManager powerManager);

    abstract int getSystemMinimumScreenBright(PowerManager powerManager);

    //=======resolution info================
    abstract String getScreenResolution();

    abstract int getScreenWidthResolution();

    abstract int getScreenHeightResolution();

    //=======Locale info ===============
    abstract Locale getLocale();

    abstract String getLanguage();

    abstract String getDisplayLanguage();

    abstract String getCountry();

    abstract String getDisplayCountry();

    abstract String getDisplayName();

    abstract String getLanguageTag();

    //===========Android other function ======================
    abstract void setSystemLanguage(Locale locale);

    //==========hht function=============
    abstract void startFloatBar();

    abstract void stopFloatBar();

    abstract void showFloatBar();

    abstract void hideFloatBar();

    abstract void openSoundOnly();

    abstract void closeSoundOnly();

    abstract void openFreezeScreen();

    abstract void closeFreezeScreen();

    //获取冻屏状态
    abstract boolean getFreezeStatus();

    //执行冻屏或解冻屏
    abstract void doFreeze();

    abstract void openBlueLightFilter();

    abstract void closeBlueLightFilter();

    abstract void updatedBlueLightFilterLevel(int level);

    abstract void openChildLock();

    abstract void closeChildLock();

    abstract void openWhiteBoard();

    abstract void openAnnotation();

    abstract void closeAnnotation();

    abstract void killWhiteBoard();

    abstract Bitmap screenShot();

    abstract void clearWhiteBoardScreenShotImg(); //just oem rs had this function

    abstract void openFullSource(int ordinal);

    abstract void sleep();

    abstract void wakeup();


//    //---------rect touch function---------------
//      middleware do this
//    abstract int initRectTouch(int topLeftX, int topLeftY, int width, int height);
//
//    abstract void updateRectTouch(int id, int topLeftX, int topLeftY, int width, int height);
//
//    abstract void cancelRectTouch(int id);
//
//    abstract  void setSourceDisableTouch(int pid);
//
//    abstract void setSourceEnableTouch(int pid);
//
//    //-----------------------------------------



    //===========midware=========
    abstract void bindHHTMiddleware(boolean initAllMiddleService);

    abstract void unbindHHTMiddleware();

    abstract void initHHTMiddlerwareService();

    abstract void initHHTSourceManager();

    abstract void initHHTSystemManager();

    abstract void initHHTAudioManager();

    abstract void initHHTDisplayManager();

    abstract void initHHTNetworkManager();


    //获取顶端信号源ordinal
    abstract int getTopSourceOrdinal();

    abstract String getTopSourceType();

    //判断信号源是否有信号
    abstract boolean hasSourceSignal(int ordinal);

    abstract void openSource(String sourceType);

    abstract void openSource(int sourceOrdinal);

    abstract List<String> getSourceTypeList();

    //切换TOUCH POINT
    abstract boolean changeTPSource(int ordinal, int pid);

    //切换USB Source
    abstract boolean changeUSBSource(int ordinal);

    //信号源信息
    abstract EntitySourceInformation getSourceInformation(int ordinal);

    //监听信号源完成显示
    abstract void registerSourceSignalListener(OnSourceSignalListener listener);

    //停止监听信号源完成显示
    abstract void unregisterSourceSignalListener(OnSourceSignalListener listener);

    //设置全屏信号源
    abstract void setFullScreenSource();

    //设置预览信号源
    abstract void setPreviewSource(int left, int top, int right, int bottom);

    //开启全区域触控
    abstract void setSourceEnableTouch(int pid);

    //关闭全区域触控
    abstract void setSourceDisableTouch(int pid);

    //设置静音
    abstract boolean setSourceMute(boolean mute);

    //获取静音
    abstract boolean getSourceMuteState();

    //调整 VGA画面
    abstract boolean adjustVGAImage();

    //获取信号源宽高
    abstract int[] getPanelSize();

    //信号源色温、色调
    abstract void applyEyeMode();

    //RS判断存在至少一个信号源连接上
    abstract boolean hasAnySourceConnection();

    //打开OPS 应用
    abstract void startOpsApp(String appName);

    //设置常用命令
    abstract int[] setTvosCommonCommand(String command);

    //设置接口命令
    abstract boolean setTvosInterfaceCommand(String command);

    //获取环境变量
    abstract String getEnvironment(String command);

    //设置环境变量
    abstract void setEnvironment(String command1, String command2);

    abstract HHTTime getAutoPowerOnTime();

    abstract HHTTime getAutoPowerOffTime();

    abstract void setAutoPowerOnTime(HHTTime time, boolean[] weekdayArr);

    abstract void setAutoPowerOffTime(HHTTime time, boolean[] weekdayArr);

    abstract void setAutoPowerOnTimeEnable(boolean enable);

    abstract void setAutoPowerOffTimeEnable(boolean enable);

    abstract boolean getAutoPowerOnTimeEnable();

    abstract boolean getAutoPowerOffTimeEnable();

    abstract void closeGpioDeviceStatus();

    abstract boolean isTopCamera();

    abstract void setTopCamera();

    abstract void setBottomCamera();

    abstract void setNetWakeUpStatus(boolean isWakeUp);

    abstract boolean isNetWakeUp();

    abstract void setMicOnOff(boolean open);

    abstract boolean isMicOff();

    abstract void updateSystemPrepare();

    abstract String getPowerOSource();

    abstract boolean getCecAutoPowerOn();

    abstract boolean getCecAutoPowerOff();

    abstract void setCecAutoPowerOn(boolean on);

    abstract void setCecAutoPowerOff(boolean off);

    //cec 是否打开 1：打开 0：关闭
    abstract int getCECAutoSwitchSource();

    //设置cec 功能 1：打开 0：关闭
    abstract void setCECAutoSwitchSource(int value);

    //护眼模式 1：开启 0：关闭
    abstract int getEyeWarmStatus();

    //设置护眼模式
    abstract void setEyeWarm(int value);

    //护眼书写
    abstract int getEyeWriteStatus();

    //设置护眼书写
    abstract void setEyeWrite(int value);

    //获取OPS 状态
    abstract int getOPSUsbStatus();

    //newline 助手是否打开
    abstract int getNLStatus();

    //获取ops插入状态
    abstract int getOpsPlugged();

    //硬静音，不会显示静音图标
    abstract void setMuteOn();

    //解除硬件静音
    abstract void setMuteOff();

    //设置HDMI out分辨率 HDMI_TX_RESOLUTION_480p / HDMI_TX_RESOLUTION_720p/ HDMI_TX_RESOLUTION_1080p / HDMI_TX_RESOLUTION_2160p
    abstract void setHDMIOutType(String param);

    abstract int getAmbientLightValue();

    abstract int getAmbientTempValue();

    abstract int getAmbientCO2Value();


    //TODO add other setting function













}
