package com.example.harmony_chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.harmony_chat.model.Hierarchy;
import com.example.harmony_chat.model.User;
import com.example.harmony_chat.service.CallService;
import com.example.harmony_chat.util.CheckInfomation;
import com.example.harmony_chat.util.RxHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener {

    private CardView avatarCardView;
    private ImageView avatarImageView;
    private ImageView find;
    private TextView setting, profile, username;

//<<<<<<< HEAD
//    private TextView setting, profile;
//    private RecyclerView chatRecyclerView;
//    private ChatAdapter chatAdapter;
//    private List<ChatItem> chatItemList;
//    private com.example.harmony_chat.model.Profile myProfile, otherProfile;
//=======
//    TextView txtEmail, txtName;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private Button allButton;
    private Button unreadButton;
    private Button readButton;
    private Button pinnedButton;
    private Button requestButton;
    private List<Button> buttons;

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatItem> chatItemList;
    private User user;
    private com.example.harmony_chat.model.Profile profileUser;
//>>>>>>> view-merge

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", null);
        // Không có tài khoản
        if(CheckInfomation.isEmpty(userId)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        hideSystemUI();

        boolean isSearchVisible = false;

        avatarCardView = findViewById(R.id.user_avatar);
        avatarImageView = findViewById(R.id.avatar_image_view);
        avatarCardView.setOnClickListener(e -> createPopUpWindow());

        find = findViewById(R.id.search_icon);
        find.setVisibility(isSearchVisible ? View.GONE : View.VISIBLE);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null) {
            String name = account.getDisplayName();
            String email = account.getEmail();
            username.setText(name);
        }
        find.setOnClickListener(v -> gotoSearchUser());

        // Liên kết các nút với mã Java
        allButton = findViewById(R.id.all_button);
        unreadButton = findViewById(R.id.unread_button);
        readButton = findViewById(R.id.read_button);
        pinnedButton = findViewById(R.id.pinned_button);
        requestButton = findViewById(R.id.request_button);

        // Thêm các nút vào danh sách
        buttons = Arrays.asList(allButton, unreadButton, readButton, pinnedButton, requestButton);

        // Đặt sự kiện nhấp chuột cho các nút
        allButton.setOnClickListener(view -> setSelectedButton(allButton));
        unreadButton.setOnClickListener(view -> setSelectedButton(unreadButton));
        readButton.setOnClickListener(view -> setSelectedButton(readButton));
        pinnedButton.setOnClickListener(view -> setSelectedButton(pinnedButton));
        requestButton.setOnClickListener(view -> setSelectedButton(requestButton));

        // Initialize RecyclerView
        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatItemList = new ArrayList<>();
        RxHelper.performImmediately(() -> {
            List<Hierarchy> rooms = CallService.getInstance().getRoom(userId);
            profileUser = CallService.getInstance().viewMyProfile(userId);
            for (int i = 0; i < rooms.size(); i++) {
                Hierarchy hierarchy = rooms.get(i);
                com.example.harmony_chat.model.Profile profileLeader = CallService.getInstance().viewOtherProfile(hierarchy.getLeader().getId());
                chatItemList.add(new ChatItem(profileLeader.getUsername(), profileLeader.getUsername(), hierarchy.getRoom().getImage(), hierarchy.getRoom().getPublished()));
            }
            runOnUiThread(() -> {
                Log.e("Data chat", rooms.toString());
                chatAdapter = new ChatAdapter(chatItemList, this);
                chatRecyclerView.setAdapter(chatAdapter);
                // Load avatar from API
                loadProfileData();
            });
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
                                    chatroom.getRoom().getId()+"",
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

    private void createPopUpWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fragment_profile_settings, null);

        int width = getResources().getDimensionPixelSize(R.dimen.popup_options), height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        avatarCardView.post(() -> popupWindow.showAsDropDown(avatarCardView, Gravity.AXIS_X_SHIFT, 5, 0));

        setting = popupView.findViewById(R.id.text_setting);
        profile = popupView.findViewById(R.id.text_profile);

        setting.setOnClickListener(v -> gotoSetting());
        profile.setOnClickListener(v -> gotoMyProfile());
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

    private void setSelectedButton(Button selectedButton) {
        for (Button button : buttons) {
            if (button == selectedButton) {
                button.setBackgroundResource(R.drawable.selected_button);
                button.setTextColor(getResources().getColor(R.color.primary_white));
            } else {
                button.setBackgroundResource(R.drawable.unselected_button);
                button.setTextColor(getResources().getColor(R.color.primary_dark));
            }
        }
    }

    private void loadProfileData() {
        Picasso.get()
                .load(profileUser.getAvatar())
                .into(avatarImageView);
    }

    private void hideSystemUI() {
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
//        Intent intent = new Intent(this, ChatScreen.class);
//
//        FirebaseFirestore.getInstance().collection("PROFILES")
//                .whereEqualTo("user.id", chatItem.getOtherUserId())
//                .get().addOnCompleteListener(task -> {
//                   if (task.isSuccessful()){
//                       for (DocumentSnapshot documnet: task.getResult()){
//                           otherProfile = documnet.toObject(com.example.harmony_chat.model.Profile.class);
//                       }
//                   }
//                });
//
//        intent.putExtra("myProfile", myProfile);
//        intent.putExtra("otherUser", otherProfile);
//        startActivity(intent);
    }


}
