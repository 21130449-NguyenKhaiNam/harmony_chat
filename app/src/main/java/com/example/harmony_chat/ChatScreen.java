package com.example.harmony_chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harmony_chat.Adapter.ChatRecyclerAdapter;
import com.example.harmony_chat.model.ChatMessageModel;
import com.example.harmony_chat.model.Room;
import com.example.harmony_chat.model.User;
import com.example.harmony_chat.service.CallService;
import com.example.harmony_chat.util.AndroidUtil;
import com.example.harmony_chat.util.CheckInfomation;
import com.example.harmony_chat.util.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Query;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChatScreen extends AppCompatActivity {
    private TextView txtChatName;
    private EditText txtChatMessage;
    private ImageView img_avatar;

    private ImageButton backBtn, btn_send, btnMore;

    private User primaryUser, secondaryUser;
    private com.example.harmony_chat.model.Profile secondaryProfile;
    private Room room;

    private ChatRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout footer;

    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_chat_screen);

        hideSystemUI();
        loadConfig();
        process();
        callApi();
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
        // Lấy ra đối tượng Profile được gửi thông qua intent từ MainActivity.java
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", null);
        // Không có tài khoản
        if (CheckInfomation.isEmpty(userId)) {
            Log.e("ChatScreen UserId", userId);
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
        primaryUser = (User) bundle.getSerializable("primary_user");
        secondaryUser = (User) bundle.getSerializable("secondary_user");
        secondaryProfile = (com.example.harmony_chat.model.Profile) bundle.getSerializable("secondary_profile");

        txtChatName.setText(secondaryUser.getEmail());
        AndroidUtil.loadImage(secondaryProfile.getAvatar(), img_avatar);

        setupChatRecyclerView();
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
        ChatMessageModel chatMessageModel = new ChatMessageModel(
                message, room.getId() + "",
                primaryUser.getId(), Timestamp.now()
        );

        FirebaseUtil.getChatroomMessageReference(room.getId() + "")
                .add(chatMessageModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        txtChatMessage.setText("");
                    } else {
                        Toast.makeText(this, "\"" + message + "\" isn't send!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupChatRecyclerView() {
        Query query = FirebaseUtil.getChatroomMessageReference(room.getId() + "")
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query, ChatMessageModel.class).build();

        adapter = new ChatRecyclerAdapter(options, getApplicationContext(), primaryUser.getId());
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

    private void callApi() {
        CallService.getInstance().getAllMembersRoomWObservalbe(room.getId() + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        
                    }

                    @Override
                    public void onNext(@NonNull List<User> users) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
