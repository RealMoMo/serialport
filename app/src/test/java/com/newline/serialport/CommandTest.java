package com.newline.serialport;

import com.newline.serialport.model.recevier.VolumeRecevierModel;

import org.junit.Test;

import java.math.BigInteger;

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
} 
