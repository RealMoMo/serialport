package com.newline.serialport;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/8/28 15:24
 * @describe
 */
public class SubCommandTest {

    @Test
    public void subString(){
        String rawData = "7F0B0B8A123456789001CF";

        String data = rawData.replace(" ","");
        if(data!= null && data.length()>12){
            boolean startMatch = data.startsWith("7F");
            boolean endMatch = data.endsWith("01CF");
            boolean dataTypeMatch = data.regionMatches(4,"0B8A",0,4);

            String dataLength = data.substring(2,4);
            int realLength = new BigInteger(dataLength, 16).intValue()-7+1;
            System.out.println("realLength:"+ realLength);
            System.out.println("raw data:"+ (data.length()/2-6) );

            boolean dataLengthMatch = (data.length()/2-6) == realLength;


            assertTrue(startMatch);
            assertTrue(endMatch);
            assertTrue(dataTypeMatch);
            assertTrue(dataLengthMatch);


            if(startMatch && endMatch && dataTypeMatch){

                System.out.println("deal with data:"+ data.substring(8,data.length()-4));
            }
        }
    }
}
