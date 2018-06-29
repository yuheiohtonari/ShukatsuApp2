package com.example.yuhei.shukatsuapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread t = new Thread(){
            public void run(){

                try{

                    sleep(2000);

                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);

                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
}
