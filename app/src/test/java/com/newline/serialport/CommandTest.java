package com.newline.serialport;

import com.newline.serialport.model.recevier.VolumeRecevierModel;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/22 13:39
 * @describe
 */
public class CommandTest {
    private String command = "7F 08 99 A2 B3 C4 02 FF 05 64 CF";

    public String CONTROLLING_CODE = "7F 08 99 A2 B3 C4 02 FF 05";
    @Test
    public void testCommandParse(){
        if(command.length() == 32){
            if(command.contains(CONTROLLING_CODE)){
                BigInteger big = new BigInteger(catchKeyValue(command), 16);
                System.out.println(big.intValue());
            }
        }
    }



    /**
     * 获取指令倒数第二段内容
     * @param targetCode
     * @return
     */
    private static String catchKeyValue(String targetCode) {
        String value = targetCode.substring(27, 29);
        return value;
    }


    @Test
    public void testStr2HexStr(){
        System.out.println(str2HexStr("realmo"));
        System.out.println(str2HexStr("RealMo"));
        System.out.println(str2HexStr("A"));//41
        System.out.println(str2HexStr("1"));//31
        //System.out.println(Arrays.toString("realmo".getBytes()));//[114,101,97,108,109,111]
    }



    /**
     * 字符串转换成为16进制(无需Unicode编码)
     * @param str
     * @return
     */
    private static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }
} 
