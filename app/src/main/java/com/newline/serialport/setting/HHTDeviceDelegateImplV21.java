package com.newline.serialport.setting;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.provider.Settings;


import com.newline.serialport.setting.i.FullStatusListener;
import com.newline.serialport.setting.utils.HHTConstant;

import java.lang.reflect.Method;

/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/4/10 10:31
 * @describe
 */
class HHTDeviceDelegateImplV21 extends HHTDeviceDelegateImplBase {

    HHTDeviceDelegateImplV21(Context context) {
        super(context);


    }

    HHTDeviceDelegateImplV21(Context context, FullStatusListener fullStatusListener) {
        super(context, fullStatusListener);


    }


    @Override
    void setMute(boolean mute) {
        Class<?> c = null;

        try {
            c = Class.forName("android.media.AudioManager");

            Method m1 = c.getMethod("setMasterMute", boolean.class);

            m1.invoke(mAudiomanager, mute);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    boolean getBeepSoundEnable() {
        return Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.SOUND_EFFECTS_ENABLED, 1) == 0 ? true : false;
    }

    @Override
    void setBeepSoundEnable(boolean enable) {
        setNotificationSoundEnable(enable);
        setTouchSoundEnable(enable);
    }

    @Override
    void setNotificationSoundEnable(boolean enable) {
        mAudiomanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, !enable);
    }

    @Override
    void setTouchSoundEnable(boolean enable) {
        Settings.System.putInt(mContext.getContentResolver(),
                Settings.System.SOUND_EFFECTS_ENABLED, enable ? 1 : 0);
        if (enable) {
            mAudiomanager.loadSoundEffects();
        } else {
            mAudiomanager.unloadSoundEffects();
        }
    }

    @Override
    public void releaseRes() {

        super.releaseRes();

    }


    @Override
    void startFloatBar() {
        Intent intent = new Intent(HHTConstant.ACTION_START_FLOATBAR);
        mContext.sendBroadcast(intent);
    }

    @Override
    void stopFloatBar() {
        Intent intent = new Intent(HHTConstant.ACTION_STOP_FLOATBAR);
        mContext.sendBroadcast(intent);
    }


}
