package com.example.harmony_chat;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.harmony_chat.model.Profile;
import com.example.harmony_chat.service.CallService;
import com.example.harmony_chat.util.MapperJson;
import com.example.harmony_chat.util.RxHelper;

import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends AppCompatActivity {
    private ListView friendListView;
    private FriendListAdapter adapter;
    private ArrayList<Profile> friendList;
    private ImageButton back;
    private com.example.harmony_chat.model.Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        hideSystemUI();
        back = findViewById(R.id.btnBackFriendList);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithResult();
            }
        });
        friendListView = findViewById(R.id.friendListView);
        friendList = new ArrayList<>();
        adapter = new FriendListAdapter(this, R.layout.item_friend, friendList);
        friendListView.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        profile = MapperJson.getInstance().convertObjFromJson(sharedPreferences.getString("profile", ""), Profile.class);

        RxHelper.performImmediately(() -> {
            List<Profile> friends = CallService.getInstance().getMyListFriends(profile.getUser().getId());
            for(int i = 0; i < friends.size(); ++i) {
                friendList.add(friends.get(i));
            }
            Log.e("Friend list", friendList.toString());
            runOnUiThread(() -> {
                adapter.notifyDataSetChanged();
            });
        });
        // Gọi phương thức để lấy danh sách bạn bè từ API
//          getFriendListFromAPI("userId");


        // Tạo và thêm dữ liệu test
//        createTestData();
//        updateFriendListTest();
    }

    private void createTestData() {
        // Tạo một số dữ liệu test cho danh sách bạn bè
        Profile friend1 = new Profile(1, "Alice", "https://www.tvtime.com/_next/image?url=https%3A%2F%2Fartworks.thetvdb.com%2Fbanners%2Fperson%2F8010329%2F62d46047aebd3.jpg&w=640&q=75");
        Profile friend2 = new Profile(2, "Bob", "https://www.tvtime.com/_next/image?url=https%3A%2F%2Fartworks.thetvdb.com%2Fbanners%2Fperson%2F8010329%2F62d46047aebd3.jpg&w=640&q=75");
        Profile friend3 = new Profile(3, "Charlie", "https://www.tvtime.com/_next/image?url=https%3A%2F%2Fartworks.thetvdb.com%2Fbanners%2Fperson%2F8010329%2F62d46047aebd3.jpg&w=640&q=75");

        // Thêm các profile vào danh sách bạn bè
        friendList.add(friend1);
        friendList.add(friend2);
        friendList.add(friend3);
    }

    private void updateFriendListTest() {
        adapter.notifyDataSetChanged();
    }
//        private void getFriendListFromAPI(String userId) {
//            // Thực hiện cuộc gọi API để lấy danh sách bạn bè từ userId bằng CallService
//            ArrayList<Profile> friendProfiles = CallService.getInstance().getMyListFriends(userId);
//            updateFriendList(friendProfiles);
//        }
//
//        private void updateFriendList(ArrayList<Profile> friendProfiles) {
//            friendList.clear();
//            friendList.addAll(friendProfiles);
//            adapter.notifyDataSetChanged();
//        }

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
    private void finishWithResult() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("friendCount", friendList.size());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}