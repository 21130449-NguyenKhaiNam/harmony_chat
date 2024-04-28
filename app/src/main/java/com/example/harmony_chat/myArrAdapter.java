package com.example.harmony_chat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class myArrAdapter extends ArrayAdapter<setting> {
    Activity context;
    int id_layout;
    ArrayList<setting> myList;

    public myArrAdapter(  Activity context, int id_layout, ArrayList<setting> myList) {
        super(context, id_layout,myList);
        this.context = context;
        this.id_layout = id_layout;
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myflater=context.getLayoutInflater();
        convertView=myflater.inflate(id_layout,null);
        setting my_setting=myList.get(position);
        ImageView ic_setting=convertView.findViewById(R.id.icon);
        ic_setting.setImageResource(my_setting.getIcon());
        TextView text=convertView.findViewById(R.id.name);
        text.setText(my_setting.getName());
        TextView amount=convertView.findViewById(R.id.amount);
        amount.setText(my_setting.getThong_so());
        ImageView ic_greater_than=convertView.findViewById(R.id.greater_than);
        ic_greater_than.setImageResource(my_setting.getIc_greater());
return convertView;
    }
}