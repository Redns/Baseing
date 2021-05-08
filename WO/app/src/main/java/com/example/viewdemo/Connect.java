package com.example.viewdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connect extends AppCompatActivity {

    private static String IP_address = null;
    private static int St_address = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_layout);
        getSupportActionBar().hide();

        Button re = (Button)findViewById(R.id.re);
        EditText IP = (EditText)findViewById(R.id.IP);
        EditText St = (EditText)findViewById(R.id.Socket);

        IP.setText("192.168.0.105");
        St.setText("8888");

        /**返回主界面*/
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IP_address = IP.getText().toString().trim();
                St_address = Integer.parseInt(St.getText().toString().trim());

                /**向MainActivity传递信息*/
                Intent intent = new Intent();
                intent.putExtra("IP_address", IP_address);
                intent.putExtra("St_address", St_address);
                setResult(0, intent);
                finish();
            }
        });
    }

}