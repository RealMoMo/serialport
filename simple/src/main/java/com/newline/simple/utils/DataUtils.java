package com.newline.simple.utils;

import android.util.Log;

public class DataUtils {


    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }
        Log.i("","str toBytes= "+str);

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
//            Log.i("","str bytes[i]= "+bytes[i]);

        }
        return bytes;
    }
}
