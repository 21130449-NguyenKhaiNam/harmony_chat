package com.example.harmony_chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.harmony_chat.model.Information;
import com.example.harmony_chat.model.User;
import com.example.harmony_chat.service.ApiService;
import com.example.harmony_chat.service.CallService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText editUsername, editPassword;
    ImageView avatar;
    Button loginBtn, gotoSignupBtn;

    TextView forgetPasswordBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);

        avatar = findViewById(R.id.avatarImg);
        forgetPasswordBtn = findViewById(R.id.forgetPasswordBtn);
        loginBtn = findViewById(R.id.loginBtn);
        gotoSignupBtn = findViewById(R.id.gotoSignupBtn);

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
    }

    public void gotoSignup() {
        finish();
    }

    public void gotoForgetPassword() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }
}
