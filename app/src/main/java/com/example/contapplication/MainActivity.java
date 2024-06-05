package com.example.contapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends android.app.Activity {
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show=findViewById(R.id.strong_show2);
    }

    public void click(View view) {
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
    }
}