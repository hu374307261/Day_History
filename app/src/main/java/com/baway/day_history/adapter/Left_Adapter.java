package com.baway.day_history.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway.day_history.R;

/**
 * 创建日期：2017/3/21 21:03
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public class Left_Adapter extends BaseAdapter {

   private int []pic={R.drawable.ic_history,R.drawable.icon_gril,R.drawable.ic_like,R.drawable.ic_about};
   private String []name={"历史上的今天","靓妹","收藏","关于"};

    private Context context;

    public Left_Adapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return pic.length;
    }

    @Override
    public Object getItem(int position) {
        return pic[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;
        if (convertView==null) {

            holder=new ViewHolder();
            convertView= View.inflate(context, R.layout.left_items, null);

             holder.pic= (ImageView) convertView.findViewById(R.id.pic_left_items);
            holder.title= (TextView) convertView.findViewById(R.id.title_left_items);
          convertView.setTag(holder);

        }else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.pic.setImageResource(pic[position]);
        holder.title.setText(name[position]);

        return convertView;
    }


    class ViewHolder{

        ImageView pic;
        TextView title;



    }


}
