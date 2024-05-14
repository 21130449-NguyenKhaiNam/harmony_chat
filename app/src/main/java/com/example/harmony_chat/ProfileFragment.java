package com.example.harmony_chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends AppCompatActivity {
    TextView setting, profile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting = findViewById(R.id.text_setting);
        profile = findViewById(R.id.text_profile);

        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoSetting();
            }
        });

        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoMyProfile();
            }
        });
    }

    public void gotoSetting() {
        Intent intent = new Intent(this, SettingScreen.class);
        startActivity(intent);
    }

    public void gotoMyProfile() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

}

