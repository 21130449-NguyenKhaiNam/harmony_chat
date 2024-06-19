package com.example.harmony_chat.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.harmony_chat.R;
import com.example.harmony_chat.model.Profile;
import com.squareup.picasso.Picasso;

public class AndroidUtil {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void loadImage(String srcImg, ImageView view, int placeholder, int error) {
        String src = (srcImg != null && !srcImg.trim().isEmpty()) ?
                srcImg :
                "https://images.unsplash.com/photo-1627087820883-7a102b79179a?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
        Picasso.get()
                .load(src)
//                .resize(view.getWidth(), view.getHeight())
//                .centerCrop()
                .placeholder(placeholder)
                .error(error)
                .into(view);
    }

    public static void loadImage(String srcImg, ImageView view) {
        loadImage(srcImg, view, R.drawable.account, R.drawable.account);
    }
}
