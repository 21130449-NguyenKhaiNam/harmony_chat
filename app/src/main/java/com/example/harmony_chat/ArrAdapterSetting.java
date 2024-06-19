package com.example.harmony_chat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.harmony_chat.model.Setting;

import java.util.ArrayList;

public class ArrAdapterSetting extends ArrayAdapter<Setting> {
    Activity context;
    int id_layout;
    ArrayList<Setting> myList;

    public ArrAdapterSetting(Activity context, int id_layout, ArrayList<Setting> myList) {
        super(context, id_layout,myList);
        this.context = context;
        this.id_layout = id_layout;
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(id_layout, parent, false);
        Setting currentSetting = myList.get(position);

        ImageView ic_setting = convertView.findViewById(R.id.icon);
        ic_setting.setImageResource(currentSetting.getIcon());
        TextView text = convertView.findViewById(R.id.name);
        text.setText(currentSetting.getName());
        TextView amount = convertView.findViewById(R.id.amount);
        amount.setText(currentSetting.getThong_so());
        ImageView ic_greater_than = convertView.findViewById(R.id.greater_than);
        ic_greater_than.setImageResource(currentSetting.getIc_greater());
        return convertView;
    }
}