package com.baway.day_history.base;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway.day_history.R;

/**
 * 创建日期：2017/3/15 19:00
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {


    private ImageView bar_back;
    private TextView bar_title;

    //初始化头部
    public abstract void initHeader();

    //初始化控件
    public abstract void initWidget();

    //设置监听
    public abstract void setWidgetState();






    public void inittHeaderWidget() {

        bar_back = (ImageView) findViewById(R.id.bar_back);
        bar_title = (TextView) findViewById(R.id.bar_title);

    }


    public void addImageLeftListener(View.OnClickListener listener ){//设置左边显示为图片
          bar_back.setOnClickListener(listener);

    }

    public void addImageLeft(int view ){//设置左边图片
        bar_back.setImageResource(view);

    }
    public void setTitle(String titles) {//设置中间文字的内容

         bar_title.setText(titles);

    }


    @Override
    public void onClick(View v) {

    }



}
