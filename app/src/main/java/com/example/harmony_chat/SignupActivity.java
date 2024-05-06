package com.example.harmony_chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    EditText editEmail, editUsername, editPassword, EditRepassword;
    ImageView avatar;
    Button signupBtn, gotoLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        EditRepassword = findViewById(R.id.editRePassword);

        avatar = findViewById(R.id.avatarImg);

        signupBtn = findViewById(R.id.signupBtn);
        gotoLoginBtn = findViewById(R.id.gotoLoginBtn);

        gotoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin();
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMainActivity();
            }
        });

    }

    public void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }
    public void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
