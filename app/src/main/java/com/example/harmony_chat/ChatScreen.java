package com.example.harmony_chat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.harmony_chat.model.Profile;
import com.example.harmony_chat.util.FirebaseUtil;
import com.squareup.picasso.Picasso;

public class ChatScreen extends AppCompatActivity {
    private TextView txtChatName;
    private Profile myProfile;
    private Profile otherUser;
    private EditText txtChatMessage;
    private ImageView img_avatar;

    private ImageButton backBtn, btn_send;
    private String chatroomId;

    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_chat_screen);

        hideSystemUI();
        loadConfig();
        process();
    }

    public void back() {
        finish();
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

    private void loadImage() {
        String default_url =
                "https://images.unsplash.com/photo-1627087820883-7a102b79179a?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                img_url = (img_avatar != null) ?
                        ((myProfile.getAvatar() != null) && !(myProfile.getAvatar().trim().isEmpty())) ? myProfile.getAvatar() : default_url :
                        default_url;
        Picasso.get()
                .load(img_url)
                .into(img_avatar);
    }

    // Tìm và gán các phần tử của Context vào đối tượng tương ứng. Phải được gọi trước khi xử lý các phần tử của Context như là bắt sự kiện,...
    private void loadConfig() {
        // Lấy ra đối tượng Profile được gửi thông qua intent từ MainActivity.java
        myProfile = (Profile) getIntent().getSerializableExtra("myProfile");

        txtChatName = findViewById(R.id.txt_chat_name);
        txtChatName.setText(myProfile.getUser().getId());   // tạm thời hiển thị id của người dùng đang đăng nhập

        txtChatMessage = findViewById(R.id.txt_chat_message);

        img_avatar = findViewById(R.id.img_avatar);
        loadImage();

        backBtn = findViewById(R.id.backBtn);
        btn_send = findViewById(R.id.btn_send);

        // getUserModel
        otherUser = (Profile) getIntent().getSerializableExtra("otherUser");
        chatroomId = FirebaseUtil.getChatroomId(FirebaseUtil.currentUserId(), otherUser.getUser().getId());
    }

    private void process() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }
}
