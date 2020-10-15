package com.newline.serialport;

import org.junit.Test;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/10/15 14:20
 * @describe
 */
public class VersionTest {

    @Test
    public void testAudioVersion(){
        String fullInfo = "CP-A08-HT01-V1.0.x";
//        String fullInfo = "-";
//        String fullInfo = "";
        int i = fullInfo.lastIndexOf("-");
        if(i == -1){
            System.out.println("no find -");
        }else{
            String audioVersion = fullInfo.substring(i+1, fullInfo.length());
            System.out.println("audio version:"+audioVersion);
        }


        if(i == -1){
            System.out.println("no find -");
        }else{
            String audioModel =  fullInfo.substring(0, i);
            System.out.println("audio model:"+audioModel);
        }

    }
}
