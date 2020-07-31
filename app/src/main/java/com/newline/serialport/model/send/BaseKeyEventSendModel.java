package com.newline.serialport.model.send;

import com.newline.serialport.SerialPortUtils;
import com.newline.serialport.dao.SerialPortDAO;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/31 15:49
 * @describe   按键发送消息基类
 */
public abstract class BaseKeyEventSendModel extends SendSerialPortModel{

    @SerialPortDAO.KeyInent
    protected int keyIntent = SerialPortDAO.KeyInent.PRESS;

    public BaseKeyEventSendModel(SerialPortUtils serialPort, @SerialPortDAO.KeyInent int keyIntent) {
        super(serialPort);
        this.keyIntent = keyIntent;
    }

    @Override
    public String getSendContent() {
        switch (keyIntent){
            case SerialPortDAO.KeyInent.DOWN:{
                return getKeyDownContent();
            }
            case SerialPortDAO.KeyInent.REPEAT:{
                return getKeyRepeatContent();
            }
            case SerialPortDAO.KeyInent.UP:{
                return getKeyUpContent();
            }
            case SerialPortDAO.KeyInent.PRESS:{
                return getKeyPressContent();
            }
            case SerialPortDAO.KeyInent.LONG_PRESS:{
                return getKeyLongPressContent();
            }
            default:{
                return "";
            }
        }
    }


    abstract String getKeyDownContent();
    abstract String getKeyRepeatContent();
    abstract String getKeyUpContent();
    abstract String getKeyPressContent();
    abstract String getKeyLongPressContent();
}
