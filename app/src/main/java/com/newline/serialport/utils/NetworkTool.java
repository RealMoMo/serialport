package com.newline.serialport.utils;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.hht.tools.log.Logger;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class NetworkTool {

    public static String hostip;             //本机IP
    public static String hostmac;            //本机MAC


    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWifiIp(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        // 获得IP地址的方法一：
        int ipAddress = info.getIpAddress();
        String ipString = "";
        if (ipAddress != 0) {
            ipString = ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                    + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
        }
        return ipString;
    }

    public String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    public static String getLanMac() {
        Enumeration<NetworkInterface> interfaceEnumeration = null;
        try {
            interfaceEnumeration = NetworkInterface.getNetworkInterfaces();

            while (interfaceEnumeration.hasMoreElements()){
                NetworkInterface networkInterface = interfaceEnumeration.nextElement();
                byte[] addr = networkInterface.getHardwareAddress();
                if (addr==null || addr.length==0){
                    continue;
                }
                StringBuilder buf = new StringBuilder();
                for (byte b: addr){
                    buf.append(String.format("%02X:",b));

                }
                if (buf.length()>0){
                    buf.deleteCharAt(buf.length()-1);

                }
                return buf.toString();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getWifiMac(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            // 获得IP地址的方法一：
            return info.getMacAddress();
        }catch (Exception e) {
            Logger.e(e);
        }

        return "";
    }
}
