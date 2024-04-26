package com.example.harmony_chat;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class Chat_Screen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_chat_screen);

        String img_url = "https://images.unsplash.com/photo-1627087820883-7a102b79179a?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
        ImageView imageView = findViewById(R.id.img_avatar);

        Picasso.get()
                .load(img_url)
                .into(imageView);
    }
}
