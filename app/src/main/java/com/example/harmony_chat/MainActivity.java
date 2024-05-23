package com.example.harmony_chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

public class MainActivity extends AppCompatActivity {

    private CardView avatar;
    private ImageView find;

    private TextView setting, profile;
    private com.example.harmony_chat.model.Profile myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideSystemUI();

        Intent intent = getIntent();
        myProfile = (com.example.harmony_chat.model.Profile) intent.getSerializableExtra("profile");

        boolean isSearchVisible = false;

        avatar = findViewById(R.id.user_avatar);
        avatar.setOnClickListener(e -> {
            createPopUpWindow();
        });

        // Hiển thị hoặc ẩn button tìm kiếm tùy thuộc vào trạng thái của thanh tìm kiếm
        find = findViewById(R.id.search_icon);
        find.setVisibility(isSearchVisible ? View.GONE : View.VISIBLE);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSearchUser();
            }
        });
    }

    private void createPopUpWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fragment_profile_settings, null);

        int width = getResources().getDimensionPixelSize(R.dimen.popup_options),
                heigth = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popupView, width, heigth, focusable);
        avatar.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAsDropDown(avatar, Gravity.AXIS_X_SHIFT, 5, 0);
            }
        });

// Sử dụng các phương thức gọi hàm xử lý trong class ProfileFragment.java ở đây để đơn giản.
        setting = popupView.findViewById(R.id.text_setting);
        profile = popupView.findViewById(R.id.text_profile);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSetting();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMyProfile();
            }
        });
    }

    public void gotoSearchUser() {
        Intent intent = new Intent(this, SearchUserActivity.class);
        startActivity(intent);
    }

    public void gotoMyProfile() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void gotoSetting() {
        Intent intent = new Intent(this, SettingScreen.class);
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
