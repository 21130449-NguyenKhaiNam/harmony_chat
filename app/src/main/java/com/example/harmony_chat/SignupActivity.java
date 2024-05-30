package com.example.harmony_chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    EditText editEmail, editUsername, editPassword, editRepassword;
    ImageView avatar;
    Button signupBtn, gotoLoginBtn;

    boolean isPasswordVisible, isRePasswordVisible;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        hideSystemUI();

        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editRepassword = findViewById(R.id.editRePassword);

        avatar = findViewById(R.id.avatarImg);

        signupBtn = findViewById(R.id.signupBtn);
        gotoLoginBtn = findViewById(R.id.gotoLoginBtn);

        isPasswordVisible = false;
        isRePasswordVisible = false;

        gotoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin();
            }
        });


//        an hien mat khau
        editPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (editPassword.getRight() - editPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (isPasswordVisible) {
                            editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            editPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, R.drawable.unhide, 0);
                            isPasswordVisible = false;
                        } else {
                            editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            editPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, R.drawable.hide, 0);
                            isPasswordVisible = true;
                        }
                        return true;
                    }
                }
                return false;

            }
        });

        editRepassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (editRepassword.getRight() - editRepassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (isRePasswordVisible) {
                            editRepassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            editRepassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, R.drawable.unhide, 0);
                            isRePasswordVisible = false;
                        } else {
                            editRepassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            editRepassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, R.drawable.hide, 0);
                            isRePasswordVisible = true;
                        }
                        return true;
                    }
                }
                return false;

            }
        });

    }

    public void gotoLogin() {
        finish();
    }

    private void hideSystemUI() {
        // Ẩn thanh trạng thái và thanh điều hướng
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }
}
