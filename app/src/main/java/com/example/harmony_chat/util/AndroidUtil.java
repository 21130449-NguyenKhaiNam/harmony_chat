package com.example.harmony_chat.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    public static void showError(String label, String message) {
        Log.e("HarmonyChat_"+label, message);
    }
    public static void showError(String message){
        showError("UndefinedError", message);
    }

    public static void loadImage(String srcImg, ImageView view, int placeholder, int error, int width, int height) {
        String src = (srcImg != null && !srcImg.trim().isEmpty()) ?
                srcImg :
                "https://images.unsplash.com/photo-1627087820883-7a102b79179a?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
        try {
            Picasso.get()
                    .load(src)
                    .resize(width,height)
                    .centerCrop()
                    .placeholder(placeholder)
                    .error(error)
                    .into(view);
        } catch (IllegalStateException e) {
            showError("Picasso", e.getMessage());
        }
    }

    public static void loadImage(String srcImg, ImageView view) {
        loadImage(srcImg, view, R.drawable.account, R.drawable.account,40, 40);
    }

    /**
     * Lấy ID của người dùng từ SharedPreferences.
     *
     * Phương thức này truy cập vào SharedPreferences với tên "user",
     * lấy giá trị của khóa "id", loại bỏ dấu ngoặc kép và khoảng trắng dư thừa xung quanh ID.
     *
     * @param context Ngữ cảnh (Context) của ứng dụng, được sử dụng để truy cập vào SharedPreferences.
     * @return ID của người dùng dưới dạng chuỗi (String), hoặc null nếu không tìm thấy ID.
     */
    public static String getUserId(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        return sharedPreferences.getString("id",null).replaceAll("\"", "").trim();
    }

    public static String getChatname(){
        return "";
    }
}
