package com.baway.day_history.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 创建日期：2017/3/17 13:33
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public class MyOkhttp {


    private static MyOkhttp instance;
    private OkHttpClient client;
    private Gson gson;
    public MyCallback myCallback;
    private  static  final int SUCCESS=1;
    private  static  final int ERROR=2;


    private Handler handler =new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //将拿到的数据进行接口回调
            switch (msg.what){

                case SUCCESS:

                    myCallback.success(msg.obj);

                    break;

                case  ERROR:

                    myCallback.Failed(msg.obj);


                    break;
                    }

        }
    };


    private MyOkhttp(){

        //初始化okhttp,gson
        client=new OkHttpClient();
        gson=new Gson();

        //初始化log日志
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client =new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(interceptor)
                .build();
    }


     //对外公布一个方法，共别的调用实例对象。单例模式

    public static MyOkhttp getInstance(){
        //双重检索
        if(instance==null){
            synchronized (MyOkhttp.class){
                if(instance==null){
                    instance=new MyOkhttp();
                }
            }
        }
        return instance;
    }




          //此方法用来拼接字符参数
    private String makeGetUrl(String url, Map<String, Object> map) {
        StringBuffer sb =new StringBuffer();
        if(map!=null){
            for (String key :map.keySet()){
                sb.append(key);
                sb.append("=");
                sb.append(map.get(key));
                sb.append("&");
            }
            return url+"?"+sb.substring(0,sb.length()-1);
        }else {
            return url;
        }
    }

    //调用okhttp的get方法
    public  <T>  void getOkHttp(String url, Map<String,Object> map,Class<T> repType){

        String urlGet = makeGetUrl(url,map);

          Request request = getRequest(urlGet);

         Call_Enqueue(request,repType);

    }


     //okhttp接口回调的方法
    private void Call_Enqueue(Request request, final Class repType) {

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                 handler.sendMessage(GetSendMessage(ERROR,e.getMessage()));

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                  //将成功的json串进行解析，转换成对象，发送到handler主线程
                String json=response.body().string();
                 Object o= gson.fromJson(json,repType);
                handler.sendMessage(GetSendMessage(SUCCESS,o));
            }
        });


    }



    //调用此方法获取接口回调内容
  public void  SetNewCallBcak(MyCallback myCallback){

        this.myCallback=myCallback;

  }

    //调用Okhttp的Post方法
    public <T> void PostOkhttp(String url,Map<String,String> map,Class<T> Cls) {

        //得到 body
        FormBody.Builder builder = new FormBody.Builder();
        //给builder赋值
        builder=GetBuilder(map,builder);
          //获取request对象
        Request request = PostRequest(url,builder);
         //调用异步方法
        Call_Enqueue(request,Cls);

    }


    //Post请求参数拼接
    private FormBody.Builder GetBuilder(Map<String,String> map,FormBody.Builder builder) {

        //遍历
        Iterator<String> iterator = map.keySet().iterator();
        //加入到 builder 对象

        while (iterator.hasNext()) {

            String key = iterator.next();
            String values = map.get(key);
            //加入到 builder 里面
            builder.add(key, values);
        }

      return  builder;

    }


    //get请求返回Request
    private Request getRequest(String urlGet) {

        if(urlGet!=null)

         return  new Request.Builder()
                .url(urlGet)
                .build();
        else

          return null;

    }


    //get请求返回Request
    private Request PostRequest(String urlGet,FormBody.Builder builder) {

        if(urlGet!=null)

            return  new Request.Builder()
                    .url(urlGet)
                    .post(builder.build())
                    .build();
        else

            return null;

    }



    //处理发送请求
    private Message GetSendMessage(int What,Object Obj){

        Message msg=Message.obtain();
          msg.what=What;
          msg.obj=Obj;
      return  msg;

    }







}
