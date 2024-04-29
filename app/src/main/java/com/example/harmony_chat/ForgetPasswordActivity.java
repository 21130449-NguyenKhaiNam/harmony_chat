package com.example.harmony_chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText editUsername, editEmail;
    ImageView avatar;
    Button forgetPasswordBtn;

    TextView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        editUsername = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);

        avatar = findViewById(R.id.avatarImg);

        forgetPasswordBtn = findViewById(R.id.forgetPasswordBtn);

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin();
            }
        });
    }

    public void gotoLogin() {
        finish();

    }
}
