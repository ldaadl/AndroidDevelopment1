package com.example.mywork1_lda.other;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BingPaperCrawler {
    private String site = "https://bing.ioliu.cn";
    // 页面查询参数
    private String siteQuery = "/?p=";
    // 哪一页
    private int whichPage = 1;

    private ArrayList<ArrayList<String>> linkAndDesc = new ArrayList<ArrayList<String>>();
    // 指向当前图片
    private int where = 0;

    // 信号量
    static Semaphore semaphore;


    private void getPhoto(Handler mHandler, int downloadOrGet){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList mess = new ArrayList();
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mess.add(linkAndDesc.get(where).get(0));
                semaphore.release();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(site+linkAndDesc.get(where).get(1))
                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)Chrome/92.0.4515.131 Safari/537.36 Edg/92.0.902.73")
                        .addHeader("Referer","https://cn.bing.com/")
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    // 使用handler通信机制，将获取的壁纸和简介发往主线程处理
                    mess.add(response.body());
                    Message msg = Message.obtain();
                    msg.what=downloadOrGet;
                    msg.obj=mess;
                    mHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    // 获取bing壁纸站点主页，抓取每一张壁纸下载练链接和描述
    private void getPhotoLinkAndDesc(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                // 注意添加请求头部，防止反爬虫
                Request request = new Request.Builder()
                        .url(site+siteQuery+whichPage++)
                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:95.0) Gecko/20100101 Firefox/95.0")
                        .addHeader("Referer","https://cn.bing.com/")
                        .addHeader("cookie","Hm_lvt_667639aad0d4654c92786a241a486361=1639721214,1639721217,1639721223,1639891131; _ga=GA1.2.2052079203.1639652821; _gid=GA1.2.1769178606.1639891131; _gat_gtag_UA_61934506_5=1; Hm_lpvt_667639aad0d4654c92786a241a486361=1639891136; likes=")
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    // 将获取的HTML文档进行解析
                    Document document = Jsoup.parse(response.body().string());
                    Elements items = document.select(".item");
                    String link;
                    String desc;
                    for(Element item:items){
                        // 解析得到链接和简介
                        link = item.select(".ctrl.download").attr("href");
                        desc = item.select("div.description h3").text();
                        ArrayList<String> tmp = new ArrayList<String>();
                        tmp.add(desc);
                        tmp.add(link);
                        linkAndDesc.add(tmp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                semaphore.release();
            }
        }).start();
    }


    public void showOrDownPhoto(int nextOrFormer,Handler handler,int down)
    {
        where+=nextOrFormer;
        if(where<0)
           where=0;
        // 当where大于当前已有链接列表时，说明我们需要请求一个新的页面
        if(where>=linkAndDesc.size()){
            // 重新初始化信号量，初值为0；在请求主页的线程中release它，请求具体壁纸的线程中先acquire在release，这保证了两个线程之间正确的同步
            semaphore = new Semaphore(0);
            getPhotoLinkAndDesc();
        }
        getPhoto(handler,down);
    }
}
