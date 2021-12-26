package com.example.mywork1_lda.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mywork1_lda.R;
import com.example.mywork1_lda.other.BingPaperCrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.ResponseBody;

public class BingPaperActivity extends AppCompatActivity {
    private TextView desc;
    private ImageView photo;
    private Button nextPhoto;
    private Button formerPhoto;
    private Button download;
    private BingPaperCrawler bingPaperCrawler;
    public Handler mHandler;
    class Mhandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d("http", "handleMessage: 收到消息");
           switch (msg.what){
               // 1是下载图片;2是展示图片
               case 1:
                   String storePath = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"Pictures";
                   File dir=new File(storePath);
                   if(!dir.exists())
                       dir.mkdir();
                   String fileName = System.currentTimeMillis()+".jpg";
                   File file = new File(dir,fileName);
                   try {
                       file.createNewFile();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   InputStream in = ((ResponseBody)(((ArrayList)msg.obj).get(1))).byteStream();
                   long sum=0;
                   try {
                       FileOutputStream fos = new FileOutputStream(file);
                       byte[] buf = new byte[1024*8];
                       int len=0;
                       while((len=in.read(buf))!=-1)
                           fos.write(buf,0,len);
                       fos.flush();
                       fos.close();
                       in.close();
                   } catch (FileNotFoundException e) {
                       e.printStackTrace();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   Uri uri = Uri.fromFile(file);
                   getApplication().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                   break;
               case 2:
                   // 简介
                   desc.setText(((ArrayList)msg.obj).get(0).toString());
                   byte[] Picture=null;
                   try {
                       Picture = ((ResponseBody)(((ArrayList)msg.obj).get(1))).bytes();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   Bitmap bitmap = BitmapFactory.decodeByteArray(Picture,0, Picture.length);
                   photo.setImageBitmap(bitmap);
                   break;
           }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setContentView(R.layout.activity_bing_paper);

        desc = findViewById(R.id.paperTitleText);
        photo = findViewById(R.id.bingPaperImage);
        nextPhoto = findViewById(R.id.nextPageButton);
        formerPhoto = findViewById(R.id.formerPageButton);
        download = findViewById(R.id.downloadButton);
        bingPaperCrawler = new BingPaperCrawler();

        mHandler = new Mhandler();

        // 显示第一张图片
        bingPaperCrawler.showOrDownPhoto(0,mHandler,2);

        nextPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bingPaperCrawler.showOrDownPhoto(1,mHandler,2);
            }
        });

        formerPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bingPaperCrawler.showOrDownPhoto(-1,mHandler,2);
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bingPaperCrawler.showOrDownPhoto(0,mHandler,1);
            }
        });

    }
}