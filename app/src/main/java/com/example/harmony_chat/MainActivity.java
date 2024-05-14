package com.example.harmony_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.makeramen.roundedimageview.RoundedImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RoundedImageView userAvatar = findViewById(R.id.user_avatar);
        final LinearLayout avatarMenu = findViewById(R.id.avatar_menu);

        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (avatarMenu.getVisibility() == View.GONE) {
                    avatarMenu.setVisibility(View.VISIBLE);
                } else {
                    avatarMenu.setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.main_layout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (avatarMenu.getVisibility() == View.VISIBLE) {
                        avatarMenu.setVisibility(View.GONE);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
