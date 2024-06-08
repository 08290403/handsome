package com.example.contapplication;

import androidx.annotation.NonNull;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;


public class MyListActivity extends ListActivity implements AdapterView.OnItemClickListener {
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
        getListView().setOnItemClickListener(this);
        final ArrayList<HashMap<String, String>> list = new ArrayList<>();
        Thread t = new Thread(() -> {
            try {
                doc = Jsoup.connect("https://www.boohee.com/food/group/1?page=2").get();
                        Elements allDivs = doc.getElementsByTag("div");

                for (Element div : allDivs) {
                    if (div.select("p").size() > 0) {
                        HashMap<String, String> map = new HashMap<>();
                        Element a = div.select("a").first();
                        Element img = div.select("img").first();
                        if (a != null) {
                            map.put("菜品", a.text());
                        }

                        Element p = div.select("p").first();
                        if (p != null) {
                            map.put("热量", p.text());
                        }
                        if (img != null) {
                            String imageUrl = img.absUrl("src"); // 获取图片的绝对URL
                            map.put("图片URL", imageUrl);
                        }
                        if (!map.isEmpty()) {
                            list.add(map);
                        }


                    }
                }
                if (list.size() > 7) {
                    // 删除前N个元素
                    for (int i = 0; i < 7 && i < list.size(); i++) {
                        list.remove(0);
                    }
                } else {
                    // 如果列表中的元素少于N，清空列表
                    list.clear();
                }
                if (list.size() > 1) {
                    // 删除倒数第二个元素（索引为 list.size() - 2）
                    list.remove(list.size() - 2);

                    // 删除最后一个元素（索引为 list.size() - 1）
                    list.remove(list.size() - 1);
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
            map.put("菜品","菜品:  "+ i );
            map.put("热量","热量  "+i);
            map.put("图片","图片  "+i);
            ListItem.add(map);
        }
        ListItemAdapter = new SimpleAdapter(this,ListItem,
                R.layout.list_item,
                new String[]{"菜品","热量","图片"},
                new int[]{R.id.itemTitle,R.id.priceTitle,R.id.itemImage});

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, String> clickedItem = ListItem.get(position);
            String priceTitle = clickedItem.get("热量");
            Intent intent = new Intent(MyListActivity.this, ContActivity.class);
            intent.putExtra("priceTitle", priceTitle);
             startActivity(intent);
    }
}




