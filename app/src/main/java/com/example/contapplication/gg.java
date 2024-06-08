package com.example.contapplication;
// 导入语句
        import android.app.Application;

// 公共类声明
public class gg extends Application {

    private  static String ggresult;
    // 类构造函数
    public void onCreate() {
        super.onCreate();
    }

    // 类方法
    public static void setResult(String result) {
        ggresult=result;
    }

    // 主方法，应用程序入口点
    public static String getResult() {
        return ggresult;
    }
}