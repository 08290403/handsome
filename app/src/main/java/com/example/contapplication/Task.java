package com.example.contapplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Task extends ListActivity {
    private Handler handler;
    private static final String TAG = "YourClass";

    public void fetchFoodData() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 连接到网页并获取Document对象
                    Document doc = Jsoup.connect("https://www.boohee.com/food/group/1?page=2").get();

                    // 选择页面中所有的div元素
                    Elements allDivs = doc.select("div.widget-food-list > ul > li");

                    // 创建一个列表来存储结果
                    ArrayList<HashMap<String, String>> list = new ArrayList<>();

                    for (Element div : allDivs) {
                        // 创建一个HashMap来存储食物名称和每一百克所含热量
                        HashMap<String, String> map = new HashMap<>();

                        // 选择div中的a元素，获取食物名称
                        Element a = div.select("h4 > a").first();
                        if (a != null) {
                            map.put("食物名称", a.text());
                        }

                        // 选择div中的p元素，获取每一百克所含热量
                        Element p = div.select("p").first();
                        if (p != null) {
                            map.put("每一百克所含热量", p.text());
                        }

                        // 如果HashMap中有数据，则添加到列表中
                        if (!map.isEmpty()) {
                            list.add(map);
                        }
                    }

                    // 使用Handler发送消息，包含列表数据
                    Message msg = handler.obtainMessage(9, list);
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    Log.e(TAG, "MalformedURLException", e);
                } catch (IOException e) {
                    Log.e(TAG, "IOException", e);
                }
            }
        });
        t.start();
    }
}
