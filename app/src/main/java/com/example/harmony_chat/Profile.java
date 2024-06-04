package com.example.harmony_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.harmony_chat.service.CallService;
import com.example.harmony_chat.service.LoadImgService;
import com.example.harmony_chat.util.RxHelper;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class Profile extends AppCompatActivity {
    private ImageButton btnBack;
    private TextView textUsername;
    private RoundedImageView avatar;
    private RecyclerView conFriends;
    private com.example.harmony_chat.model.Profile profile;
    private List<com.example.harmony_chat.model.Profile> listFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        hideSystemUI();

        // Ánh xạ phần tử
        btnBack = findViewById(R.id.btn_back);

        textUsername = findViewById(R.id.text_username);
        avatar = findViewById(R.id.avatar);
        conFriends = findViewById(R.id.con_friends);

        work();
    }

    // Luồng thực hiện hàm
    private void work() {
        // Thiết lập chức năng nút
        setupButton();

        // Lấy dữ liệu
        RxHelper.performImmediately(() -> {
            profile = CallService.getInstance().viewMyProfile("316199df-4227-4efb-8af3-42e4a5dd8c4a");
            listFriends = CallService.getInstance().getMyListFriends("316199df-4227-4efb-8af3-42e4a5dd8c4a");
            runOnUiThread(() -> injectData());
        });
    }

    // Truyền dữ liệu vào
    private void injectData() {
        // Thông tin cá nhân
        LoadImgService.getInstance().injectImage(profile.getAvatar(), avatar);
        textUsername.setText(profile.getUsername());

        // Bạn bè
        FriendsAdapter friendsAdapter = new FriendsAdapter(listFriends);
        conFriends.setAdapter(friendsAdapter);
        conFriends.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupButton() {
        btnBack.setOnClickListener((view) -> finish());
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

    public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

        private List<com.example.harmony_chat.model.Profile> friendsList;

        // Constructor
        public FriendsAdapter(List<com.example.harmony_chat.model.Profile> friendsList) {
            this.friendsList = friendsList;
        }

        // ViewHolder class
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView nameTextView;
            public RoundedImageView imgView;

            public ViewHolder(View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.item_friend_text_name);
                imgView = itemView.findViewById(R.id.item_friend_avatar);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_friend, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            com.example.harmony_chat.model.Profile friend = friendsList.get(position);
            holder.nameTextView.setText(friend.getUsername());
            LoadImgService.getInstance().injectImage(friend.getAvatar(), holder.imgView);
        }

        @Override
        public int getItemCount() {
            return friendsList.size();
        }
    }

}