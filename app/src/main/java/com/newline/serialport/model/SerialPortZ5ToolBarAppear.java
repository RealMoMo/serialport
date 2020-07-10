package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.dao.SettingDAO;
import com.newline.serialport.utils.Sender;

public class SerialPortZ5ToolBarAppear extends SerialPortModel {

    public static final String CONTROLLING_CODE = "7F 09 99 A2 B3 C4 02 FF 0B 06 CF";

    protected String returnCode = "7F 09 99 A2 B3 C4 02 FF 0B 06 01 CF";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
        SettingDAO.setZ5ToolBarAppear(context, true);
        Sender.sendZ5ToolBarBroadcast(context);
    }
}