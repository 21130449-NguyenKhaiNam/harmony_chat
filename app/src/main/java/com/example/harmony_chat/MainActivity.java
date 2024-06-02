package com.example.harmony_chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {

    private CardView avatarCardView;
    private ImageView avatarImageView;
    private ImageView find;
    private TextView setting, profile;

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

    String img_url = "https://images.unsplash.com/photo-1627087820883-7a102b79179a?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideSystemUI();
        boolean isSearchVisible = false;

        avatarCardView = findViewById(R.id.user_avatar);
        avatarImageView = findViewById(R.id.avatar_image_view);
        avatarCardView.setOnClickListener(e -> createPopUpWindow());

        find = findViewById(R.id.search_icon);
        find.setVisibility(isSearchVisible ? View.GONE : View.VISIBLE);
//        txtEmail = findViewById(R.id.txtEmail);
//        txtName = findViewById(R.id.txtName);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null) {
            String username = account.getDisplayName();
            String useremail = account.getEmail();
//            txtName.setText(username);
//            txtEmail.setText(useremail);
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
        chatItemList.add(new ChatItem("User1","Hello",img_url,"5:10"));
        chatItemList.add(new ChatItem("User2","Hi",img_url,"5:13"));

        chatAdapter = new ChatAdapter(chatItemList);
        chatRecyclerView.setAdapter(chatAdapter);

        // Load avatar from API
        loadProfileData();

        // Load chat data from API
        loadChatData();
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
                .load(img_url)
                .into(avatarImageView);
    }

    private void loadChatData() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://example.com/path/to/chatdata.json"; // URL của JSON API

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            parseJsonData(jsonData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    private void parseJsonData(String jsonData) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String title = jsonObject.getString("title");
            String message = jsonObject.getString("message");
            String time = jsonObject.getString("time");
            String avatarUrl = jsonObject.getString("avatarUrl");
            chatItemList.add(new ChatItem(title, message, time, avatarUrl));
        }
        chatAdapter.notifyDataSetChanged();
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
}
