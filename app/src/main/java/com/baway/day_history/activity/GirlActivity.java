package com.baway.day_history.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baway.day_history.R;
import com.baway.day_history.utils.GlideImageLoader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.co.senab.photoview.PhotoView;

/**
 * 创建日期：2017/3/22 14:43
 * 创建者：胡阵涛
 * 创建类的作用：
 */

public class GirlActivity extends Activity implements View.OnClickListener {


    private String url;
    private ImageView imageView_girl;
    private ImageView callback;
    private PhotoView photoView;
    private String pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.girlactivity);

        url = getIntent().getStringExtra("icon");
        pos = getIntent().getStringExtra("pos");

        initView();

    }

    private void initView() {

        imageView_girl = (ImageView) findViewById(R.id.imageView_girl_activity);
        callback = (ImageView) findViewById(R.id.Callback);
        imageView_girl.setOnClickListener(this);
        callback.setOnClickListener(this);
        photoView = (PhotoView) findViewById(R.id.img);
        photoView.canZoom();
        photoView.setMaximumScale(5.0f);

        if(url !=null)
            GlideImageLoader.GetUlrLoader(GirlActivity.this, url, photoView);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.imageView_girl_activity:

              initMyDownload();
                break;
            case   R.id.Callback:
                this.finish();

                break;




        }



    }

    private void initMyDownload() {

       Bitmap bitmap= photoView.getVisibleRectangleBitmap();

        try {
               int state= saveFile(bitmap,pos+".png");
            if(state==1) {
                Toast.makeText(GirlActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }else {

                Toast.makeText(GirlActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**将bitmap转换成流保存到本地
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public int saveFile(Bitmap bm, String fileName)  {
        try {
        File directory = Environment.getExternalStorageDirectory();

        File file=new File(directory,fileName);
        if(!file.exists()) {

            file.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
      return 1;

        } catch (Exception e) {
                e.printStackTrace();

        }

        return 0;
    }








}
