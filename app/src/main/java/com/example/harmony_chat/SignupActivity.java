package com.example.harmony_chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.harmony_chat.model.Profile;
import com.example.harmony_chat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    private EditText editEmail, editUsername, editPassword, editRepassword;
    private ImageView avatar;
    private Button signupBtn, gotoLoginBtn;
    private FirebaseAuth mAuth;
    private boolean isPasswordVisible, isRePasswordVisible;
    private User user;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        hideSystemUI();

        mAuth = FirebaseAuth.getInstance();

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
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editPassword.getRight() - editPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
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
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editRepassword.getRight() - editRepassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
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

//        Dang ky tai khoan nguoi dung
        signupBtn.setOnClickListener(e -> {
            String email = editEmail.getText().toString().trim(),
                    password = editPassword.getText().toString().trim(),
                    username = editUsername.getText().toString().trim(),
                    repassword = editRepassword.getText().toString().trim();
//            Chua xu ly validation cho cac su kien
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Email or Password is empty!", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
//      success
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

//  Luu username cua nguoi dung dua vao UUID
                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task1 -> {
                            if (task.isSuccessful()) {
                                String user_id = mAuth.getCurrentUser().getUid(); // get UUID cua User
                                user = new User(user_id, null, null);
                                Profile profile = new Profile();
                                profile.setUser(user);
                                profile.setUsername(username);
                                FirebaseFirestore.getInstance()
                                        .collection("PROFILES")
                                        .add(profile)
                                        .addOnCompleteListener(task2 -> {
                                            if (task2.isSuccessful()) {
                                                Toast.makeText(this, "Username is saved!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Log.e("FIRESTORE", task2.getException().toString());
                                            }
                                        });
                            }
                        });
                    } else {
//      fail
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
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
