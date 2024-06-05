//package com.example.contapplication;
//
//import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.os.RemoteException;
//import android.provider.DocumentsContract;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.PipedInputStream;
//import java.io.Reader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity implements Runnable {
//
//    private static final String TAG = "Net";
//    Handler handler;
//    TextView show;
//
////    public MyTask(Handler handler) {
////        this.handler = handler;
////    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        show = findViewById(R.id.net_show);
//        handler = new Handler(Looper.myLooper()) {
//            public void handleMessage(@NonNull Message msg) {
//                if (msg.what == 5) {
//                    String str = (String) msg.obj;
//                   Log.i(TAG, ".....");
//                   show.setText(str);
//                }
//                super.handleMessage(msg);
//            }
//        };
//    }
//
//    public void onClick(View btn) {
//        Log.i(TAG, "onCreate:start thread");
//        Thread t = new Thread(this);
//        t.start();
//    }
//
//
//    @Override
//    public void run() {
//        Log.i(TAG, "run:run()......");
//
//        URL url = null;
//        String html="";
//        Bundle retbundle=new Bundle();
//        ArrayList<String> list =new ArrayList<String>();
//        try{
//            //url=new URL("view-source:https://www.boohee.com/food/group/1?page=2")
//            //HttpURLConnection http=(HttpURLConnection) url.openConnection()
//            //InputStream in =http.getInputStream();
//
//            //html=inputStream2String(in);
//            //Log.i(TAG,"run:html="+html);
//            Document doc=Jsoup.parse("view-source:https://www.boohee.com/food/group/1?page=2").get();
//            Elements divs =doc.getElementsByTag("div");
//            doc.getElementsByClass("text-box");
//            divs.remove(0);
//            for(Element div: divs) {
//                Element img=div.getElementsByTag("img").first();
//                Elements str=div.getElementsByTag("a");
//                String imgsrc = img.attr("src");
//                for (Element img:imgs){
//                    Elements tds=div.getElementsByTag("href");
//                    Element td1=tds.first();
//                    Elements trs=div.getElementsByTag("p");
//                    Element tr1=tds.first();
//                    html +=(td1.text()+"=>"+tr1.text()+"\n");
//                }
//            }
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
////private String inputStream2String(InputStream inputStream)throws IOException {
////    final int bufferSize = 1024;
////    final char[] buffer = new char[bufferSize];
////    final StringBuilder out = new StringBuilder();
////    Reader in = new InputStreamReader(inputStream, "gb2312");
////    while (true) {
////        int rsz = in.read(buffer, 0, buffer.length);
////        if (rsz < 0)
////            break;
////        out.append(buffer, 0, rsz);
////    }
////    return out.toString();
//    retbundle.putStringArrayList("mylist",list);
//    Message msg=handler.obtainMessage(5,retbundle);
//    handler.sendMessage(msg);
//}
//}