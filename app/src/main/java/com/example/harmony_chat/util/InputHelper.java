package com.example.harmony_chat.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.EditText;

public class InputHelper {
    public static boolean checkNull(EditText e) {
        boolean re= false;
        if(e.getText().toString().equals("")) {
            e.setHint("Không được để trống");
            e.setHintTextColor(Color.RED);
            changeEditStroke(e, Color.RED);
            return true;
        } else {
            e.setHint("");
            e.setHintTextColor(Color.WHITE);
            changeEditStroke(e, Color.TRANSPARENT);
        }
        return re;
    }

    public static int changeEditStroke(EditText e, int color) {
        Drawable background = e.getBackground();
        if (background instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) background;
            gradientDrawable.setStroke(4, color);
            return 1;
        }
        return 0;

    }
}
