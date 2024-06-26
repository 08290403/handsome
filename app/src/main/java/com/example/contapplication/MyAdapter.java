package com.example.contapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends ArrayAdapter {
    public MyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<HashMap<String,String>> list) {
        super(context, resource, list);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Map<String, String> map = (Map<String, String>) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
        TextView price = (TextView) itemView.findViewById(R.id.priceTitle);

        ImageView image = (ImageView) itemView.findViewById(R.id.itemImage);
        String imageUrl = map.get("图片");
        Picasso.get().load(imageUrl).into(image);
        title.setText(map.get("菜品"));
        price.setText((map.get("热量")));

        return itemView;
    }
}

