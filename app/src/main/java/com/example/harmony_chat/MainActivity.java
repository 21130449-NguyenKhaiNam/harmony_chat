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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harmony_chat.Adapter.ChatAdapter;
import com.example.harmony_chat.Adapter.SelectListener;
import com.example.harmony_chat.Item.ChatItem;
import com.example.harmony_chat.model.Hierarchy;
import com.example.harmony_chat.model.User;
import com.example.harmony_chat.service.CallService;
import com.example.harmony_chat.service.LoadImgService;
import com.example.harmony_chat.util.AndroidUtil;
import com.example.harmony_chat.util.CheckInfomation;
import com.example.harmony_chat.util.MapperJson;
import com.example.harmony_chat.util.RxHelper;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener {

    private FrameLayout avatarCardView;
    private RoundedImageView avatarImageView;
    private ImageView find;
    private TextView setting, profile, username;

    private Button allButton, unreadButton, readButton, pinnedButton;
    private Button requestButton;
    private List<Button> buttons;

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatItem> chatItemList;
    private com.example.harmony_chat.model.Profile profileUser;
    private String userId;

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

        hideSystemUI();

        username = findViewById(R.id.user_name);

        boolean isSearchVisible = false;

        avatarCardView = findViewById(R.id.user_avatar);
        avatarImageView = findViewById(R.id.avatar_image_view);
        avatarCardView.setOnClickListener(e -> {
            createPopUpWindow();
        });

        find = findViewById(R.id.search_icon);
        find.setVisibility(isSearchVisible ? View.GONE : View.VISIBLE);
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
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("profile", MapperJson.getInstance().convertObjToJson(profileUser));
            editor.commit();
            User user = new User(userId);
            profileUser.setUser(user);
            for (int i = 0; i < rooms.size(); i++) {
                Hierarchy hierarchy = rooms.get(i);
                chatItemList.add(
                        new ChatItem(
                                (hierarchy.getLeader().getId().trim().equals(userId) ? hierarchy.getDeputy().getEmail() : hierarchy.getLeader().getEmail()),
                                "",
                                hierarchy.getRoom().getImage(),
                                hierarchy.getRoom().getPublished(),
                                hierarchy.getDeputy().getId(),
                                hierarchy.getRoom(),
                                hierarchy.getLeader(),
                                hierarchy.getDeputy()
                        ));
            }
            runOnUiThread(() -> {
                chatAdapter = new ChatAdapter(chatItemList, this);
                chatRecyclerView.setAdapter(chatAdapter);
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

        avatarCardView.post(() -> {
            popupWindow.showAsDropDown(avatarCardView, Gravity.AXIS_X_SHIFT, 5, 0);
        });

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
        intent.putExtra("totalFriends", chatItemList.size());
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
        username.setText(profileUser.getUsername());
        LoadImgService.getInstance().injectImage(profileUser.getAvatar(), avatarImageView);
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


    // Khi người dùng nhấn vào từng dòng của RecycleView thì thực hiện chuyển màn hình tới ChatScreen tương ứng của cuộc trò chuyện
    @Override
    public void onItemClicked(ChatItem chatItem) {
        Intent intent = new Intent(this, ChatScreen.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("room", chatItem.getRoom());

        /**
         * Trong trường hợp này vì của Nam chỉ cung cấp mỗi API /api/v1/relationship/room nên sẽ chỉ trả về được mỗi List<Hierarchy>
         * Đối với cấu trúc của Hierarchy thì chỉ tồn tại một room_id trong table nên muốn xác định người truy cập
         * vào trong cuộc trò chuyện là A hoặc B thì phải thực hiện kiểm tra user_id của 1 trong 2.
         * Ví dụ:
         * - Trong Hierarchy có 2 thuộc tính là leader: A và deputy: B
         * - Khi A đăng nhập thì A sẽ truy cập vào cuộc trò chuyện này với vai trò là người GỬI, B là người NHẬN
         * - Khi B đăng nhập thì B sẽ là người GỬI, A là người NHẬN
         * - Vấn đề đặt ra là làm sao để biết khi nào thì A (B) đóng vai trò nào trong phiên đăng nhập? => so sánh id với user_id của phiên đăng nhập
         * - Sau khi đã xác định được vai trò thì mặc định ở ChatScreen sẽ tự hiểu là primaryUser sẽ là người GỬI, secondaryUser sẽ là người NHẬN
         */
        User primaryUser = chatItem.getLeader(), secondaryUser = chatItem.getDeputy();
        if (primaryUser.getId().equals(userId)) {
            bundle.putSerializable("primary_user", primaryUser);
            bundle.putSerializable("secondary_user", secondaryUser);
        } else {
            bundle.putSerializable("primary_user", secondaryUser);
            bundle.putSerializable("secondary_user", primaryUser);
        }

        intent.putExtras(bundle);
        startActivity(intent);
    }

//    private void whoAmI(User user1, User user2) {
//        if (user1 != null && user2 != null && user1.getId() != null && user2.getId() != null) {
//            if (user1.getId().equals(userId)) {
//                primaryUser = user1;
//                secondaryUser = user2;
//            } else {
//                primaryUser = user2;
//                secondaryUser = user1;
//            }
//        }
//    }
}
