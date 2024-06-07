package com.example.harmony_chat.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.harmony_chat.model.Profile;

public class AndroidUtil {
    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
