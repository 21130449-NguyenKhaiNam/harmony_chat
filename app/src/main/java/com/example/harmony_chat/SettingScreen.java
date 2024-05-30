package com.example.harmony_chat;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class SettingScreen extends AppCompatActivity {
    ImageButton back, logoutButton;
    int icon[] = {R.drawable.account_privacy, R.drawable.friends, R.drawable.block, R.drawable.chat, R.drawable.unfriend, R.drawable.font, R.drawable.add_user, R.drawable.chat, R.drawable.unfriend, R.drawable.font};
    String name[] = {"Quyền riêng tư tài khoản", "Danh sách bạn bè", "Bị chặn", "Tin nhắn", "Hạn chế", "Ẩn từ ngữ", "Thêm bạn bè", "Trợ giúp", "Trạng thái tài khoản", "Giới thiệu"};
    String thong_so[] = {"Public", "0", "0", "0", "20", "0", "0", "0", "Online", "0"};
    int ic_greater = R.drawable.greater_than;
    ArrayList<Setting> group1, group2, group3;
    ArrAdapterSetting adapter1, adapter2, adapter3;
    ListView lv1, lv2, lv3;

    private static final int REQUEST_FRIEND_LIST = 1;
    private static final int REQUEST_BLOCKED_USERS = 2;

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

        populateSettings();

        adapter1 = new ArrAdapterSetting(this, R.layout.item_setting, group1);
        adapter2 = new ArrAdapterSetting(this, R.layout.item_setting, group2);
        adapter3 = new ArrAdapterSetting(this, R.layout.item_setting, group3);

        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);
        lv3.setAdapter(adapter3);

        back = findViewById(R.id.btn_back_other);
        logoutButton = findViewById(R.id.ic_logout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogout();
            }
        });

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                }
            }
        });

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Xử lý khi người dùng nhấp vào "Hạn chế"
                        break;
                    case 1:
                        // Xử lý khi người dùng nhấp vào "Ẩn từ ngữ"
                        break;
                    case 2:
                        // Xử lý khi người dùng nhấp vào "Thêm bạn bè"
                        break;
                }
            }
        });

        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Xử lý khi người dùng nhấp vào "Trợ giúp"
                        break;
                    case 1:
                        // Xử lý khi người dùng nhấp vào "Trạng thái tài khoản"
                        break;
                    case 2:
                        // Xử lý khi người dùng nhấp vào "Giới thiệu"
                        break;
                }
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

    private void handleLogout() {
        Intent intent = new Intent(SettingScreen.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void handleAccountPrivacy() {
        // Xử lý logic cho mục "Quyền riêng tư tài khoản"
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

        for (int i = 0; i < name.length; i++) {
            if (i < 4) {
                group1.add(new Setting(icon[i], name[i], thong_so[i], ic_greater));
            } else if (i <= 6) {
                group2.add(new Setting(icon[i], name[i], thong_so[i], ic_greater));
            } else {
                group3.add(new Setting(icon[i], name[i], thong_so[i], ic_greater));
            }
        }
    }

    public void updateThongSo(int index, String value) {
        if (index >= 0 && index < thong_so.length) {
            thong_so[index] = value;
            populateSettings(); // Làm mới danh sách cài đặt
            adapter1.notifyDataSetChanged();
            adapter2.notifyDataSetChanged();
            adapter3.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_FRIEND_LIST) {
                int friendCount = data.getIntExtra("friendCount", 0);
                updateThongSo(1, String.valueOf(friendCount)); // Cập nhật thông số cho số lượng bạn bè
            } else if (requestCode == REQUEST_BLOCKED_USERS) {
                int blockedCount = data.getIntExtra("blockedCount", 0);
                updateThongSo(2, String.valueOf(blockedCount)); // Cập nhật thông số cho số lượng người bị chặn
            }
        }
    }
}
