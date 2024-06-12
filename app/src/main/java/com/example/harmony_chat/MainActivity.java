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
import com.example.harmony_chat.util.AndroidUtil;
import com.example.harmony_chat.model.Hierarchy;
import com.example.harmony_chat.model.User;
import com.example.harmony_chat.service.CallService;
import com.example.harmony_chat.util.CheckInfomation;
import com.example.harmony_chat.util.RxHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity implements SelectListener {

    private CardView avatarCardView;
    private ImageView avatarImageView, find;
    private TextView setting, profile;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private Button allButton, unreadButton, readButton, pinnedButton;
    private Button requestButton;
    private List<Button> buttons;

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatItem> chatItemList;
    private com.example.harmony_chat.model.Profile profileUser;
    private String userId;
//    private User primaryUser, secondaryUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("id", null);
        // Không có tài khoản
        if (CheckInfomation.isEmpty(userId)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        userId = userId.replaceAll("\"", "");

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
        if (account != null) {
            String name = account.getDisplayName();
            String email = account.getEmail();
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
                chatItemList.add(
                        new ChatItem(
                                (hierarchy.getLeader().getId().trim().equals(userId) ? hierarchy.getDeputy().getEmail() : hierarchy.getLeader().getEmail()),
                                "",
                                hierarchy.getRoom().getImage(),
                                hierarchy.getRoom().getPublished(),
                                "",
                                hierarchy.getRoom(),
                                hierarchy.getLeader(),
                                hierarchy.getDeputy()
                        ));
            }
            runOnUiThread(() -> {
                Log.e("Data chat", rooms.toString());
                chatAdapter = new ChatAdapter(chatItemList, this);
                chatRecyclerView.setAdapter(chatAdapter);

                // Load avatar from API
                loadProfileData();
            });
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
        AndroidUtil.loadImage(profileUser.getAvatar(), avatarImageView);
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
        User primaryUser = chatItem.getLeader(), secondaryUser = chatItem.getDeputy();
        String secondaryUserId = (primaryUser.getId().equals(userId)) ? secondaryUser.getId() : primaryUser.getId();

        sendSecondaryUserProfile(chatItem, primaryUser, secondaryUser, secondaryUserId);
    }

    private void sendSecondaryUserProfile(ChatItem chatItem, User primaryUser, User secondaryUser, String secondaryUserId) {
        RxHelper.performImmediately(() -> {
            com.example.harmony_chat.model.Profile secondaryProfile = CallService.getInstance().viewMyProfile(secondaryUserId);

            runOnUiThread(() -> {
                Intent intent = new Intent(this, ChatScreen.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("room", chatItem.getRoom());

                if (primaryUser.getId().equals(userId)) {
                    bundle.putSerializable("primary_user", primaryUser);
                    bundle.putSerializable("secondary_user", secondaryUser);
                } else {
                    bundle.putSerializable("primary_user", secondaryUser);
                    bundle.putSerializable("secondary_user", primaryUser);
                }

                bundle.putSerializable("secondary_profile", secondaryProfile);
                intent.putExtras(bundle);
                startActivity(intent);
            });
        });
    }
}
