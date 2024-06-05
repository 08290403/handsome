package com.example.score;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class MyTask implements Runnable{

    private static final String TAG = "Rate";
    Handler handler;

    public MyTask(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run(){
        Log.i(TAG,"run:子线程run()......");

        //获取网络数据
        java.net.URL url=null;
        String html = "";
        Bundle retbundle = new Bundle();
        ArrayList<String> list = new ArrayList<String>();
        try{
//            url = new URL("https://www.boc.cn/sourcedb/whpj/index_1.html");
//            HttpURLConnection http = (HttpURLConnection)url.openConnection();
//            InputStream in = http.getInputStream();
//
//            html = inputStream2String(in);
//            Log.i(TAG,"run:html ="+html);
            Document doc = Jsoup.connect("https://wocha.cn/huilv/?jinri").get();
            Elements tables = doc.getElementsByTag("table");
            org.jsoup.nodes.Element table = tables.get(0);
            Elements rows=table.getElementsByTag("tr");
            rows.remove(0);
            for(org.jsoup.nodes.Element row : rows){
                Log.i(TAG,"run:row="+row);
                Elements tds = row.getElementsByTag("td");
                if (!tds.isEmpty()){
                    org.jsoup.nodes.Element td1 = tds.get(0);
                    if(tds.size()>=5) {
                        Element td2 = tds.get(4);

                        String str1 = td1.text().trim();
                        String str2 = td2.text().trim();

                        Log.i(TAG,"run:td1 = "+ str1 +"->" + str2);

                        //html方法会保留文本+HTML标签
                        html += (str1 +"->" + str2 +"\n");
                        list.add (str1 +"->" + str2);

                        //获取所需的汇率值
                        if (str1.equals("欧元")){
                            retbundle.putFloat("euro",Float.parseFloat(str2));
                        }
                        else if (str1.equals("美元")){
                            retbundle.putFloat("dollar",Float.parseFloat(str2));
                        }
                        else if (str1.equals("韩元")){
                            retbundle.putFloat("won",Float.parseFloat(str2));
                        }
                    }else html +=("此条为空");
                }


            }
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        Log.d(TAG, "run: MyTask bundle = "+retbundle);

        //发送消息
        retbundle.putStringArrayList("mylist",list);
        Message msg = handler.obtainMessage(5,retbundle);
        handler.sendMessage(msg);

    }
}
