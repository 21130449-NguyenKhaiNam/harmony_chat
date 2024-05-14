package com.example.harmony_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.makeramen.roundedimageview.RoundedImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideSystemUI();

        final RoundedImageView userAvatar = findViewById(R.id.user_avatar);
        final LinearLayout avatarMenu = findViewById(R.id.avatar_menu);
        boolean isSearchVisible = false;

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

        // Hiển thị hoặc ẩn button tìm kiếm tùy thuộc vào trạng thái của thanh tìm kiếm
        ImageView find = findViewById(R.id.search_icon);
        find.setVisibility(isSearchVisible ? View.GONE : View.VISIBLE);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSearchUser();
            }
        });
    }

    public void gotoSearchUser(){
        Intent intent = new Intent(this, SearchUserActivity.class);
        startActivity(intent);
    }

    public void gotoMyProfile() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
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
