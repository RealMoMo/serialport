package com.newline.serialport.setting.observer.observer.broadcastobserver.platform.standard;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

import com.newline.serialport.setting.observer.observer.broadcastobserver.BaseBroadcastReceiver;
import com.newline.serialport.setting.utils.SettingConstant;


/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/9/25 22:18
 * @describe
 */
public class VolumeOBroadcastReceiver extends BaseBroadcastReceiver {

    public VolumeOBroadcastReceiver(Context context) {
        super(context);
    }

    @Override
    protected IntentFilter initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SettingConstant.ACTION_STREAM_MUTE);
        intentFilter.addAction(SettingConstant.ACTION_VOLUME_CHANGED);

        return intentFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case SettingConstant.ACTION_STREAM_MUTE: {
                if (intent.getIntExtra("EXTRA_VOLUME_STREAM_TYPE", AudioManager.STREAM_MUSIC) == AudioManager.STREAM_MUSIC) {

                    proxyDeviceListener.onMuteChange();
                }
            }
            break;
            case SettingConstant.ACTION_VOLUME_CHANGED: {
                if (intent.getIntExtra("EXTRA_VOLUME_STREAM_TYPE", AudioManager.STREAM_MUSIC) == AudioManager.STREAM_MUSIC) {
                    proxyDeviceListener.onVolumeChange();
                }

            }
            break;
        }
    }
}
