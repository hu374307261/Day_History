package com.baway.day_history.utils;

import android.content.Context;
import android.widget.ImageView;

import com.baway.day_history.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 创建日期：2017/3/16 13:14
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public class GlideImageLoader {



    public static void  GetUlrLoader(Context context, String url, ImageView imageView){

        Glide.with(context)
                .load(url)  //设置路径
                .override(400,320) //设置图片大小
  //              .listener(new GlideListener())//监听glide
                //设置图片缓存机制(是否进行内存缓存，sd卡缓存的四种模式,选择合适自己的模式进行缓存)
                //默认是有sd卡缓存的
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
               .thumbnail(0.3f)//设置加载时缩放图
 //               .dontAnimate()//不设置动画
                  .skipMemoryCache(false)  //设置跳过内存缓存
//                  .fitCenter()  //设置居中
                .centerCrop() //设置图片居中
  //              .animate(R.anim.silde_in_left) //设置动画效果
             .bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))//设置成圆形图
                .placeholder(R.drawable.default_img)  //设置占位符，默认图片
                .error(R.drawable.ic_history)  //加载失败的图片
                .into(imageView);  //设置图片

    }




//    //监听类
//   public static class  GlideListener implements RequestListener<String, GlideDrawable> {
//
//
//        @Override
//        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//            return false;
//        }
//
//        @Override
//        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//            return false;
//        }
//
//
//
//    }




}
