package com.baway.day_history.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway.day_history.App;
import com.baway.day_history.R;
import com.baway.day_history.base.BaseActivity;
import com.baway.day_history.bean.ToDayDetailsBean;
import com.baway.day_history.bean.Persontos;
import com.baway.day_history.utils.GlideImageLoader;
import com.baway.day_history.utils.LoOkHttpUtils;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 创建日期：2017/3/25 16:50
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public class DetailsActivity extends BaseActivity {


    private String id;
    private Map<String,String> maps=new HashMap<String,String>();
    private ImageView iv_pic;
    private Toolbar toolbar;
    private TextView tv_content;
    private FloatingActionButton fab_like;
    private CollapsingToolbarLayout collToolBar;
    private List<ToDayDetailsBean.ResultBean> result=new ArrayList<ToDayDetailsBean.ResultBean>();
    private Persontos p;
    private ImageView left_image;
    private String data;
    private int position=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.history_layout_into);
        initHeader();
        initWidget();
        setWidgetState();


    }


    @Override
    protected void onResume() {
        super.onResume();
       GetTitle();

    }

    @Override
    public void initHeader() {
        id = getIntent().getStringExtra("_id");
        data = getIntent().getStringExtra("_data");

    }

    @Override
    public void initWidget() {


        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        left_image = (ImageView) findViewById(R.id.ic_left_btn);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_content = (TextView) findViewById(R.id.tv_content);
        fab_like = (FloatingActionButton) findViewById(R.id.fab_like);
        collToolBar = (CollapsingToolbarLayout) findViewById(R.id.collToolBar);
        left_image.setOnClickListener(this);

        List<Persontos> all =DataSupport.findAll(Persontos.class);
        for (Persontos ps:all) {
            if (ps.getE_id().equals(id)){
                fab_like.setSelected(true);
            }else{
                fab_like.setSelected(false);

            }
        }

        IsSelector();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()){

            case R.id.ic_left_btn:
                finish();
                break;


        }




    }

    private void IsSelector() {


        fab_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(position==0){
                    position=1;
                    fab_like.setSelected(true);
                    //保存数据
                    p = new Persontos();
                    p.setTitle_name(result.get(0).getTitle());
                    p.setContent_vt(result.get(0).getContent());
                    p.setE_id(result.get(0).getE_id());
                    if(result.get(0).getPicUrl().size()>0) {
                        p.setUrl(result.get(0).getPicUrl().get(0).getUrl());
                    }else {

                        p.setUrl("");
                    }
                    p.setDataTime(data);
                    p.save();

                }else {
                    position=0;
                    DataSupport.deleteAll(Persontos.class,"e_id = ?",result.get(0).getE_id());
                    fab_like.setSelected(false);


                }




            }
        });





    }

    @Override
    public void setWidgetState() {
        setToolbar();

    }

    private void setToolbar() {
        collToolBar.setExpandedTitleColor(Color.WHITE);
        collToolBar.setCollapsedTitleTextColor(Color.WHITE);

    }

   public  void  GetTitle(){

       maps.put("key","69a7eeba7869f8bdcdee7b2bc3bb5aa2");
       maps.put("e_id",id);
       App.loOkHttpUtils.get("http://v.juhe.cn/todayOnhistory/queryDetail.php?",maps, ToDayDetailsBean.class);
       App.loOkHttpUtils.setCallbackM(new LoOkHttpUtils.CallbackM() {
           @Override
           public void onFailure(Call request, IOException e) {

           }

           @Override
           public void onResponse(Object response) {


               ToDayDetailsBean detailsBean = (ToDayDetailsBean) response;

               if (detailsBean.getResult() != null) {
                   List<ToDayDetailsBean.ResultBean> resultBeen = detailsBean.getResult();
                   result=resultBeen;
                   if (resultBeen.size() != 0) {
                         //显示数据
                       collToolBar.setTitle(resultBeen.get(0).getTitle());
                       tv_content.setText(resultBeen.get(0).getContent());
                       List<ToDayDetailsBean.ResultBean.PicUrlBean> picUrl = resultBeen.get(0).getPicUrl();
                       if (picUrl.size() != 0) {
                           GlideImageLoader.GetUlrLoader(DetailsActivity.this, picUrl.get(0).getUrl(), iv_pic);
                       } else {
                           iv_pic.setImageResource(R.drawable.default_img);
                       }


                   }


               }
           }

       });



   }



}
