package com.example.harmony_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity1 extends AppCompatActivity {
    int icon[]={R.drawable.account_privacy,R.drawable.friends,R.drawable.block,R.drawable.chat
    ,R.drawable.unfriend,R.drawable.font,R.drawable.add_user,R.drawable.chat,R.drawable.unfriend,R.drawable.font};
    String name[]={"Account Privacy","List Friend","Blocked","Messages","Restricted","Hidden word","Add Friend",
    "help","Account status","About"};
    String thong_so[]={"Public","50","0","0","20","0","0","0","online","0"};
    int  ic_greater=R.drawable.greater_than;
    ArrayList<setting>myList;
    myArrAdapter mad;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        lv=findViewById(R.id.lv);
        myList=new ArrayList<>();
        for(int i=0;i<name.length;i++){
            myList.add(new setting(icon[i],name[i],thong_so[i],ic_greater));
        }
        mad=new myArrAdapter(MainActivity1.this,R.layout.item_setting,myList);
        lv.setAdapter(mad);



    }
}