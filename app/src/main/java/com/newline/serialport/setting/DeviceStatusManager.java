package com.newline.serialport.setting;

import android.content.Context;

import com.newline.serialport.setting.i.BaseDeviceStatusListener;
import com.newline.serialport.setting.i.HHTDeviceStatusListener;
import com.newline.serialport.setting.i.ReleaseRes;
import com.newline.serialport.setting.i.StandardDeviceStatusListener;
import com.newline.serialport.setting.observer.ProxyDeviceListener;
import com.newline.serialport.setting.observer.observable.HHTDeviceObservable;
import com.newline.serialport.setting.observer.observer.broadcastobserver.platform.standard.VolumeOBroadcastReceiver;
import com.newline.serialport.setting.observer.observer.contentobserver.platform.mstar.VolumeMstarV21Observer;
import com.newline.serialport.setting.observer.observer.contentobserver.platform.newline.FreezeObserver;
import com.newline.serialport.setting.observer.observer.contentobserver.platform.standard.BrightnessObserver;
import com.newline.serialport.setting.observer.observer.contentobserver.platform.standard.MuteV21Observer;


/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/9/25 20:28
 * @describe  状态变化统一管理者
 */
final class DeviceStatusManager implements ProxyDeviceListener, ReleaseRes {

    private StandardDeviceStatusListener standardDeviceStatusListener;
    private HHTDeviceStatusListener hhtDeviceStatusListener;
    private HHTDeviceDelegate hhtDeviceDelegate;

    private Context mContext;

    private HHTDeviceObservable hhtDeviceObservable;


    DeviceStatusManager(HHTDeviceDelegate hhtDeviceDelegate, Context context) {
        this.hhtDeviceDelegate = hhtDeviceDelegate;
        mContext = context.getApplicationContext();
        hhtDeviceObservable = new HHTDeviceObservable();
        hhtDeviceObservable.setProxyDeviceListener(this);
    }

    void setStandardDeviceStatusListener(StandardDeviceStatusListener standardDeviceStatusListener) {
        this.standardDeviceStatusListener = standardDeviceStatusListener;

        hhtDeviceObservable.registerObserver(new BrightnessObserver(mContext));
        if(hhtDeviceDelegate instanceof HHTDeviceDelegateImplO){
            hhtDeviceObservable.registerObserver(new VolumeOBroadcastReceiver(mContext));
        }else{
            hhtDeviceObservable.registerObserver(new MuteV21Observer(mContext));
            hhtDeviceObservable.registerObserver(new VolumeMstarV21Observer(mContext));
        }


    }

    void setHhtDeviceStatusListener(HHTDeviceStatusListener hhtDeviceStatusListener) {
        this.hhtDeviceStatusListener = hhtDeviceStatusListener;
        hhtDeviceObservable.registerObserver(new FreezeObserver(mContext));

    }

    @Override
    public void onFreezeChange() {
        checkListernNotNull(hhtDeviceStatusListener);
        hhtDeviceStatusListener.onFreezeChange(hhtDeviceDelegate.getFreezeStatus());

    }

    @Override
    public void onCurrentSourceChange() {
        checkListernNotNull(hhtDeviceStatusListener);
        hhtDeviceStatusListener.onCurrentSourceChange(hhtDeviceDelegate.getTopSourceType());

    }

    @Override
    public void onMuteChange() {
        checkListernNotNull(standardDeviceStatusListener);
        standardDeviceStatusListener.onMuteChange(hhtDeviceDelegate.getMuteStatus());

    }

    @Override
    public void onVolumeChange() {
        checkListernNotNull(standardDeviceStatusListener);
        standardDeviceStatusListener.onVolumeChange(hhtDeviceDelegate.getVolume());

    }

    @Override
    public void onBrightnessChange() {
        checkListernNotNull(standardDeviceStatusListener);
        standardDeviceStatusListener.onBrightnessChange(hhtDeviceDelegate.getBright());

    }

    private void checkListernNotNull(BaseDeviceStatusListener listener){
        if(listener == null){
            throw new NullPointerException("DeviceStatusListner == null,please set listener!");
        }
    }

    @Override
    public void releaseRes() {
        hhtDeviceObservable.unregisterAll();
        mContext = null;
    }
}
