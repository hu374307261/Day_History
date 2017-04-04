package com.baway.day_history.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.baway.day_history.R;
import com.baway.day_history.base.BaseActivity;
import com.baway.day_history.base.MainActivity;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 创建日期：2017/3/24 13:03
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public class WelcomeActivity extends BaseActivity {

    private Timer timer;
     Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            int num= (int) msg.obj;
             if(num>2){

                 Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
                 startActivity(intent);
                 finish();
                timer.cancel();

             }
        }

    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        initHeader();
        initWidget();
        setWidgetState();


    }

    @Override
    public void initHeader() {



         timer=new Timer();

        timer.schedule(new TimerTask() {


          int currentIndex = 0;

            @Override
            public void run() {
                currentIndex++;
                Message message =Message.obtain();
                message.obj = currentIndex;
                handler.sendMessage(message);
            }
        }, 0, 1000);

    }

    @Override
    public void initWidget() {

    }

    @Override
    public void setWidgetState() {

    }
}
