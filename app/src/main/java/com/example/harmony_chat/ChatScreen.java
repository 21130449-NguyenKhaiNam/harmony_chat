package com.example.harmony_chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harmony_chat.Adapter.ChatRecyclerAdapter;
import com.example.harmony_chat.model.ChatMessageModel;
import com.example.harmony_chat.model.ChatroomModel;
import com.example.harmony_chat.model.Profile;
import com.example.harmony_chat.model.Room;
import com.example.harmony_chat.model.User;
import com.example.harmony_chat.service.CallService;
import com.example.harmony_chat.util.AndroidUtil;
import com.example.harmony_chat.util.CheckInfomation;
import com.example.harmony_chat.util.FirebaseUtil;
import com.example.harmony_chat.util.RxHelper;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.firestore.Query;

public class ChatScreen extends AppCompatActivity {
    private TextView txtChatName;
    private EditText txtChatMessage;
    private ImageView img_avatar;

    private ImageButton backBtn, btn_send, btnMore;
    private Room room;
    private ChatRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout footer;
    private List<Profile> profiles = new ArrayList<>();
    private String roomId, userId;
    private ChatroomModel chatroomModel;

    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_chat_screen);

        hideSystemUI();
        loadConfig();
    }

    public void back() {
        finish();
    }

    private void hideSystemUI() {
        final View decorView = getWindow().getDecorView();

        Runnable setSystemUiVisibility = new Runnable() {
            @Override
            public void run() {
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                );
            }
        };

        setSystemUiVisibility.run();

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    setSystemUiVisibility.run();
                }
            }
        });
    }

    // Tìm và gán các phần tử của Context vào đối tượng tương ứng. Phải được gọi trước khi xử lý các phần tử của Context như là bắt sự kiện,...
    private void loadConfig() {
        userId = AndroidUtil.getUserId(this);
        // Không có tài khoản
        if (CheckInfomation.isEmpty(userId)) {
            AndroidUtil.showError("UserId", userId);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish(); // Thêm finish để ngăn người dùng quay lại màn hình này
            return;
        }

        // Khởi tạo các view
        footer = findViewById(R.id.chat_screen_footer);
        txtChatName = findViewById(R.id.txt_chat_name);
        txtChatMessage = findViewById(R.id.txt_chat_message);
        recyclerView = findViewById(R.id.chat_recycler_view);
        img_avatar = findViewById(R.id.img_avatar);
        backBtn = findViewById(R.id.backBtn);
        btn_send = findViewById(R.id.btn_send);
        btnMore = findViewById(R.id.btn_more);

        // Lấy thông tin bundle được gửi từ MainActivity.java
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle.isEmpty()) back();
        room = (Room) bundle.getSerializable("room");
        roomId = String.valueOf(room.getId());

        RxHelper.performImmediately(() -> {
            List<User> users = CallService.getInstance().getAllMembersRoom(roomId);
            for (User u : users) {
                Profile userPro5 = CallService.getInstance().viewMyProfile(u.getId());
                if (userPro5.getUser().getId().equals(userId)) {
                    profiles.add(0, userPro5);
                } else
                    profiles.add(userPro5);
            }
            runOnUiThread(() -> {
                txtChatName.setText(bundle.getString("chatname"));
                process();
                loadImage4Chatroom();
                setupChatRecyclerView();
                getOrCreateChatroomModel();
            });
        });

    }

    private void getOrCreateChatroomModel() {
        List<String> userIds = new ArrayList<>();
        RxHelper.performImmediately(() -> {
            List<User> users = CallService.getInstance().getAllMembersRoom(roomId);
            users.forEach(user -> {
                userIds.add(user.getId());
            });
            runOnUiThread(() -> {
                FirebaseUtil.getChatroomReference(roomId).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        chatroomModel = task.getResult().toObject(ChatroomModel.class);
                        if (chatroomModel == null) {
                            chatroomModel = new ChatroomModel(
                                    roomId,
                                    userIds,
                                    Timestamp.now(),
                                    ""
                            );
                            FirebaseUtil.getChatroomReference(roomId).set(chatroomModel);
                        }
                    }
                });
            });
        });
    }

    private void loadImage4Chatroom() {
        AndroidUtil.loadImage(room.getImage(), img_avatar);
    }

    private void process() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        btn_send.setOnClickListener(e -> {
            String message = txtChatMessage.getText().toString().trim();
            if (message.isEmpty()) return;
            sendMessageToUser(message);
        });

        btnMore.setOnClickListener(e -> {
            createPopupMoreFeatures();
        });
    }

    private void createPopupMoreFeatures() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fragment_profile_settings, null);

        int width = getResources().getDimensionPixelSize(R.dimen.popup_options), height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        btnMore.post(() -> {
            popupWindow.showAsDropDown(footer, Gravity.END, -Gravity.TOP * 2, Gravity.TOP);
            popupWindow.setWindowLayoutType(WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG);
        });
    }

    private void sendMessageToUser(String message) {

        chatroomModel.setLastMessageTimestamp(Timestamp.now());
        chatroomModel.setLastMessageSenderId(userId);
        FirebaseUtil.getChatroomReference(roomId).set(chatroomModel);

        ChatMessageModel chatMessageModel = new ChatMessageModel(message, roomId, userId, Timestamp.now());

        FirebaseUtil.getChatroomMessageReference(roomId)
                .add(chatMessageModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        txtChatMessage.setText("");
                    } else {
                        String m = "\"" + message + "\" isn't send!";
                        AndroidUtil.showToast(this, m);
                        AndroidUtil.showError("Can't send message", m);
                    }
                });
    }

    private void setupChatRecyclerView() {
        AndroidUtil.showError("Firebase", roomId);
        Query query = FirebaseUtil.getChatroomMessageReference(roomId)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Log.d("FirestoreQuery", document.getId() + " => " + document.getData());
                }
            } else {
                Log.d("FirestoreQuery", "Error getting documents: ", task.getException());
            }
        });

        FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query, ChatMessageModel.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new ChatRecyclerAdapter(options, getApplicationContext(), profiles.get(0).getUser().getId());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }
}
