package com.baway.day_history.utils;


import android.os.Handler;
import android.os.Looper;

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

/**
 * 创建日期：2017/1/9 16:09
 * 创建者：huzhentao
 * 创建类的作用：
 */
public class LoOkHttpUtils {


    //声明对象
    CallbackM callbackM;


    //声明对象 私有化
    private static  LoOkHttpUtils LoOkHttpUtils;

    //获取主线程 handler
    private Handler handler;
    //解析对象
    private Gson gson;

    //构造函数 私有化
    private LoOkHttpUtils() {

        handler = new Handler(Looper.getMainLooper());
        gson = new Gson();

    }

    // 提供公共方法
    public static LoOkHttpUtils getInstance() {

        if (null == LoOkHttpUtils) {

            synchronized (LoOkHttpUtils.class) {

                if (null == LoOkHttpUtils) {

                    LoOkHttpUtils = new LoOkHttpUtils();
                }
            }
        }

        return LoOkHttpUtils;
    }


    /*
    * 拼接 url的方法
    *
    */

    public <T> void setUrl(String url,Map<String,String> map,Class<T> Cls,Methods methods){


        switch (methods){

            case GET: //get 方法

                get(url,map,Cls);

                break;
            case PSOT: // post方法

                Post(url,map,Cls);

                break;

        }


    }


    /*
    * get 方法
    */

    public <T> void get(String url,Map<String,String> map,Class<T> Cls){

        int i=0;

        if(map!=null) {
            // 遍历
            Iterator<String> iterator = map.keySet().iterator();

            while (iterator.hasNext()) {

                String key = iterator.next();
                String value = map.get(key);


                if (i == 0) {

                    url += key + "=" + value;

                } else {

                    url += "&" + key + "=" + value;
                }

                i++;

            }


        }
        geturl(url,Cls);

    }


    // get  请求
    public <T> void geturl(String url, final Class<T> Cls) {

        //创建okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //创建一个Request

        final Request request = new Request.Builder()
                .url(url)
                .build();

        ////new call
        Call call = okHttpClient.newCall(request);

        //回调接口

        call.enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

                if (null != callbackM) {
                    callbackM.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                getJSon(json, Cls);

            }


        });


    }


    /*
    * GSON 解析
    *
    * */

    public <T> void getJSon(String json, Class<T> Cls) {

        T t1 = gson.fromJson(json, Cls);

        MainThread(t1);

    }


    public void MainThread(final Object response) {

        handler.post(new Runnable() {
            @Override
            public void run() {

                if (null != callbackM) {
                    callbackM.onResponse(response);
                }
            }
        });
    }


    /*
    *  post 方法
    *  url 请求网络的地址
    *  map post 请求的参数
    *
    */

    public <T> void Post(String url, Map<String,String> map, final Class<T> Cls){


        //得到 client 对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //得到 body
        FormBody.Builder builder = new FormBody.Builder();
        //遍历
        Iterator<String> iterator = map.keySet().iterator();
        //加入到 builder 对象

        while (iterator.hasNext()){

            String key = iterator.next();
            String values = map.get(key);

            //加入到 builder 里面
            builder.add(key,values);

        }

        // 得到 request

        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        //得到 call

        Call call = okHttpClient.newCall(request);


        //接口回调

        call.enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {
                if (null != callbackM) {
                    callbackM.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                getJSon(json, Cls);
            }

        });


    }


    //设置监听的方法

    public void setCallbackM(CallbackM callbackM) {
        this.callbackM = callbackM;
    }


    //观察者模式
    public interface CallbackM {

        public void onFailure(Call  request, IOException e);

        public void onResponse(Object response);
    }



    /*
    *  定义枚举类型
    *  GET PSOT DOWN
    *  关键字段 enum
    *
    */

    public enum Methods{

        GET,PSOT,DOWN
    }



}
