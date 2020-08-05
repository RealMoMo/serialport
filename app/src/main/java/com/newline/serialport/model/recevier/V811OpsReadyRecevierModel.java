package com.newline.serialport.model.recevier;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.newline.serialport.setting.HHTDeviceManager;
import com.newline.serialport.setting.utils.SystemPropertiesUtils;


/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/8/1 14:32
 * @describe
 */
public class V811OpsReadyRecevierModel extends RecevierSerialPortModel {


    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 66 01 CF";

    private Context mContext;

    public V811OpsReadyRecevierModel(Context context, HHTDeviceManager hhtDeviceManager) {
        super(hhtDeviceManager);
        mContext = context;
    }

    @Override
    public void action() {
        //发广播给系统，结束开机动画以及跳转到ops
        mContext.sendBroadcast(new Intent("android.tencent.dismiss_view"));
        if(!((boolean)SystemPropertiesUtils.getProperty("ro.product.dismiss", false))) {
            SystemPropertiesUtils.setProperty("ro.product.dismiss","true");
        }

    }

    @Override
    public String retryContent() {
        return CONTROLLING_CODE;
    }
}
