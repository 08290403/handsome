package com.example.contapplication;

import androidx.annotation.NonNull;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.SimpleAdapter;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;


public class MyListActivity extends ListActivity {
    public static final String TAG = "ListActivity2";
    private ArrayList<HashMap<String,String>> ListItem;
    private SimpleAdapter ListItemAdapter;
    private Handler handler;
    private Document doc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        setListAdapter(ListItemAdapter);

        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                if (msg.what == 9 ){
                    Log.i(TAG, "handleMessage: what= "+msg.what);
                    ListItem = (ArrayList<HashMap<String,String>>)msg.obj;
                    MyAdapter myAdapter = new MyAdapter(MyListActivity.this,R.layout.list_item,ListItem);
                    setListAdapter(myAdapter);
                }
                super.handleMessage(msg);
            }
        };
        final ArrayList<HashMap<String, String>> list = new ArrayList<>();
        Thread t = new Thread(() -> {
            try {
                doc = Jsoup.connect("https://www.boohee.com/food/group/1?page=2").get();
                        Elements allDivs = doc.getElementsByTag("div");

                        int startIdx = 27;
                        int endIdx = 38;

                        if (startIdx < allDivs.size() && endIdx < allDivs.size() && startIdx < endIdx) {
                            for (int i = startIdx; i <= endIdx; i++) {
                                Element div = allDivs.get(i);
                                Elements rows = div.getElementsByTag("a");
                                for (Element row : rows) {
                                    System.out.println(row.text());
                                }
                                Elements strs = div.getElementsByTag("p");
                                for (Element str : strs) {
                                    System.out.println(str.text());
                                }
                            }
                        } else {
                            System.out.println("指定的索引范围超出了div元素的数量。");
                        }
                for (Element div : allDivs) {
                    HashMap<String, String> map = new HashMap<>();
                    Elements as = div.getElementsByTag("a");
                    Elements ps = div.getElementsByTag("p");
                    if (!as.isEmpty()) {
                        map.put("食物名称", as.first().text());
                    }
                    if (!ps.isEmpty()) {
                        map.put("每一百克所含热量", ps.first().text());
                    }
                    list.add(map);
                }
                Message msg = handler.obtainMessage(9, list);
                handler.sendMessage(msg);
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException", e);
            } catch (IOException e) {
                Log.e(TAG, "IOException", e);
            }
        });
        t.start();
    }

    private void initListView(){
        ListItem = new ArrayList<HashMap<String,String>>();
        for(int i = 0;i < 10;i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("食物名称","菜品:  "+ i );
            map.put("热量","热量  "+i);
            ListItem.add(map);
        }
        ListItemAdapter = new SimpleAdapter(this,ListItem,
                R.layout.list_item,
                new String[]{"菜名","热量"},
                new int[]{R.id.itemTitle,R.id.priceTitle});

    }
}




