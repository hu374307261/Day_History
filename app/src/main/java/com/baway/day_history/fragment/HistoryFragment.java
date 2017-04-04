package com.baway.day_history.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baway.day_history.App;
import com.baway.day_history.R;
import com.baway.day_history.activity.CalendarActivity;
import com.baway.day_history.activity.DetailsActivity;
import com.baway.day_history.adapter.History_Adapter;
import com.baway.day_history.base.BaseFragment;
import com.baway.day_history.bean.HistoryBean;
import com.baway.day_history.utils.CircularAnimUtil;
import com.baway.day_history.utils.MyCallback;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 创建日期：2017/3/18 8:40
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public class HistoryFragment extends BaseFragment implements XRecyclerView.LoadingListener {


    private XRecyclerView recycler;
    private History_Adapter historyAdapter;
    private List<HistoryBean.ResultBean> result_list=new ArrayList<>();

    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private Calendar mCalendar;
    private TextView tv_time;
    private List<HistoryBean.ResultBean> mDatas = new ArrayList<>();
    private RelativeLayout rl_previous;
    private RelativeLayout rl_next;
    private FloatingActionButton floatActionBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.history_layout,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initWidget();
         initHeader();
         setWidgetState();

    }


    @Override
    public void onResume() {
        super.onResume();
        getTitleDateInterNet(10,10);

    }

    private void getTitleDateInterNet(int Month,int Day) {
             GetTitle();
           map.put("date",Month+"/"+Day);
        App.myOkhttp.getOkHttp("http://v.juhe.cn/todayOnhistory/queryEvent.php",map,HistoryBean.class);
        App.myOkhttp.SetNewCallBcak(new MyCallback() {
            @Override
            public void success(Object o) {


                HistoryBean historyBean= (HistoryBean) o;
                List<HistoryBean.ResultBean> result = historyBean.getResult();
                result_list=result;
                showContent(result);
            }

            @Override
            public void Failed(Object object) {

            }
        });



    }

    @Override
    public void initHeader() {

        init();
        initTime();

    }

    @Override
    public void initWidget() {

        rl_previous = (RelativeLayout) getActivity().findViewById(R.id.rl_previous);
        rl_next = (RelativeLayout) getActivity().findViewById(R.id.rl_next);
        tv_time = (TextView) getActivity().findViewById(R.id.tv_time);
        floatActionBtn = (FloatingActionButton) getActivity().findViewById(R.id.floatActionBtn);
        recycler = (XRecyclerView) getActivity().findViewById(R.id.recyclerView);
        rl_next.setOnClickListener(this);
        rl_previous.setOnClickListener(this);
        floatActionBtn.setOnClickListener(this);
        recycler.setLoadingListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        historyAdapter = new History_Adapter(getActivity());
        recycler.setAdapter(historyAdapter);

    }

    @Override
    public void setWidgetState() {

        //点击跳转详情
              historyAdapter.SetItemListener(new History_Adapter.onItemListener() {
                  @Override
                  public void ItemListener(View v, int position) {
                      Intent intent=new Intent(getActivity(), DetailsActivity.class);
                       intent.putExtra("_data",result_list.get(position).getDate());
                       intent.putExtra("_id",result_list.get(position).getE_id());
                       getActivity().startActivity(intent);
                  }
              });

    }

    @Override
    public void onRefresh() {

    HistoryStop();

    }

    @Override
    public void onLoadMore() {

        HistoryStop();
    }

//关闭刷新
    public  void  HistoryStop(){
        recycler.loadMoreComplete();
        recycler.refreshComplete();
    }



    private void initTime() {
        mCalendar = Calendar.getInstance();
        currentYear = mCalendar.get(Calendar.YEAR);
        currentMonth = mCalendar.get(Calendar.MONTH) + 1;
        currentDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        setTVTime();

    }

    //3.0版本
    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true, priority = 100)
    public void test(CalendarDay mDate) {
        onEventMainThread(mDate);
    }

    public void onEventMainThread(CalendarDay date) {
        currentYear=date.getYear();
        currentMonth=date.getMonth()+1;
        currentDay=date.getDay();
        setTVTime();
        getTitleDateInterNet(currentMonth,currentDay);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
    switch (v.getId()){

        case R.id.rl_previous:
            setPreVious();
            setTVTime();
            getTitleDateInterNet(currentMonth, currentDay);
            break;
        case R.id.rl_next:

            setNext();
            setTVTime();
            getTitleDateInterNet(currentMonth, currentDay);
            break;
        case R.id.floatActionBtn:
            Intent intent=new Intent(getActivity(),CalendarActivity.class);
            CircularAnimUtil.startActivityForResult(getActivity(),intent,200,v, R.color.colorPrimary);
            break;


     }


    }

    private void init() {
        EventBus.getDefault().register(this);
    }


    //上一个月
    private void setPreVious() {
        if (currentMonth == 3) {
            currentDay--;
            if (currentDay < 1) {
                if ((currentYear % 4 == 0 && currentYear % 100 != 0) || currentYear % 400 == 0) {
                    //闰年
                    currentDay = 29;
                    currentMonth--;
                } else {
                    //平年
                    currentDay = 28;
                    currentMonth--;
                }
            }
        } else if (currentMonth == 1) {
            currentDay--;
            if (currentDay < 1) {
                currentDay = 31;
                currentMonth = 12;
                currentYear--;
            }
        } else if (currentMonth == 2 || currentMonth == 4 || currentMonth == 6 || currentMonth == 8 || currentMonth == 9 || currentMonth == 11) {
            currentDay--;
            if (currentDay < 1) {
                currentDay = 31;
                currentMonth--;
            }

        } else {
            currentDay--;
            if (currentDay < 1) {
                currentDay = 30;
                currentMonth--;
            }
        }


    }

    //下一个月
    private void setNext() {

        if (currentMonth == 2) {
            //二月
            if ((currentYear % 4 == 0 && currentYear % 100 != 0) || currentYear % 400 == 0) {
                //闰年
                currentDay++;
                if (currentDay > 29) {
                    currentDay = 1;
                    currentMonth++;
                }

            } else {
                //平年
                currentDay++;
                if (currentDay > 28) {
                    currentDay = 1;
                    currentMonth++;
                }
            }

        } else if (currentMonth == 1 || currentMonth == 3 || currentMonth == 5 || currentMonth == 6 || currentMonth == 8 || currentMonth == 10) {
            //大月
            currentDay++;
            if (currentDay > 31) {
                currentDay = 1;
                currentMonth++;
            }


        } else if (currentMonth == 12) {
            currentDay++;
            if (currentDay > 31) {
                currentDay = 1;
                currentMonth = 1;
                currentYear++;
            }

        } else {
            //小月
            currentDay++;
            if (currentDay > 30) {
                currentDay = 1;
                currentMonth++;
            }
        }
    }

    public void setTVTime() {
        tv_time.setText(currentYear + "年" + currentMonth + "月" + currentDay + "日");
    }



    public void showContent(List<HistoryBean.ResultBean> result) {

        /**
         * 排序
         */
        Collections.sort(result, new Comparator<HistoryBean.ResultBean>() {

            @Override
            public int compare(HistoryBean.ResultBean o1, HistoryBean.ResultBean o2) {
                int eid1 = Integer.parseInt(o1.getE_id());
                int eid2 = Integer.parseInt(o2.getE_id());
                return eid2 - eid1;
            }
        });
         historyAdapter.getData(result);
        historyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

}
