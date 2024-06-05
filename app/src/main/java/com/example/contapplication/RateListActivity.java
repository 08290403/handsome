package com.example.contapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class RateListActivity extends ListActivity {
    public static final String TAG = "RateListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //数据
        String[] data = {"1","2","3"};
        List<String> list_data = new ArrayList<String>(100);
        for (int i = 1;i<29;i++){
            list_data.add("Item"+i);
        }

        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                if (msg.what == 5 ){
                    Bundle bundle = (Bundle) msg.obj;
                    ArrayList<String> retlist = bundle.getStringArrayList("mylist");
                    ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this, android.R.layout.simple_list_item_1,retlist);


                }
                super.handleMessage(msg);
            }
        };


        //适配器
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);

        //绑定
        setListAdapter(adapter);

        //开启线程获得数据
        Thread t = new Thread(new com.example.score.MyTask(handler));
        t.start();
        Log.i(TAG, "onCreate: 启动线程");


    }
}
