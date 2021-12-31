package com.example.viewdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/*
* Target: 安卓上位机，可与服务器建立TCP连接，显示数据波形
*         软件特性：
*                 1.可以自己设置服务器IP和端口号
*                 2.波形显示为Auto模式
*         通信协议：
*                 1.发送数据给上位机前需要先发送 "READY"
*                 2.数据包由203个数据组成，数据之间用空格隔开
*                 3.数据包具体内容为"Frq Vrms Vpp point1 point2 ... point200"
*                 4.Frq, Vrms, Vpp需要自带单位，如Hz, kHz, mV ...
* Author: Redns
* Date: 2021/5/8 23:26
* */
public class MainActivity extends AppCompatActivity {

    /**默认IP地址和端口号*/
    public static String IP_address = null;
    public static int St_address = 0;

    /**Handler状态*/
    public static final int REFRESH = 1;
    public static final int CONNECTED = 0;

    /**要绘制的数据*/
    public static float[] floats = new float[200];

    /**频率、有效值、峰峰值*/
    public static String frq, rms, pp;

    /**缓冲区大小，单位: 字节*/
    public static final int BUFF_SIZE = 2500;

    /**日志筛选*/
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**初始化图像*/
        DrawLineChart chart = findViewById(R.id.chart);
        chart.setMaxValue(10f);
        chart.setMinValue(-10f);
        chart.setNumberLine(5);

        /**初始化数据*/
        for (int i = 0; i < floats.length; i++) {
            floats[i] = 0;
        }
        chart.setValue(floats);
        getSupportActionBar().hide();

        /**跳转到设置界面*/
        Button set = (Button) findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this, Connect.class);
                startActivityForResult(intent, 1);
            }
        });

        /**点击进行连接和数据传输*/
        Button connect = (Button) findViewById(R.id.connect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**若未填写信息则弹出警告*/
                if(IP_address == null || St_address == 0){
                    Toast.makeText(MainActivity.this, "请先点击设置", Toast.LENGTH_SHORT).show();
                }

                else {
                    new ConnectThread().start();
                }
            }
        });
    }


    /**创建Handler用于更新曲线*/
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == REFRESH){
                /**更新曲线*/
                DrawLineChart chart = findViewById(R.id.chart);

                chart.setMinValue((-1) * getNum(pp));
                chart.setMaxValue(getNum(pp));
                chart.setNumberLine(5);
                chart.setValue(floats);
                chart.requestLayout();

                /**更新频率*/
                TextView Frq = (TextView)findViewById(R.id.freq);
                Frq.setText("频率:" + frq);

                /**更新有效值*/
                TextView Vrms = (TextView)findViewById(R.id.Vrms);
                Vrms.setText("有效值:" + rms);

                /**更新峰峰值*/
                TextView Vpp = (TextView)findViewById(R.id.Vpp);
                Vpp.setText("峰峰值:" + pp);
            }

            else if(msg.what == CONNECTED){
                Toast.makeText(MainActivity.this, "已连接", Toast.LENGTH_SHORT).show();
            }
        }
    };


    /**连接线程*/
    private class ConnectThread extends Thread{
        @Override
        public void run() {
            Message msg = new Message();

            try {
                Socket s = new Socket(IP_address, St_address);
                InputStream is = s.getInputStream();
                OutputStream os = s.getOutputStream();
                byte[] b = new byte[BUFF_SIZE];

                /**成功连接*/
                msg.what = CONNECTED;
                handler.sendMessage(msg);

                /**发送ID码验证身份*/
                os.write("Phone".getBytes());

                /**线程阻塞*/
                while (true){
                    int len;

                    /**收到READY信号后开始接收数据*/
                    len = is.read(b);
                    String message = new String(b, 0, len);

                    if(message.equals("READY")){
                        len = is.read(b);
                        String points = new String(b, 0, len);
                        Log.d(TAG, "Received Data:" + points);

                        /**解析数据并存入绘图数组中*/
                        String[] data = points.split(" ");

                        frq = data[0];
                        rms = data[1];
                        pp = data[2];
                        for(int i = 0; i < 200; i++){
                            floats[i] = Float.parseFloat(data[i + 3]);
                        }

                        /**更新UI*/
                        Message msg0 = new Message();
                        msg0.what = REFRESH;
                        handler.sendMessage(msg0);
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**接受设置界面传递的参数*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if(data != null){
                super.onActivityResult(requestCode, resultCode, data);

                /**获取IP地址和端口号*/
                if (requestCode == 1) {
                    IP_address = data.getStringExtra("IP_address");
                    St_address = data.getIntExtra("St_address", 0);
                }
            }

        }

    /**将字符串中的数字提取出来*/
    public float getNum(String str){
        int index = str.length();

        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) > '9'){
                index = i;
                break;
            }
        }

        return Float.parseFloat(str.substring(0, index));
    }
}