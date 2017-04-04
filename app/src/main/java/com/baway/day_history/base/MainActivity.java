package com.baway.day_history.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baway.day_history.R;
import com.baway.day_history.adapter.Left_Adapter;
import com.baway.day_history.bean.ShowFramgent;
import com.baway.day_history.fragment.AsRegardsFragment;
import com.baway.day_history.fragment.CollectFragment;
import com.baway.day_history.fragment.GirlFragment;
import com.baway.day_history.fragment.HistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    //用来添加fragment  的list
    List<ShowFramgent> fragmentList = new ArrayList<ShowFramgent>();
    //当前应该加载的fragment
    int current;
    private HistoryFragment historyFragment;
    private CollectFragment collectFragment;
    private GirlFragment girlFragment;
    private AsRegardsFragment asRegardsFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Left_Adapter adapter;
    private DrawerLayout drawerLayout;
    private ListView drawerlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initHeader();
        initWidget();
        setWidgetState();


    }

    private void InitView() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerlist = (ListView) findViewById(R.id.left_drawer);
        historyFragment = new HistoryFragment();
        collectFragment = new CollectFragment();
        girlFragment = new GirlFragment();
        asRegardsFragment = new AsRegardsFragment();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        adapter=new Left_Adapter(MainActivity.this);

        drawerlist.setAdapter(adapter);

        drawerLayout.closeDrawer(Gravity.LEFT);


    }


    public void addlist() {

        for (int i = 0; i < 4; i++) {

            ShowFramgent fragment = new ShowFramgent();

            switch (i) {
                case 0:
                    fragment.fragment = historyFragment;
                    break;
                case 1:
                    fragment.fragment = girlFragment;
                    break;
                case 2:
                    fragment.fragment = collectFragment;
                    break;
                case 3:
                    fragment.fragment = asRegardsFragment;
                    break;

            }

            fragmentList.add(fragment);

        }
    }
    //将 fragment 加载进来
    public void addFragment(int position) {

        transaction = manager.beginTransaction();
        //将fragment 加载进来
        for (int i = 0; i < fragmentList.size(); i++) {

            if (i != position) {
                transaction.hide(fragmentList.get(i).fragment);
            }
        }

        if (fragmentList.get(position).statue == 0) {
            transaction.add(R.id.frame_layout_one, fragmentList.get(position).fragment, position + "");
            fragmentList.get(position).statue = 1;
            transaction.show(fragmentList.get(position).fragment);
        } else {
            transaction.show(fragmentList.get(position).fragment);
        }

        transaction.commit();

    }


    @Override
    public void initHeader() {
        inittHeaderWidget();
        setTitle("历史上的今天");

    }


    @Override
    public void initWidget() {
        InitView();
        addImageLeftListener(this);
        addlist();
        //三木运算
         addFragment(current == 0? 0 : current);
      drawerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             switch (position){

                 case 0:
                     addFragment(0);
                     setTitle("历史上的今天");
                     break;
                 case 1:
                     addFragment(1);
                     setTitle("靓妹");
                     break;
                 case 2:
                     addFragment(2);
                     setTitle("收藏");
                     break;
                 case 3:
                     addFragment(3);
                     setTitle("关于");

                     break;



             }
         }
     });

    }

    @Override
    public void setWidgetState() {


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
      switch (v.getId()){

          case R.id.bar_back:

           drawerLayout.openDrawer(GravityCompat.START);

              break;


      }



    }

        @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            DiaLogShow();
            return true;
        }
        return true;


    }







    private void DiaLogShow() {
        // 创建退出对话框
        AlertDialog isExit = new AlertDialog.Builder(this).create();
        // 设置对话框标题
        isExit.setTitle("系统提示");
        // 设置对话框消息
        isExit.setMessage("确定要退出吗");
        // 添加选择按钮并注册监听
        isExit.setButton("确定", listener);
        isExit.setButton2("取消", listener);
        // 显示对话框
        isExit.show();

    }


    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

}
