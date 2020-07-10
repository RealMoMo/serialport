package com.newline.serialport;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.hht.serialport.R;
import com.newline.serialport.model.SerialPortModel;
import com.newline.serialport.utils.StartActivityManager;
import com.newline.serialport.model.SerialPortModel;

import android_serialport_api.SerialPort;

public class MainActivity extends Activity {
    SerialPortUtils serialPortUtils = new SerialPortUtils();
    private byte[] mBuffer;
    private Handler handler = new Handler();
    private TextView hello;
    SerialPort serialPort;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hello = findViewById(R.id.hello);
        StartActivityManager.startSerialPort(this);
        finish();
        serialPort = serialPortUtils.openSerialPort();
        //串口数据监听事件
        serialPortUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] buffer, int size) {
                s = SerialPortUtils.bytesToHexString(buffer);
                Log.d("MainActivity", "进入数据监听事件中。。。" + s);
                //
                //在线程中直接操作UI会报异常：ViewRootImpl$CalledFromWrongThreadException
                //解决方法：handler
                //
                mBuffer = buffer;
                String partOffCodes = SerialPortUtils.bytesToHexString(mBuffer).toUpperCase();
                SerialPortModel serialPortModel = SerialPortModel.getSerialPortModelByControllingCode(partOffCodes, size);
                Log.i("Main"," serialPortModel = "+serialPortModel);
                if (serialPortModel != null) {
                    serialPortModel.action(MainActivity.this);

                    String code = serialPortModel.getReturnCode();

                    serialPortUtils.sendSerialPort(code);

                }
                handler.post(runnable);
            }
            //开线程更新UI
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    hello.setText("size："+ String.valueOf(mBuffer.length)+"数据监听："+ s);
                }
            };
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serialPortUtils.closeSerialPort();
    }
}
