package com.baway.day_history;

import android.app.Application;

import com.baway.day_history.dbmanager.PersonDao;
import com.baway.day_history.utils.LoOkHttpUtils;
import com.baway.day_history.utils.MyOkhttp;

import org.litepal.LitePal;

/**
 * 创建日期：2017/3/16 14:55
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public class App extends Application {

    public static LoOkHttpUtils loOkHttpUtils;
    public static MyOkhttp myOkhttp;

    public static PersonDao pdo;

    @Override
    public void onCreate() {
        super.onCreate();
             //初始化网络框架
              loOkHttpUtils=LoOkHttpUtils.getInstance();
              myOkhttp=MyOkhttp.getInstance();
           // 初始化数据库
             pdo = new PersonDao(this);
          //创建本地数据库
          LitePal.initialize(this);
           LitePal.getDatabase();



    }
}
