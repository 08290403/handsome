package com.example.contapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ContActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cont);

        Intent intent = getIntent();
//        float result = intent.getFloatExtra("result", 0);
        float result1 = Float.parseFloat(gg.getResult());

        TextView priceView = findViewById(R.id.priceView);
        TextView textView3= findViewById(R.id.textView3);
        final EditText eat = findViewById(R.id.eat);
        final Button config = findViewById(R.id.config);


        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String priceTitle = intent.getStringExtra("priceTitle");
                Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
                Matcher matcher = pattern.matcher(priceTitle);

                if (matcher.find()) {
                    float number = Float.parseFloat(matcher.group());
                    String userInputText = eat.getText().toString();
                    if (TextUtils.isEmpty(userInputText)) {
                        priceView.setText("请输入数值");
                        return;
                    }
                    float userInput = Float.parseFloat(userInputText);
                    float result2 = (number * userInput) / 100;

                    priceView.setText("这一餐总共吃掉的热量为"+String.valueOf(result2)+"大卡");
//                    gg.getResult(String.valueOf(result));
                    Float result3 =result1 - result2;
                    Log.i("Calories", "Result: " + result1 + ", Result2: " + result2 + ", Result3: " + result3);
                    // 根据 result3 的值更新 TextView
                    if (result3 > 0) {
                        textView3.setText("你的热量余额还剩：" + String.valueOf(result3));
                    } else {
                        textView3.setText("您的热量余额不足");
                    }

                } else {
                    priceView.setText("未找到数字");
                }
            }
        });
    }
}