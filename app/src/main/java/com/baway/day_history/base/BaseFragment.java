package com.baway.day_history.base;

import android.support.v4.app.Fragment;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建日期：2017/3/18 8:40
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener{


    //初始化头部
    public abstract void initHeader();

    //初始化控件
    public abstract void initWidget();

    //设置监听
    public abstract void setWidgetState();

    public Map<String,Object> map=new HashMap<String,Object>();

    public  void GetTitle(){

        map.put("key","69a7eeba7869f8bdcdee7b2bc3bb5aa2");
        map.put("v","1.0");

    }


    @Override
    public void onClick(View v) {

    }
}
