package com.example.harmony_chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.harmony_chat.model.Profile;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class FriendListAdapter extends ArrayAdapter<Profile> {
    private Context context;
    int id_layout;
    private List<Profile> friendList;

    public FriendListAdapter(Context context, int id_layout, List<Profile> friendList) {
        super(context, id_layout, friendList);
        this.context = context;
        this.id_layout = id_layout;
        this.friendList = friendList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(id_layout, parent, false);
        Profile currentSProfile = friendList.get(position);
        RoundedImageView avatarImageView = convertView.findViewById(R.id.item_friend_avatar);
        Glide.with(context)
                .load(currentSProfile.getAvatar()) // Truyền đường dẫn của avatar vào đây
                .placeholder(R.drawable.placeholder_avatar) // Hình ảnh hiển thị khi đang tải
                .error(R.drawable.error_avatar) // Hình ảnh hiển thị khi tải thất bại
                .into(avatarImageView);
        TextView text = convertView.findViewById(R.id.item_friend_text_name);
        text.setText(currentSProfile.getUsername());
        return convertView;
    }
}

