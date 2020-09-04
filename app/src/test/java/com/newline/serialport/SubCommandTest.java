package com.newline.serialport;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

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
        String rawData = "7F7d0B7A56312E302E307C54542D544336357C463A5C576F726B73706163655C43235C53657269616C536572766963655C4175746F5570646174654170705C62696E5C44656275675C7570646174655C7570646174652E7A69707C356335643834623261373462323932353062613339396161663736663564323601CF";

        String data = rawData.replace(" ","");
        if(data!= null && data.length()>12){
            boolean startMatch = data.startsWith("7F");
            boolean endMatch = data.endsWith("01CF");
            boolean dataTypeMatch = data.regionMatches(4,"0B7A",0,4);

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
                String realData = CommandTest.hexStr2Str(data.substring(8, data.length() - 4));
                String[] split = realData.split("\\|");

                System.out.println("deal with data:"+ data.substring(8,data.length()-4));
                System.out.println("data :"+ split[0]);
                System.out.println("data :"+ split[1]);
                System.out.println("data :"+ split[2]);
                System.out.println("data :"+ split[3]);
            }
        }
    }
}
