package com.example.harmony_chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.harmony_chat.model.Profile;
import com.example.harmony_chat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private EditText editEmail, editPassword;
    private ImageView avatar;
    private Button loginBtn, gotoSignupBtn;
    private ImageButton btn_facebook, btn_github, btn_google;
    private TextView forgetPasswordBtn;
    private boolean isPasswordVisible;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private User myUser;
    private Profile myProfile;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ẩn cả thanh trạng thái và thanh điều hướng
        hideSystemUI();

        mAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.editEmailLogin);
        editPassword = findViewById(R.id.editPassword);

        avatar = findViewById(R.id.avatarImg);
        forgetPasswordBtn = findViewById(R.id.forgetPasswordBtn);
        loginBtn = findViewById(R.id.loginBtn);
        gotoSignupBtn = findViewById(R.id.gotoSignupBtn);

        btn_facebook = findViewById(R.id.facebookBtn);
        btn_github = findViewById(R.id.githubBtn);
        btn_google = findViewById(R.id.googleBtn);

        isPasswordVisible = false;

//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gotoMain();
//            }
//        });

        loginBtn.setOnClickListener(e -> {
            String email = editEmail.getText().toString().trim(),
                    password = editPassword.getText().toString().trim();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user = mAuth.getCurrentUser();
                    if (user != null) {
                        String user_id = user.getUid();
                        FirebaseFirestore.getInstance()
                                .collection("PROFILES")
                                .whereEqualTo("user.id", user_id)
                                .get()
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        for (DocumentSnapshot document : task1.getResult()) {
                                            myProfile = document.toObject(Profile.class);

                                            Intent intent = new Intent(this, MainActivity.class);
                                            intent.putExtra("profile", myProfile);
                                            startActivity(intent);
                                        }
                                    } else Log.e("FIRESTORE", task1.getException().toString());
                                });
                    }
                } else Log.e("FIRESTORE", task.getException().toString());
            });
        });

        gotoSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSignup();
            }
        });

        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoForgetPassword();
            }
        });

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

        btn_facebook.setOnClickListener(e -> {
            futureFeatures();
        });
        btn_google.setOnClickListener(e -> {
            futureFeatures();
        });
        btn_github.setOnClickListener(e -> {
            futureFeatures();
        });
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

    private void futureFeatures() {
        Toast.makeText(this, "Tính năng sẽ được cập nhật trong phiên bản sau", Toast.LENGTH_SHORT).show();
    }

    public void gotoSignup() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void gotoForgetPassword() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    public void gotoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
