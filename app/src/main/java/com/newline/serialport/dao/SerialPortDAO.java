package com.newline.serialport.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.support.annotation.IntDef;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;

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

    @Keep
    public static final String KEY_ANDROID_UPGRADE = "android_upgrade";
    @Keep
    public static final String KEY_AUDIO_UPGRADE = "audio_upgrade";



    /**
     * 按键事件意图类型
     */
    @Keep
    @IntDef({KeyInent.DOWN, KeyInent.REPEAT, KeyInent.UP,
            KeyInent.PRESS, KeyInent.LONG_PRESS,KeyInent.CUSTOM})
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
        //自定义类型，用于特殊业务处理
        @Keep
        int CUSTOM = DOWN +5;
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

    /**
     * 获取Android升级信息
     * @param context
     * @return Anroid升级的字符信息,可实际通过json格式转为 {@link com.newline.serialport.model.AndroidUpgradeBean}，若没存储则返回Null
     */
    @Keep
    public static @Nullable String getAndroidUpgradeInfo(Context context){
       return getString(getUriFor(SERIAL_PORT_URI,null),context.getContentResolver(),KEY_ANDROID_UPGRADE);
    }


    /**
     * 获取音频版升级信息
     * @param context
     * @return Anroid升级的字符信息,可实际通过json格式转为 {@link com.newline.serialport.model.AudioVersionUpgradeBean}，若没存储则返回Null
     */
    @Keep
    public static @Nullable String getAudioUpgradeInfo(Context context){
        return getString(getUriFor(SERIAL_PORT_URI,null),context.getContentResolver(),KEY_AUDIO_UPGRADE);
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



    @Keep
    private static @Nullable String getString(Uri uri, ContentResolver resolver, String name) {
        Cursor cursor = resolver.query(uri, new String[]{name}, null, null, null);
        String value = null;
        if (cursor == null || cursor.getCount() == 0) {
            return value;
        }
        cursor.moveToFirst();
        value = cursor.getString(cursor.getColumnIndex(name));
        cursor.close();

        return value;
    }


    @Keep
    private static Uri getUriFor(Uri uri, @Nullable String name) {

        if (name == null) {
            return uri;
        }

        if (name.isEmpty()) {
            return uri;
        }

        return Uri.withAppendedPath(uri, name);
    }
}

