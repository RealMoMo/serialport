package com.newline.serialport.model;

import com.newline.serialport.dao.SerialPortDAO;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/31 14:11
 * @describe
 */
public class KeyEventBean {

    private int keycode;

    @SerialPortDAO.KeyInent
    private int keyIntent;

    public KeyEventBean(int keycode,@SerialPortDAO.KeyInent int keyIntent) {
        this.keycode = keycode;
        this.keyIntent = keyIntent;
    }

    public int getKeycode() {
        return keycode;
    }

    public int getKeyIntent() {
        return keyIntent;
    }

    @Override
    public String toString() {
        return "KeyEventBean{" +
                "keycode=" + keycode +
                ", keyIntent=" + keyIntent +
                '}';
    }
}
