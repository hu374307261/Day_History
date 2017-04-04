package com.baway.day_history.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baway.day_history.R;
import com.baway.day_history.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * 创建日期：2017/3/18 8:40
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public class AsRegardsFragment extends BaseFragment implements XRecyclerView.LoadingListener {




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.asregards_fragment_layout,container,false);
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




    }

    @Override
    public void initHeader() {

    }

    @Override
    public void initWidget() {

    }

    @Override
    public void setWidgetState() {

    }

    @Override
    public void onRefresh() {


    }

    @Override
    public void onLoadMore() {

    }


}
