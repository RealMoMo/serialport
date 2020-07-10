package com.newline.porttest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.hht.porttest.R;
import com.hht.tools.log.LogIntent;
import com.hht.tools.log.Logger;

import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends Activity {
    public static final String USB_UART_LOCAL_SOCKET = "usblocalsocket";
    private OutputStream outputStream;

    //创建对象
    LocalSocket localSocket = new LocalSocket();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.setOpenLog(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void clickForOpenFactory2(View view) {
        startHHTFactoryMenu(this);
        finish();
    }

    public void clickForOpenFactory(View view) {
        startFactoryMenu(this);
        finish();
    }

    public void startFactoryMenu(Context context) {
        //连接socketServerSocket
//        try {
//            localSocket.connect(new LocalSocketAddress(USB_UART_LOCAL_SOCKET));
//            outputStream = localSocket.getOutputStream();
//            outputStream.write(toBytes("7F 09 99 A2 B3 C4 02 FF 0F 00 CF"));//mute on
//            outputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


        try {
            Intent intent = new Intent("mstar.tvsetting.factory.intent.action.MainmenuActivity");
            context.startActivity(intent);
        } catch (Exception var3) {
            Logger.e(var3);
        }
    }

    public void startHHTFactoryMenu(Context context) {
//        try {
//            localSocket.connect(new LocalSocketAddress(USB_UART_LOCAL_SOCKET));
//            outputStream = localSocket.getOutputStream();
//            outputStream.write(toBytes("7F 09 99 A2 B3 C4 02 FF 0F 01 CF"));//mute off
//            outputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.hht.factory", "com.hht.factory.main.ui.MainActivity"));
            context.startActivity(intent);
        } catch (Exception var3) {
            Logger.e(var3);
        }

    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        str = str.replace(" ", "");
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
