package com.example.harmony_chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.harmony_chat.Adapter.ChatAdapter;
import com.example.harmony_chat.Adapter.SelectListener;
import com.example.harmony_chat.Item.ChatItem;
import com.example.harmony_chat.model.ChatroomModel;
import com.example.harmony_chat.model.Room;
import com.example.harmony_chat.util.FirebaseUtil;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener {

    private CardView avatar;
    private ImageView find;

    private TextView setting, profile;
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatItem> chatItemList;
    private com.example.harmony_chat.model.Profile myProfile, otherProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideSystemUI();

//        createDB();

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

        // Initialize RecyclerView
        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatItemList = new ArrayList<>();
        String urlImage = "https://images.unsplash.com/photo-1627087820883-7a102b79179a?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

        // Truy xuất cơ sở dữ liệu và hiển thị nó ra RecycleView của màn hình giao diện
        FirebaseFirestore.getInstance()
                .collection("CHATROOMS")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ChatroomModel chatroom = document.toObject(ChatroomModel.class);
                            chatItemList.add(new ChatItem(
                                    chatroom.getRoom().getId(),
                                    chatroom.getLastMessageSenderId(),
                                    chatroom.getRoom().getImage(),
                                    FirebaseUtil.timestampToString(chatroom.getLastMessageTimestamp()),
                                    chatroom.getUserIds().get(1))
                            );
                        }
                        // Sau khi đã lấy được dữ liệu từ Firestore và thêm vào chatItemList
                        // Bây giờ ta tạo adapter và thiết lập cho RecyclerView
                        chatAdapter = new ChatAdapter(chatItemList, this);
                        chatRecyclerView.setAdapter(chatAdapter);
                        chatAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("MAIN_ACTIVITY ERROR", "Error getting documents: ", task.getException());
                    }
                });
    }

    private void createDB() {

        ArrayList<String> userIds1 = new ArrayList<>(),
                userIds2 = new ArrayList<>();
        userIds1.add("PIvRqetu8cPO08NKhgwBoHS6WWK2");
        userIds1.add("jcb71Z0JguTSgRGLO9tB3zjyHND3");
        userIds2.add("jcb71Z0JguTSgRGLO9tB3zjyHND3");
        userIds2.add("sCnLWWex8bc7hZNy0k3QQmBwjA52");

        ChatroomModel r1 = new ChatroomModel(
                new Room(),
                userIds1,
                new Timestamp(Instant.now()),
                ""),
                r2 = new ChatroomModel(
                        new Room(),
                        userIds2,
                        new Timestamp(Instant.now()),
                        ""
                );
        r1.getRoom().setId("1");
        r2.getRoom().setId("2");
        ArrayList<ChatroomModel> chatroomModels = new ArrayList<>();
        chatroomModels.add(r1);
        chatroomModels.add(r2);

        for (ChatroomModel c : chatroomModels) {
            FirebaseFirestore.getInstance()
                    .collection("CHATROOMS")
                    .add(c)
                    .addOnCompleteListener(task -> {

                    });
        }

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

    // Khi người dùng nhấn vào từng dòng của RecycleView thì thực hiện chuyển màn hình tới ChatScreen tương ứng của cuộc trò chuyện
    @Override
    public void onItemClicked(ChatItem chatItem) {
        Intent intent = new Intent(this, ChatScreen.class);

        FirebaseFirestore.getInstance().collection("PROFILES")
                .whereEqualTo("user.id", chatItem.getOtherUserId())
                .get().addOnCompleteListener(task -> {
                   if (task.isSuccessful()){
                       for (DocumentSnapshot documnet: task.getResult()){
                           otherProfile = documnet.toObject(com.example.harmony_chat.model.Profile.class);
                       }
                   }
                });

        intent.putExtra("myProfile", myProfile);
        intent.putExtra("otherUser", otherProfile);
        startActivity(intent);
    }


}
