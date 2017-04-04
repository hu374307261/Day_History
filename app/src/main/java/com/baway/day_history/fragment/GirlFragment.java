package com.baway.day_history.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baway.day_history.App;
import com.baway.day_history.R;
import com.baway.day_history.adapter.GirleAdapter;
import com.baway.day_history.base.BaseFragment;
import com.baway.day_history.bean.GirleBean;
import com.baway.day_history.utils.LoOkHttpUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * 创建日期：2017/3/18 8:40
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public class GirlFragment extends BaseFragment implements XRecyclerView.LoadingListener {


    private XRecyclerView recycler;
    private  int num=1;
    private GirleAdapter girleAdapter;
    private FloatingActionButton floatActionBtn;
    private GridLayoutManager manager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.girle_layout,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

         initHeader();
         initWidget();
         setWidgetState();

    }


    @Override
    public void onResume() {
        super.onResume();

    getTitleDateInterNet();

    }

    private void getTitleDateInterNet() {

        String url="http://gank.io/api/data/福利/12/"+String.valueOf(num);

        App.loOkHttpUtils.get(url,null,GirleBean.class);
        App.loOkHttpUtils.setCallbackM(new LoOkHttpUtils.CallbackM() {
            @Override
            public void onFailure(Call request, IOException e) {

            }

            @Override
            public void onResponse(Object response) {

                GirleBean girleBean= (GirleBean) response;
                List<GirleBean.ResultsBean> result = girleBean.getResults();
                girleAdapter.getGirlData(result);


            }
        });

//        App.myOkhttp.getOkHttp(url,null, GirleBean.class);
//        App.myOkhttp.SetNewCallBcak(new MyCallback() {
//            @Override
//            public void success(Object o) {
//
//
//
//            }
//
//            @Override
//            public void Failed(Object object) {
//
//            }
//        });



    }

    @Override
    public void initHeader() {

    }

    @Override
    public void initWidget() {

        floatActionBtn = (FloatingActionButton) getActivity().findViewById(R.id.floatActionBtn_girl_layout);
        recycler = (XRecyclerView) getActivity().findViewById(R.id.x_recycler);
        recycler.setLoadingListener(this);
        floatActionBtn.setOnClickListener(this);
      //  recycler.addItemDecoration(new DividerItemDecoration(getActivity(), GridLayoutManager.VERTICAL));

        manager = new GridLayoutManager(getActivity(),2);
        recycler.setLayoutManager(manager);
        girleAdapter = new GirleAdapter(getActivity());
        recycler.setAdapter(girleAdapter);

    }

    @Override
    public void setWidgetState() {

    }

    @Override
    public void onRefresh() {
        num++;
        getTitleDateInterNet();
        GirlStop();

    }

    @Override
    public void onLoadMore() {

        num++;
        getTitleDateInterNet();
        GirlStop();
    }


    public  void  GirlStop(){
        recycler.loadMoreComplete();
        recycler.refreshComplete();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if(v.getId()==R.id.floatActionBtn_girl_layout){
            girleAdapter.notifyDataSetChanged();
            manager.scrollToPosition(0);


        }



    }
}
