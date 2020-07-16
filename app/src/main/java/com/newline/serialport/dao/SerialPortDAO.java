package com.newline.serialport.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.net.Uri;
import android.support.annotation.Keep;
import android.util.Log;
import android.view.KeyEvent;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/16 15:31
 * @describe
 */
@Keep
public class SerialPortDAO {


    @Keep
    public static final String SERIAL_PORT_AUTHORITY = "com.newline.serialport";

    @Keep
    public static final Uri SERIAL_PORT_URI =
            Uri.parse("content://" + SERIAL_PORT_AUTHORITY);

    @Keep
    public static final String NAME = "key";
    @Keep
    public static final String VALUE = "value";
    @Keep
    public static final String KEY_KEYCODE=  "keycode";

    /**
     * 发送keycode to Newline串口服务
     * @param context
     * @param keycode
     * @return  是否发送成功
     */
    @Keep
    public static boolean putKeycode(Context context, int keycode){

        return putInt(context.getContentResolver(),SERIAL_PORT_URI,KEY_KEYCODE,keycode);
    }


    @Keep
    private static boolean putInt(ContentResolver resolver, Uri uri,
                                       String name, int value) {

        try {
            ContentValues conValue = new ContentValues();
            conValue.put(NAME, name);
            conValue.put(VALUE, value);
            resolver.update(uri, conValue, null, null);
            return true;
        } catch (SQLException e) {
           e.printStackTrace();
            return false;
        }

    }
}

