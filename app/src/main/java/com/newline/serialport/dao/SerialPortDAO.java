package com.newline.serialport.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.net.Uri;
import android.support.annotation.IntDef;
import android.support.annotation.Keep;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
    public static final String KEY_INTENT = "key_intent";
    @Keep
    public static final String KEY_KEYCODE=  "keycode";



    /**
     * 按键事件意图类型
     */
    @Keep
    @IntDef({KeyInent.DOWN, KeyInent.REPEAT, KeyInent.UP,KeyInent.PRESS, KeyInent.LONG_PRESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyInent {
        @Keep
        int DOWN = 1;
        @Keep
        int REPEAT = DOWN+1;
        @Keep
        int UP = DOWN+2;
        @Keep
        int PRESS = DOWN+3;
        @Keep
        int LONG_PRESS = DOWN+4;
    }

    /**
     * 发送keycode to Newline串口服务
     * @param context
     * @param keycode
     * @return  是否发送成功
     */
    @Keep
    public static boolean putKeycode(Context context, int keycode, @KeyInent int keyInent){

        try {
            ContentValues conValue = new ContentValues();
            conValue.put(NAME, KEY_KEYCODE);
            conValue.put(VALUE, keycode);
            conValue.put(KEY_INTENT, keyInent);
            context.getContentResolver().update(SERIAL_PORT_URI, conValue, null, null);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

