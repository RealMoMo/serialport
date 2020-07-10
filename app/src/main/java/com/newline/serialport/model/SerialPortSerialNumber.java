package com.newline.serialport.model;

import android.content.Context;

import com.newline.serialport.chip.UniteImpl;
import com.hht.tools.log.Logger;
import com.newline.serialport.chip.UniteImpl;

import java.io.UnsupportedEncodingException;

public class SerialPortSerialNumber extends SerialPortModel {

    public static final String IDENTIFICATION_NUMBER = "99 A2 B3 C4 02 FF AA 55";

    protected String returnCode = "";

    public static String HexString = "";

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void action(Context context) {
//        HexString = "7F 16 99 A2 B3 C4 02 FF AA 55 E7 9B B8 E6 9C BA 20 28 32 29 53 CF";模拟数据 : 相机（2）
        if (!HexString.contains(IDENTIFICATION_NUMBER)) {
            return;
        }
        String zz = "00";
        returnCode = "";
        String headerStart = "7F ";
        String EndCode = " CF";
        String textTemp = HexString.split(IDENTIFICATION_NUMBER)[1];
        Logger.i("textTemp = "+ textTemp);
        textTemp = textTemp.substring(0, textTemp.length() - 2);
        String text = textTemp.substring(1, textTemp.length() - 4);
        Logger.i("text = "+ text);
        String content = asciiToString(text);
        Logger.i("content = "+content);
        if (content != null) {
            UniteImpl.setEnvironment(content);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String envStr = UniteImpl.getEnvironment();
            Logger.i("envStr  = "+ envStr);
            zz = content.equals(envStr) ? "00" : "01";
            if ("01".equals(zz)) {
                Logger.i("envStr error reset it");
                UniteImpl.setEnvironment("");
            }
        }
        String receiverFlag = zz;

        //不包括命令头、命令长度、命令尾
        String idNumber = IDENTIFICATION_NUMBER.replace("AA 55", "AA 56");
        String totalString = (idNumber + " " + receiverFlag + " " + text);
        int total = totalString.replaceAll(" ", "").length() / 2;
        Logger.i("totalString " +totalString + "  total = "+ total);
        String totalHex = Integer.toHexString(total + 1);//1 = checkSum
        if (totalHex.length() == 1) {
            totalHex = "0" +totalHex;
        }
        Logger.i("totalHex ="+totalHex);
        String checkSum = checkSum((totalHex + " " + totalString));

        returnCode = (headerStart + totalHex + " " + totalString + " " + checkSum + EndCode).toUpperCase();
        Logger.i("returnCode = "+returnCode);
    }

    /**
     * Ascii转换为字符串
     * @param value
     * @return
     */
    public static String asciiToString(String value) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(" ");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i], 16));
        }

        byte[] bytes = hexToByte(value);
        try {
            String fileName = new String(bytes,"UTF-8");
            Logger.d("fileName " +fileName);

            return fileName;
        } catch (UnsupportedEncodingException e) {
            Logger.e(e);
        }

        return sbu.toString();
    }

    /**
     * hex转byte数组
     * @param hexString
     * @return
     */
    public static byte[] hexToByte(String hexString){
        int m = 0, n = 0;
        String[] hexSet = hexString.split(" ");
        int byteLen = hexSet.length; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        Logger.d("hexString = "+hexString);
        Logger.d("hexSet " + byteLen);
        for (int i = 0; i < byteLen; i++) {
            String hex = hexSet[i];
            int intVal = 0;
            if (hex.length() == 1) {
                intVal = Integer.decode("0x0" + hex.substring(0, 1));
            } else {
                intVal = Integer.decode("0x" + hex.substring(0, 1) + hex.substring(1, 2));
            }
            ret[i] = Byte.valueOf((byte)intVal);
        }
        return ret;
    }

    private String checkSum(String target) {
        Logger.i("target "+ target);
        if (!target.contains(" ")) {
            return "00";
        }
        String[] codes = target.split(" ");
        int sum = 0;
        for (int i = 0; i < codes.length; i++) {
            int number = Integer.parseInt(codes[i], 16);
            sum = sum + number;
        }

        String sumString = Integer.toHexString((sum & 0xff));
        if (sumString.length() == 1) {
            return "0" + sumString;
        }

        return sumString;
    }
}
