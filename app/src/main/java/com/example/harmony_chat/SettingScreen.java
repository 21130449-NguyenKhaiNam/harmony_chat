package com.example.harmony_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.harmony_chat.model.Profile;
import com.example.harmony_chat.model.Setting;
import com.example.harmony_chat.util.MapperJson;
import com.example.harmony_chat.util.RxHelper;
import com.squareup.picasso.Picasso;

import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingScreen extends AppCompatActivity {

    private ImageButton back, logoutButton, avatar;
    private TextView username;
    private int[] icons = {R.drawable.account_privacy, R.drawable.friends, R.drawable.block, R.drawable.chat, R.drawable.unfriend, R.drawable.font, R.drawable.add_user, R.drawable.chat, R.drawable.unfriend, R.drawable.font};
    private String[] names = {"Quyền riêng tư tài khoản", "Danh sách bạn bè", "Bị chặn", "Tin nhắn", "Hạn chế", "Ẩn từ ngữ", "Thêm bạn bè", "Trợ giúp", "Trạng thái tài khoản", "Giới thiệu"};
    private String[] thongSo = {"Public", "0", "0", "0", "20", "0", "0", "0", "Online", "0"};
    private int icGreater = R.drawable.greater_than;
    private ArrayList<Setting> group1, group2, group3;
    private ArrAdapterSetting adapter1, adapter2, adapter3;
    private ListView lv1, lv2, lv3;
    private com.example.harmony_chat.model.Profile profile;
    private static final int REQUEST_FRIEND_LIST = 1;
    private static final int REQUEST_BLOCKED_USERS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializeViews();
        initializeProfile();
        setupSettingsGroups();
        setupAdapters();
        setupListeners();

        hideSystemUI();
    }

    private void initializeViews() {
        avatar = findViewById(R.id.account_status);
        username = findViewById(R.id.user_name);
        lv1 = findViewById(R.id.lv_group1);
        lv2 = findViewById(R.id.lv_group2);
        lv3 = findViewById(R.id.lv_group3);
        back = findViewById(R.id.btn_back_other);
        logoutButton = findViewById(R.id.ic_logout);
    }

    private void initializeProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        profile = MapperJson.getInstance().convertObjFromJson(sharedPreferences.getString("profile", ""), Profile.class);

        RxHelper.performImmediately(() -> {
            Intent intent = getIntent();
            thongSo[1] = String.valueOf(intent.getIntExtra("totalFriends", 0));
            thongSo[3] = thongSo[1];
            runOnUiThread(this::populateSettings);
        });

        Picasso.get().load(profile.getAvatar()).into(avatar);
        username.setText(profile.getUsername());
    }

    private void setupSettingsGroups() {
        group1 = new ArrayList<>();
        group2 = new ArrayList<>();
        group3 = new ArrayList<>();
        populateSettings();
    }

    private void setupAdapters() {
        adapter1 = new ArrAdapterSetting(this, R.layout.item_setting, group1);
        adapter2 = new ArrAdapterSetting(this, R.layout.item_setting, group2);
        adapter3 = new ArrAdapterSetting(this, R.layout.item_setting, group3);
        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);
        lv3.setAdapter(adapter3);
    }

    private void setupListeners() {
        back.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        logoutButton.setOnClickListener(v -> handleLogout());

        lv1.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    handleAccountPrivacy();
                    break;
                case 1:
                    openFriendList();
                    break;
                case 2:
                    openBlockedUsers();
                    break;
                case 3:
                    openMessages();
                    break;
                default:
                    futureFeatures();
            }
        });

        lv2.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0: // Handle "Hạn chế"
                    break;
                case 1: // Handle "Ẩn từ ngữ"
                    break;
                case 2: // Handle "Thêm bạn bè"
                    break;
                default:
                    futureFeatures();
                    break;
            }
        });

        lv3.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0: // Handle "Trợ giúp"
                    break;
                case 1: // Handle "Trạng thái tài khoản"
                    break;
                case 2: // Handle "Giới thiệu"
                    break;
                default:
                    futureFeatures();
                    break;
            }
        });
    }

    private void futureFeatures() {
        Toast.makeText(this, "Tính năng sẽ được cập nhật trong phiên bản sau", Toast.LENGTH_SHORT).show();
    }

    private void hideSystemUI() {
        final View decorView = getWindow().getDecorView();

        Runnable setSystemUiVisibility = () -> decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setSystemUiVisibility.run();

        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                setSystemUiVisibility.run();
            }
        });
    }

    private void handleLogout() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void handleAccountPrivacy() {
        // Handle account privacy logic
    }

    private void openFriendList() {
        Intent intent = new Intent(SettingScreen.this, FriendListActivity.class);
        startActivityForResult(intent, REQUEST_FRIEND_LIST);
    }

    private void openBlockedUsers() {
        Intent intent = new Intent(SettingScreen.this, BlackListActivity.class);
        startActivityForResult(intent, REQUEST_BLOCKED_USERS);
    }

    private void openMessages() {
        Intent intent = new Intent(SettingScreen.this, MainActivity.class);
        startActivity(intent);
    }

    private void populateSettings() {
        group1.clear();
        group2.clear();
        group3.clear();

        for (int i = 0; i < names.length; i++) {
            Setting setting = new Setting(icons[i], names[i], thongSo[i], icGreater);
            if (i < 4) {
                group1.add(setting);
            } else if (i <= 6) {
                group2.add(setting);
            } else {
                group3.add(setting);
            }
        }

        if (adapter1 != null) adapter1.notifyDataSetChanged();
        if (adapter2 != null) adapter2.notifyDataSetChanged();
        if (adapter3 != null) adapter3.notifyDataSetChanged();
    }

    public void updateThongSo(int index, String value) {
        if (index >= 0 && index < thongSo.length) {
            thongSo[index] = value;
            populateSettings(); // Refresh settings list
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_FRIEND_LIST) {
                int friendCount = data.getIntExtra("friendCount", 0);
                updateThongSo(1, String.valueOf(friendCount));
            } else if (requestCode == REQUEST_BLOCKED_USERS) {
                int blockedCount = data.getIntExtra("blockedCount", 0);
                updateThongSo(2, String.valueOf(blockedCount));
            }
        }
    }
}
