package com.baway.day_history.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.day_history.R;
import com.baway.day_history.activity.DetailsActivity;
import com.baway.day_history.base.BaseFragment;
import com.baway.day_history.bean.Persontos;
import com.baway.day_history.utils.GlideImageLoader;
import com.baway.day_history.view.SlideCutListView;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 创建日期：2017/3/18 8:40
 * 创建者：胡阵涛
 * 创建类的作用：收藏
 */

public class CollectFragment extends BaseFragment implements SlideCutListView.RemoveListener {



    private SlideCutListView xRecyclerView;
  //  private CommonAdapter<Persontos> mAdapter;
    private List<Persontos> list;
    private MyAdapter collectAdapter;
    private List<Persontos> all;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.collect_fragment_layout,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

          initHeader();
          initWidget();
        setWidgetState();

    }

    @Override
    public void initHeader() {

       list = DataSupport.findAll(Persontos.class);



    }

    @Override
    public void initWidget() {

        xRecyclerView = (SlideCutListView) getActivity().findViewById(R.id.collect_recyclerView);

        xRecyclerView.setRemoveListener(this);

        collectAdapter = new MyAdapter();

        xRecyclerView.setAdapter(collectAdapter);



    }

    @Override
    public void setWidgetState() {

        xRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("_data",list.get(position).getDataTime());
                intent.putExtra("_id",list.get(position).getE_id());
                getActivity().startActivity(intent);




            }
        });




        //            mAdapter = new CommonAdapter<Persontos>(getActivity(), R.layout.collectfragment,list) {
//                  @Override
//                  protected void convert(ViewHolder holder, Persontos persontos, int position) {
//
//                      holder.setText(R.id.collect_time, persontos.getDataTime());
//                      holder.setText(R.id.collect_title, persontos.getTitle_name());
//                      ImageView pic = holder.getView(R.id.collect_pic);
//                      pic.setImageResource(R.drawable.ic_loading);
//                      String  url=persontos.getUrl();
//
//
//                       if(url.equals("")) {
//                           pic.setImageResource(R.drawable.ic_loading);
//                           Toast.makeText(getActivity(),"没有数据",Toast.LENGTH_SHORT).show();
//
//                       }else if (url==null){
//
//                           pic.setImageResource(R.drawable.ic_loading);
//                           Toast.makeText(getActivity(),"没有数据",Toast.LENGTH_SHORT).show();
//
//                       }else {
//
//                           GlideImageLoader.GetUlrLoader(getActivity(), persontos.getUrl(), pic);
//                       }
//
//                  }
//              };



    }


    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {

        all = DataSupport.findAll(Persontos.class);
        String pos = all.get(position).getE_id();
        list.remove(position);
        collectAdapter.notifyDataSetChanged();
        switch (direction){
            case RIGHT:
                DataSupport.deleteAll(Persontos.class,"e_id = ?",pos);
                Toast.makeText(getActivity(),"向右滑动删除",Toast.LENGTH_SHORT).show();
                break;
            case LEFT:
                DataSupport.deleteAll(Persontos.class,"e_id = ?",pos);
                Toast.makeText(getActivity(),"向左滑动删除",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size()!=0?list.size():0;
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View converview, ViewGroup viewGroup) {
           ViewHolder viewholder;
            if (converview == null) {
                converview = View.inflate(getActivity(), R.layout.collectfragment, null);
                viewholder = new ViewHolder();
                viewholder.title = (TextView) converview.findViewById(R.id.collect_title);
                viewholder.time = (TextView) converview.findViewById(R.id.collect_time);
                viewholder.image = (ImageView) converview.findViewById(R.id.collect_pic);
                converview.setTag(viewholder);
            } else {
                viewholder = (ViewHolder) converview.getTag();
            }
            viewholder.title.setText(list.get(position).getTitle_name());
            viewholder.time.setText(list.get(position).getDataTime());
            GlideImageLoader.GetUlrLoader(getActivity(), list.get(position).getUrl(), viewholder.image);
            return converview;
        }


     public    class ViewHolder {

            public TextView title;
            public TextView time;
            public ImageView image;
        }

    }

}
