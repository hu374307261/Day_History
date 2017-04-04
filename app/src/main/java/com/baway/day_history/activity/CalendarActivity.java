package com.baway.day_history.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.baway.day_history.R;
import com.baway.day_history.base.BaseActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.greenrobot.eventbus.EventBus;


/**
 * 创建日期：2017/3/25 18:32
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public class CalendarActivity extends BaseActivity {


    private MaterialCalendarView calendarView;
    private Button btn_ok;
    private CalendarDay mDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_calendar);

        initHeader();
        initWidget();
       setWidgetState();
    }

    @Override
    public void initHeader() {

        inittHeaderWidget();
       setTitle("选择日期");

      addImageLeft(R.drawable.ic_left);
    }

    @Override
    public void initWidget() {

        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(this);
        addImageLeftListener(this);

    }

    @Override
    public void setWidgetState() {

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                mDate=date;
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()){

            case  R.id.bar_back:

                  this.finish();

                break;


            case  R.id.btn_ok:
                EventBus.getDefault().post(mDate);
                finish();
                break;




        }


    }
}
