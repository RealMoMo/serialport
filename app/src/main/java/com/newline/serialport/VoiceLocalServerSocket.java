package com.newline.serialport;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.util.Log;

import com.hht.tools.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static java.lang.Thread.sleep;

public class VoiceLocalServerSocket {
    private static final String TAG = VoiceLocalServerSocket.class.getSimpleName();
    public static final String USB_UART_LOCAL_SOCKET = "usblocalsocket";
    LocalServerSocket serverSocket = null;
    InputStream inputStream = null;
    OutputStream outputStream = null;
    public boolean isOpen;
    public Thread SocketThread;

    public void openSocket() {
        if (SocketThread == null) {
            SocketThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Logger.i(" openSocket address: "+USB_UART_LOCAL_SOCKET);
                    try {
                        serverSocket = new LocalServerSocket(USB_UART_LOCAL_SOCKET);
                        isOpen = true;
                        while (isOpen) {
                            //等待建立连接
                            Logger.i("wait...");
                            LocalSocket receiver = serverSocket.accept();
                            //接收获取数据流
                            inputStream = receiver.getInputStream();
                            outputStream = receiver.getOutputStream();

                            Log.d(TAG, "进入线程run");
                            //64   1024
                            byte[] buffer = new byte[64];
                            int size; //读取数据的大小
                            try {
                                size = inputStream.read(buffer);
                                if (size > 0){
                                    Log.d(TAG, "run: 接收到了数据：" + bytesToHexString(buffer));
                                    Log.d(TAG, "run: 接收到了数据大小：" + String.valueOf(size));
//                                    onDataReceiveListener.onDataReceive(buffer,size);
                                }
                                Logger.i("just write back OK");
                                outputStream.write("OK".getBytes());
                                outputStream.flush();
                                outputStream.close();
                            } catch (IOException e) {
                                Log.e(TAG, "run: 数据读取异常：" +e.toString());
                            }
                        }
                    } catch (IOException e) {
                        Logger.e(e);
                    }
                }
            });
        }
        SocketThread.start();
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param bytes 要转换的字节数组
     * @return 转换后的结果
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
//            Log.i("HWL", "bytes[i] = "+bytes[i] +"  i = "+i);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public void closeSocket() {
        Logger.i(" closeSocket ");
        isOpen = false;
        try {
            inputStream.close();
        } catch (IOException e) {
            Logger.e(e);
        }
        SocketThread.interrupt();
        SocketThread = null;
    }

    //这是写了一监听器来监听接收数据
    public SerialPortUtils.OnDataReceiveListener onDataReceiveListener = null;
    public static interface OnDataReceiveListener {
        public void onDataReceive(byte[] buffer, int size);
    }
    public void setOnDataReceiveListener(SerialPortUtils.OnDataReceiveListener dataReceiveListener) {
        onDataReceiveListener = dataReceiveListener;
    }
}
