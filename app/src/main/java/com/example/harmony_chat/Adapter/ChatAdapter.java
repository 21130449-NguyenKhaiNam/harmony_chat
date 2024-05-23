package com.example.harmony_chat.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.harmony_chat.Item.ChatItem;
import com.example.harmony_chat.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatItem> chatList;
    private SelectListener selectListener;

    public ChatAdapter(List<ChatItem> chatList, SelectListener selectListener) {
        this.chatList = chatList;
        this.selectListener = selectListener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatItem chatItem = chatList.get(position);
        holder.nameTextView.setText(chatItem.getTitle());
        holder.messageTextView.setText(chatItem.getMessage());
        holder.timeTextView.setText(chatItem.getTime());

        Picasso.get()
                .load("https://images.unsplash.com/photo-1627087820883-7a102b79179a?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                .into(holder.avatarImageView);

//        set su kien cho recycleview
        holder.linearLayout.setOnClickListener(v -> {
            selectListener.onItemClicked(chatList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView avatarImageView;
        TextView nameTextView, messageTextView, timeTextView;
        LinearLayout linearLayout;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            linearLayout = itemView.findViewById(R.id.main_container_chat);
        }
    }
}