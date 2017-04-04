package com.baway.day_history.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baway.day_history.R;
import com.baway.day_history.bean.HistoryBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 创建日期：2017/3/16 20:55
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public class History_Adapter extends RecyclerView.Adapter<History_Adapter.ViewHolder> {

     private Context context;
     private List<HistoryBean.ResultBean> result=new ArrayList<>();

    onItemListener itemListener;

     public  interface  onItemListener{

         void  ItemListener(View v,int position);

     }





    public History_Adapter(Context context) {
        this.context = context;
    }


    public  void getData(List<HistoryBean.ResultBean> result){
         this.result.clear();
         this.result.addAll(result);
         this.notifyDataSetChanged();


    }



    @Override
    public History_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       ViewHolder holder=new ViewHolder(LayoutInflater.from(
               context).inflate(R.layout.history_items, parent,
               false));

        return holder;
    }


    public  void SetItemListener(onItemListener itemListener){

           this.itemListener=itemListener;

    }

    @Override
    public void onBindViewHolder(History_Adapter.ViewHolder holder, final int position) {

        HistoryBean.ResultBean bean = result.get(position);

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                itemListener.ItemListener(v,position);

             }
         });

            holder.tv_content.setText(bean.getTitle());
            holder.tv_time.setText(bean.getDate());

    }

    @Override
    public int getItemCount() {

        return result.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_content;
        private final TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);


        }
    }



}
