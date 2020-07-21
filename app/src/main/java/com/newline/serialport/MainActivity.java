package com.newline.serialport;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.hht.serialport.R;
import com.newline.serialport.dao.SerialPortDAO;
import com.newline.serialport.utils.StartActivityManager;

public class MainActivity extends Activity {

    private Button hello;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hello = findViewById(R.id.hello);
        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SerialPortDAO.putKeycode(MainActivity.this,7);
            }
        });
        StartActivityManager.startSerialPort(this);

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("realmo","main keycode:"+keyCode);
        SerialPortDAO.putKeycode(MainActivity.this,keyCode);
        return super.onKeyUp(keyCode, event);
    }
}
