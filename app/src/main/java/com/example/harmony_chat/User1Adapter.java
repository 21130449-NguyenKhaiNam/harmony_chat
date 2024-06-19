package com.example.harmony_chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harmony_chat.model.Profile;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harmony_chat.service.LoadImgService;

import java.util.ArrayList;
import java.util.List;

import model.User1;

public class User1Adapter extends RecyclerView.Adapter<User1Adapter.User1ViewHolder> {
    private Context mContext;
    private List<Profile> users;
    private OnDeleteItemClickListener onDeleteItemClickListener;
    private OnItemClickListener onItemClickListener;

    public User1Adapter(Context mContext, List<Profile> users) {
        this.mContext = mContext;
        this.users = users != null ? users : new ArrayList<>();
    }

    public interface OnDeleteItemClickListener {
        void onDeleteItemClick(int position);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnDeleteItemClickListener(OnDeleteItemClickListener listener) {
        this.onDeleteItemClickListener = listener;
    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setData(List<Profile> users) {
        if (users == null) {
            return;
        }
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    public Profile getUser(int ind) {
        return users.get(ind);
    }

    @NonNull
    @Override
    public User1ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_searched_item, parent, false);
        return new User1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull User1ViewHolder holder, int position) {
        Profile user = users.get(position);
        if (user == null) {
            return;
        }
        LoadImgService.getInstance().injectImage(user.getAvatar(), holder.userAvatar);
        holder.userName.setText(user.getUsername());

        if (onDeleteItemClickListener != null) {
            holder.deleteBtn.setOnClickListener(v -> onDeleteItemClickListener.onDeleteItemClick(holder.getAdapterPosition()));
        }

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(holder.getAdapterPosition()));
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class User1ViewHolder extends RecyclerView.ViewHolder {
        ImageView userAvatar;
        TextView userName;
        ImageButton deleteBtn;

        public User1ViewHolder(@NonNull View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.userAvatar);
            userName = itemView.findViewById(R.id.userName);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
