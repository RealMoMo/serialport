package com.newline.serialport.setting;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.provider.Settings;

import com.newline.serialport.setting.i.FullStatusListener;
import com.newline.serialport.setting.utils.HHTConstant;


/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/4/10 10:31
 * @describe
 */
class HHTDeviceDelegateImplO extends HHTDeviceDelegateImplV21 {

    HHTDeviceDelegateImplO(Context context) {
        super(context);
    }

    HHTDeviceDelegateImplO(Context context, FullStatusListener fullStatusListener) {
        super(context, fullStatusListener);


    }


    @Override
    void setMute(boolean mute) {
        mAudiomanager.adjustStreamVolume(AudioManager.STREAM_MUSIC, mute ? AudioManager.ADJUST_MUTE : AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);
    }


    @Override
    void setBeepSoundEnable(boolean enable) {
        setNotificationSoundEnable(enable);
        setTouchSoundEnable(enable);
    }

    @Override
    void setNotificationSoundEnable(boolean enable) {
        //mAudiomanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, disable);
        Settings.System.putInt(mContext.getContentResolver(),
                Settings.System.NOTIFICATION_SOUND, enable ? 1 : 0);
        if (enable) {
            mAudiomanager.loadSoundEffects();
        } else {
            mAudiomanager.unloadSoundEffects();
        }
    }


    @Override
    void startFloatBar() {
        Intent intent = new Intent(HHTConstant.ACTION_START_FLOATBAR);
        //TODO checked platform by sub platform class to impl this method
        intent.setComponent(new ComponentName("", ""));
        mContext.sendBroadcast(intent);
    }

    @Override
    void stopFloatBar() {
        Intent intent = new Intent(HHTConstant.ACTION_STOP_FLOATBAR);
        //TODO checked platform by sub platform class to impl this method
        intent.setComponent(new ComponentName("", ""));
        mContext.sendBroadcast(intent);
    }

    @Override
    public void releaseRes() {
        super.releaseRes();
    }
}
