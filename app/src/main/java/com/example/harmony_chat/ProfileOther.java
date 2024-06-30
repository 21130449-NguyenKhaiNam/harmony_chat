package com.example.harmony_chat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harmony_chat.model.Hierarchy;
import com.example.harmony_chat.model.Profile;
import com.example.harmony_chat.model.Room;
import com.example.harmony_chat.service.CallService;
import com.example.harmony_chat.service.LoadImgService;
import com.example.harmony_chat.util.MapperJson;
import com.example.harmony_chat.util.RxHelper;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ProfileOther extends AppCompatActivity {

    ImageButton backBtn;
    RoundedImageView avatar;
    TextView username, description;
    RecyclerView conFriends;

    Button relationshipBtn, sendMessageBtn;
    private List<com.example.harmony_chat.model.Profile> listFriends;

    boolean isFriend = false;
    boolean isSendAddFriend = false;

    int colorPrimarySky;
    int colorWhite;
    private com.example.harmony_chat.model.Profile profileMain;
    private Profile profile;


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_other);
        colorPrimarySky = ContextCompat.getColor(this, R.color.primary_sky);
        colorWhite = ContextCompat.getColor(this, R.color.primary_white);

        backBtn = findViewById(R.id.btn_back);
        relationshipBtn = findViewById(R.id.btn_relationship);
        sendMessageBtn = findViewById(R.id.btn_message);
        avatar = findViewById(R.id.avatar);
        username = findViewById(R.id.text_username);
        description = findViewById(R.id.text_description);
        conFriends = findViewById(R.id.con_friends);
        profile = (Profile) getIntent().getSerializableExtra("other");

        username.setText(profile.getUsername());
        LoadImgService.getInstance().injectImage(profile.getAvatar(), avatar);

        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        profileMain = MapperJson.getInstance().convertObjFromJson(sharedPreferences.getString("profile", null), com.example.harmony_chat.model.Profile.class);
        RxHelper.performImmediately(() -> {
            listFriends = CallService.getInstance().getMyListFriends(profile.getUser().getId());
            listFriends.forEach(friend -> {
                if (friend.getUser().getId().equals(profileMain.getUser().getId())) {
                    isFriend = true;
                    return;
                }
            });
            runOnUiThread(() -> {
                // Bạn bè
                FriendsAdapter friendsAdapter = new FriendsAdapter(listFriends);
                conFriends.setAdapter(friendsAdapter);
                conFriends.setLayoutManager(new LinearLayoutManager(this));
            });
        });

        setRelationshipBtnState(isFriend, isSendAddFriend);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        relationshipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFriend == false) {
                    if (isSendAddFriend == false) {
                        sendAddFriend();
                        isSendAddFriend = true;
                    } else {
                        cancelAddFriend();
                        isSendAddFriend = false;
                    }
                } else {
                    deleteFiend();
                    isFriend = false;
                }
            }
        });

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    public void back() {
        finish();
    }


    //    thiet lap trang thai ban dau cho nut relationship
    public void setRelationshipBtnState(boolean isFriend, boolean isSendAddFriend) {
        if (isSendAddFriend) { //neu da la ban
            relationshipBtn.setText(R.string.friend);
            relationshipBtn.setTextColor(colorWhite);
            relationshipBtn.setBackgroundResource(R.drawable.button_active);
        } else { //chua la ban
            if (isSendAddFriend) {  // da gui ket ban
                relationshipBtn.setText(R.string.cancelAddFriend);
                relationshipBtn.setTextColor(colorPrimarySky);
                relationshipBtn.setBackgroundResource(R.drawable.background_border);
            } else { // chua gui ket ban
                relationshipBtn.setText(R.string.addFriend);
                relationshipBtn.setTextColor(colorWhite);
                relationshipBtn.setBackgroundResource(R.drawable.button_active);
            }

        }
    }


    //    gui ket ban
    public void sendAddFriend() {
        relationshipBtn.setText(R.string.cancelAddFriend);
        relationshipBtn.setTextColor(colorPrimarySky);
        relationshipBtn.setBackgroundResource(R.drawable.background_border);
        if(!isFriend) {
            RxHelper.performImmediately(() -> {
                CallService.getInstance().addFriend(profileMain.getUser().getId(), profile.getUser().getId());
            });
        }
    }

    //    huy gui ket ban
    public void cancelAddFriend() {
        relationshipBtn.setText(R.string.addFriend);
        relationshipBtn.setTextColor(colorWhite);
        relationshipBtn.setBackgroundResource(R.drawable.button_active);
    }

    //    huy ket ban
    public void deleteFiend() {
        //hien ra hop thoai xac nhan
    }

    public void sendMessage() {
        if (isSendAddFriend) {
            Room room = new Room();
            Profile deputy = new Profile();
            Profile lead = new Profile();
            RxHelper.performImmediately(() -> {
                List<Hierarchy> hierarchies = CallService.getInstance().getRoom(profileMain.getUser().getId());
                hierarchies.forEach(hierarchy -> {
                    if (hierarchy.getLeader().getId().equals(profileMain.getUser().getId())
                            && hierarchy.getDeputy().getId().equals(profile.getUser().getId())) {
                        deputy.setUsername(profile.getUsername());
                        deputy.setAvatar(profile.getAvatar());
                        Room r = hierarchy.getRoom();
                        room.setId(r.getId());
                        room.setBackground(r.getBackground());
                        room.setImage(r.getImage());
                        room.setPublished(r.getPublished());
                        room.setVisible(r.isVisible());
                    }
                });
            });
            if (room != null) {
                Intent intent = new Intent(this, ChatScreen.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("room", room);
                bundle.putString("chatname", deputy.getUsername());
                bundle.putString("image", deputy.getAvatar());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        } else {
            // Tạo và hiển thị AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo");
            builder.setMessage("Hãy gửi lời mời kết bạn trước.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
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
        public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_friend, parent, false);
            return new FriendsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FriendsAdapter.ViewHolder holder, int position) {
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