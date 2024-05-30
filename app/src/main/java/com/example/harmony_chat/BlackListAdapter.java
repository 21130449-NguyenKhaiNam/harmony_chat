package com.example.harmony_chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.harmony_chat.model.BlackList;
import com.example.harmony_chat.model.Profile;
import com.example.harmony_chat.service.CallService;
import com.makeramen.roundedimageview.RoundedImageView;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.ArrayList;

public class BlackListAdapter extends ArrayAdapter<BlackList> {
    private Context context;
    private int id_layout;
    private ArrayList<BlackList> blackList;

    public BlackListAdapter(Context context, int id_layout, ArrayList<BlackList> blackList) {
        super(context, id_layout, blackList);
        this.context = context;
        this.id_layout = id_layout;
        this.blackList = blackList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(id_layout, parent, false);
        BlackList currentBlackList = blackList.get(position);
        TextView blockedUserNameTextView = convertView.findViewById(R.id.item_block_text_name);
        RoundedImageView avatarImageView = convertView.findViewById(R.id.item_block_avatar);
        String userId = currentBlackList.getBlockUser().getId();
        CallService callService = CallService.getInstance();
        Profile profile = callService.viewMyProfile(userId);
        blockedUserNameTextView.setText(profile.getUsername());
        Glide.with(context)
                .load(profile.getAvatar())
                .placeholder(R.drawable.placeholder_avatar) // Hình ảnh hiển thị khi đang tải
                .error(R.drawable.error_avatar) // Hình ảnh hiển thị khi tải thất bại
                .into(avatarImageView);

        return convertView;
    }
}
