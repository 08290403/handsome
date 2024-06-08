package com.example.contapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show = findViewById(R.id.strong_show2);

        Button btnGoToListActivity = findViewById(R.id.menu);
        btnGoToListActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyListActivity.class);
                startActivity(intent);
            }
        });

        // 为点击事件设置监听器
        Button btnCalculate = findViewById(R.id.button);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText maleinput =findViewById(R.id.male);
            String maleshow=maleinput.getText().toString();
            EditText yearinput =findViewById(R.id.year);
            String yearshow=yearinput.getText().toString();
            float year=Float.parseFloat(yearshow);
            EditText heightinput=findViewById(R.id.height);
            String heightshow=heightinput.getText().toString();
            float height=Float.parseFloat(heightshow);
            EditText weightinput=findViewById(R.id.weight);
            String weightshow=weightinput.getText().toString();
            float weight=Float.parseFloat(weightshow);
            EditText stronginput=findViewById(R.id.strong);
            String strongshow=stronginput.getText().toString();
            float strong=Float.parseFloat(strongshow);
            float result=0.0f;
        if (maleshow.equals("man")) {
                result = (float) (weight * 10 + height * 6.25 - year * 5 + 5);
            }
        if(maleshow.equals("woman")){
                result = (float) (weight * 10 + height * 6.25 - year * 5 -161);
            }
            result=result*strong;
        show.setText(String.valueOf(result));
                Intent intent = new Intent(MainActivity.this, ContActivity.class);
                intent.putExtra("result", result);
                gg.setResult(String.valueOf(result));
            }
        });
    }
}
//            EditText maleinput =findViewById(R.id.male);
//            String maleshow=maleinput.getText().toString();
//            EditText yearinput =findViewById(R.id.year);
//            String yearshow=yearinput.getText().toString();
//            float year=Float.parseFloat(yearshow);
//            EditText heightinput=findViewById(R.id.height);
//            String heightshow=heightinput.getText().toString();
//            float height=Float.parseFloat(heightshow);
//            EditText weightinput=findViewById(R.id.weight);
//            String weightshow=weightinput.getText().toString();
//            float weight=Float.parseFloat(weightshow);
//            EditText stronginput=findViewById(R.id.strong);
//            String strongshow=stronginput.getText().toString();
//            float strong=Float.parseFloat(strongshow);
//            float result=0.0f;
//        if (maleshow.equals("man")) {
//                result = (float) (weight * 10 + height * 6.25 - year * 5 + 5);
//            }
//        if(maleshow.equals("woman")){
//                result = (float) (weight * 10 + height * 6.25 - year * 5 -161);
//            }
//            result=result*strong;
//        show.setText(String.valueOf(result));