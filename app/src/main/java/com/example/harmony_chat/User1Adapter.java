package com.example.harmony_chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import model.User1;

public class User1Adapter extends RecyclerView.Adapter<User1Adapter.User1ViewhHolder> {
    Context mContext;
    List<User1> users;

    public User1Adapter(Context mContext, List<User1> users) {
        this.mContext = mContext;
        this.users = users;
    }

    private OnDeleteItemClickListener onDeleteItemClickListener;

    public interface OnDeleteItemClickListener {
        void onDeleteItemClick(int position);
    }

    public void setOnDeleteItemClickListener(OnDeleteItemClickListener listener) {
        this.onDeleteItemClickListener = listener;
    }

    public User1Adapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<User1> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public User1ViewhHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_searched_item, parent, false);
        return new User1ViewhHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull User1ViewhHolder holder, int position) {
        User1 user = users.get(position);
        if(user == null) {
            return;
        }
        holder.userAvatar.setImageResource(user.getImageID());
        holder.userName.setText(user.getName());

//        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onDeleteItemClickListener != null) {
//                    onDeleteItemClickListener.onDeleteItemClick(position);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (users!=null) {
            return users.size();
        }
        return 0;
    }

    public class User1ViewhHolder extends RecyclerView.ViewHolder {

        ImageView userAvatar;
        TextView userName;
        ImageButton deleteBtn;

        public User1ViewhHolder(@NonNull View itemView) {
            super(itemView);

            userAvatar = itemView.findViewById(R.id.userAvatar);
            userName = itemView.findViewById(R.id.userName);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);


        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
