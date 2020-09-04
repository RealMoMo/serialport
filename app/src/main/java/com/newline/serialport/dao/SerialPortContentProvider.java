package com.newline.serialport.dao;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.newline.serialport.model.KeyEventBean;
import com.newline.serialport.model.recevier.UpgradeRecevierModel;
import com.newline.serialport.utils.GlobalConfig;

import java.util.Arrays;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/16 15:20
 * @describe
 */
public class SerialPortContentProvider extends ContentProvider {

    private static Object lock = new Object();

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        synchronized (lock){
            if(projection== null){
                return null;
            }
            if(projection.length<1){
                return null;
            }
            String key = projection[0];
            if(key == null){
                return null;
            }
            switch (key){
                case SerialPortDAO.KEY_ANDROID_UPGRADE:{
                    MatrixCursor matrixCursor = new MatrixCursor(projection,1);
                    matrixCursor.addRow(new String[]{getContext().getSharedPreferences(GlobalConfig.SP_NAME, Context.MODE_PRIVATE).getString(GlobalConfig.KEY_SP_ANDROID_UPGRADE_INFO,null)});
                    matrixCursor.setNotificationUri(getContext().getContentResolver(),uri);
                    return matrixCursor;
                }
                default:{
                    return null;
                }
            }
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        synchronized (lock){
            String key = (String)values.get(SerialPortDAO.NAME);
            if(key == null){
                return 0;
            }
            switch (key){
                case SerialPortDAO.KEY_KEYCODE:{
                    KeyEventBean keyEventBean = new KeyEventBean((int)values.get(SerialPortDAO.VALUE),(int)values.get(SerialPortDAO.KEY_INTENT));
                    KeyEventDAO.keyCodeQueue.add(keyEventBean);
                    getContext().getContentResolver().notifyChange(Uri.withAppendedPath(uri,key), null);
                    return 1;
                }
                case SerialPortDAO.KEY_ANDROID_UPGRADE:{
                    /**
                     *  实际存储{@link UpgradeRecevierModel#action()} 在此处理
                     *  此处只负责通知数据有变化
                     */

                    getContext().getContentResolver().notifyChange(Uri.withAppendedPath(uri,key), null);
                    return 1;
                }
                default:
                    return 0;
            }
        }


    }
}
