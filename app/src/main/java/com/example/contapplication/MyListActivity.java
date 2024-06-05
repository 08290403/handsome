package com.example.contapplication;

import androidx.annotation.NonNull;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.example.contapplication.MyAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class MyListActivity extends ListActivity {
    public static final String TAG = "ListActivity2";
    private ArrayList<HashMap<String,String>> ListItem;
    private SimpleAdapter ListItemAdapter;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();

        setListAdapter(ListItemAdapter);

        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                if (msg.what == 9 ){
                    Log.i(TAG, "handleMessage: what= "+msg.what);
                    ListItem = (ArrayList<HashMap<String,String>>)msg.obj;
                    //ListItemAdapter = new SimpleAdapter(ListActivity2.this,ListItem,
                    //        R.layout.list_item,
                    //        new String[]{"ItemTitle","Price"},
                    //        new int[]{R.id.itemTitle,R.id.price});
                    MyAdapter myAdapter = new MyAdapter(MyListActivity.this,R.layout.list_item,ListItem);
                    setListAdapter(myAdapter);
                }
                super.handleMessage(msg);
            }
        };

        Thread t = new Thread(()->{
            ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
            try{

                //Element aa = doc.getElementsByTag("li class='history-urls'").first();
                //String regex = "\\.html$"; // 匹配以 http:// 或 https:// 开头的 URL
                //Pattern pattern = Pattern.compile(regex);
                ////Elements rows = aa.getElementsByTag("a href='"+pattern+"'");
                ////Elements rows = aa.getElementsByAttributeValueMatching("a href=", pattern);
                //Elements rows = aa.select("a[href$='.html']");
                //for(org.jsoup.nodes.Element row : rows){
                //        Log.i(TAG,"run:row="+row);
                //    System.out.println(row.attr("href"));
                //Elements tds = row.getElementsByTag("td");
                //if (!tds.isEmpty()){
                //    org.jsoup.nodes.Element td1 = tds.get(0);
                //    if(tds.size()>=5) {
                //        Element td2 = tds.get(4);
////
                //        String str1 = td1.text().trim();
                //        String str2 = td2.text().trim();
////
                //        Log.i(TAG,"run:td1 = "+ str1 +"->" + str2);
                //        HashMap<String,String> map = new HashMap<String,String>();
                //        map.put("ItemTitle","Rate:  "+ str1 );
                //        map.put("Price","detail  "+ str2);
                //        list.add(map);
////
                //    }
                //}
//
//
                //}
                Document doc = Jsoup.connect("view-source:https://www.boohee.com/food/group/1?page=2 ").get();
                Element table = doc.getElementsByTag("div").first();
                Elements rows = table.getElementsByTag("a");
                Elements strs = table.getElementsByTag("p");
                rows.remove(0);
                for(org.jsoup.nodes.Element row : rows){
                    Log.i(TAG,"run:row="+row);
                    if (!rows.isEmpty()&&!strs.isEmpty()){
                        org.jsoup.nodes.Element td1 = rows.get(0);
                        org.jsoup.nodes.Element td2 = strs.get(0);
                        if(rows.size()>=5) {
                            String str1 = td1.text().trim();
                            String str2 = td2.text().trim();
                            Log.i(TAG,"run:td1 = "+ str1 +"->" + str2);
                            HashMap<String,String> map = new HashMap<String,String>();
                            map.put("ItemTitle","Rate:  "+ str1 );
                            map.put("Price","detail  "+ str2);
                            list.add(map);
                        }

                    }
                }
            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            Message msg = handler.obtainMessage(9,list);
            handler.sendMessage(msg);

        });
        t.start();
    }

    private void initListView(){
        ListItem = new ArrayList<HashMap<String,String>>();
        for(int i = 0;i < 10;i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("ItemTitle","Rate:  "+ i );
            map.put("Price","detail  "+i);
            ListItem.add(map);
        }
        //生成适配器的Item和动态组对应的元素
        ListItemAdapter = new SimpleAdapter(this,ListItem,
                R.layout.list_item,
                new String[]{"ItemTitle","Price"},
                new int[]{R.id.itemTitle,R.id.priceTitle});

    }
}
//        try{
//            Document doc= Jsoup.parse("view-source:https://www.boohee.com/food/group/1?page=2").get();
//            Elements divs =doc.getElementsByTag("div");
//            doc.getElementsByClass("text-box");
//            divs.remove(0);
//            for(Element div: divs) {
//                Element img=div.getElementsByTag("img").first();
//                Elements str=div.getElementsByTag("a");
//                String imgsrc = img.attr("src");
//
//                }
//            }
//        catch (MalformedURLException e) {
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }


