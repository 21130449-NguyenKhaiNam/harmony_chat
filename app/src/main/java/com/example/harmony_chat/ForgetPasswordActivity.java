package com.example.harmony_chat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText editUsername, editEmail;
    ImageView avatar;
    Button forgetPasswordBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        editUsername = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);

        avatar = findViewById(R.id.avatarImg);

        forgetPasswordBtn = findViewById(R.id.forgetPasswordBtn);

    }
}
