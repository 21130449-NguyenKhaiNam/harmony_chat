package com.example.harmony_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class SettingScreen extends AppCompatActivity {
    ImageButton back;
    int icon[]={R.drawable.account_privacy,R.drawable.friends,R.drawable.block,R.drawable.chat
    ,R.drawable.unfriend,R.drawable.font,R.drawable.add_user,R.drawable.chat,R.drawable.unfriend,R.drawable.font};
    String name[]={"Account Privacy","List Friend","Blocked","Messages","Restricted","Hidden word","Add Friend",
    "help","Account status","About"};
    String thong_so[]={"Public","50","0","0","20","0","0","0","online","0"};
    int  ic_greater=R.drawable.greater_than;
    ArrayList<Setting>myList;
    ArrayList<Setting> group1, group2, group3;
    myArrAdapter adapter1, adapter2, adapter3;
    ListView lv1, lv2, lv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        hideSystemUI();

        lv1 = findViewById(R.id.lv_group1);
        lv2 = findViewById(R.id.lv_group2);
        lv3 = findViewById(R.id.lv_group3);
        group1 = new ArrayList<>();
        group2 = new ArrayList<>();
        group3 = new ArrayList<>();

        for (int i = 0; i < name.length; i++) {
            if (i < 4) {
                group1.add(new Setting(icon[i], name[i], thong_so[i], ic_greater));
            } else if (i <= 6) {
                group2.add(new Setting(icon[i], name[i], thong_so[i], ic_greater));
            } else {
                group3.add(new Setting(icon[i], name[i], thong_so[i], ic_greater));
            }
        }

        adapter1 = new myArrAdapter(this, R.layout.item_setting, group1);
        adapter2 = new myArrAdapter(this, R.layout.item_setting, group2);
        adapter3 = new myArrAdapter(this, R.layout.item_setting, group3);

        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);
        lv3.setAdapter(adapter3);
        back=findViewById(R.id.btn_back_other);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void hideSystemUI() {
        // Ẩn thanh trạng thái và thanh điều hướng
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }
}
