package com.baway.day_history.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baway.day_history.R;
import com.baway.day_history.activity.GirlActivity;
import com.baway.day_history.bean.GirleBean;
import com.baway.day_history.utils.GlideImageLoader;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2017/3/19 19:34
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public class GirleAdapter extends XRecyclerView.Adapter<GirleAdapter.GirlViewHolder>{

    private Context context;
    private List<GirleBean.ResultsBean> list=new ArrayList<GirleBean.ResultsBean>();


    public GirleAdapter(Context context) {
        this.context = context;

    }

    public  void getGirlData(List<GirleBean.ResultsBean> result){

        this.list.addAll(result);
        this.notifyDataSetChanged();


    }

    @Override
    public GirleAdapter.GirlViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        GirlViewHolder holder=new GirlViewHolder(LayoutInflater.from(
                context).inflate(R.layout.girle_itmes, parent,
                false));



        return holder;
    }

    @Override
    public void onBindViewHolder(final GirlViewHolder holder, final int position) {

     //    GirleBean.ResultsBean resultsBean = list.get(position);

      GlideImageLoader.GetUlrLoader(context,list.get(position).getUrl(),holder.icon);

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //点击图片跳转
                 Intent intent=new Intent(context, GirlActivity.class);
                 intent.putExtra("pos",position+"");
                 intent.putExtra("icon",list.get(position).getUrl());
                 context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


   class  GirlViewHolder extends  RecyclerView.ViewHolder{


       private final ImageView icon;

       public GirlViewHolder(View itemView) {
           super(itemView);

           icon = (ImageView) itemView.findViewById(R.id.icon_Girl);


       }
   }




}
