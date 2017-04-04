package com.baway.day_history.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway.day_history.R;
import com.baway.day_history.bean.Persontos;
import com.baway.day_history.utils.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2017/3/29 15:19
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public class CollectAdapter extends BaseAdapter {

    private Context context;

    private List<Persontos> list=new ArrayList<Persontos>();

    public CollectAdapter(Context context) {
        this.context = context;
    }

    public  void getData(List<Persontos> list){
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        return list.size()!=0 ? list.size():0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View converview, ViewGroup viewGroup) {
        ViewHolder viewholder;
        if (converview == null){
            converview = View.inflate(context,R.layout.collectfragment,null);
            viewholder = new ViewHolder();
            viewholder.title = (TextView) converview.findViewById(R.id.collect_title);
            viewholder.time = (TextView) converview.findViewById(R.id.collect_time);
            viewholder.image = (ImageView) converview.findViewById(R.id.collect_pic);
            converview.setTag(viewholder);
        }else{
            viewholder = (ViewHolder) converview.getTag();
        }
        viewholder.title.setText(list.get(position).getTitle_name());
        viewholder.time.setText(list.get(position).getDataTime());
        GlideImageLoader.GetUlrLoader(context,list.get(position).getUrl(),viewholder.image);
        return converview;
         }



    public class ViewHolder{
        public TextView title;
        public TextView time;
        public ImageView image;
    }



}


