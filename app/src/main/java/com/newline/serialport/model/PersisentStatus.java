package com.newline.serialport.model;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/22 11:21
 * @describe
 */
public class PersisentStatus {
    private static final PersisentStatus ourInstance = new PersisentStatus();

    public static PersisentStatus getInstance() {
        return ourInstance;
    }

    private PersisentStatus() {
    }

    public int volume;
    public boolean volumeMute;
    public boolean micMute;
}
