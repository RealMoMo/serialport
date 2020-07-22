package com.newline.serialport.model.recevier;

import com.newline.serialport.setting.HHTDeviceManager;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/22 10:53
 * @describe
 */
public class VolumeRecevierModel extends RecevierSerialPortModel {

    /**
     * 设置音量全命令： 7F 08 99 A2 B3 C4 02 FF 05 XX CF
     * XX 范围：0-100
     */
    public static final String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 05";

    private int volume;


    public VolumeRecevierModel(HHTDeviceManager hhtDeviceManager,int volume) {
        super(hhtDeviceManager);
        this.volume = volume;
    }

    @Override
    public void action() {
        hhtDeviceManager.get().setVolume(volume);
    }
}
