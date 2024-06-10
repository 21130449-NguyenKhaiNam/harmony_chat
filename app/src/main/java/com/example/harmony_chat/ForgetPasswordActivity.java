package com.example.harmony_chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.harmony_chat.JavaMail.JavaMailAPI;
import com.example.harmony_chat.model.User;
import com.example.harmony_chat.service.CallService;
import com.example.harmony_chat.util.InputHelper;
import com.example.harmony_chat.util.RxHelper;
import com.squareup.picasso.Picasso;

import java.util.Random;
public class ForgetPasswordActivity extends AppCompatActivity {
    EditText editEmail;
    ImageView avatar;
    Button forgetPasswordBtn;

    TextView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        hideSystemUI();
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

        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword();
            }
        });

        loadLogo();
    }

    private void loadLogo() {
        Picasso.get()
                .load(R.drawable.logo_black)
                .into((ImageView) findViewById(R.id.avatarImg));
    }

    public void forgetPassword() {
        boolean isOK= true;
        if(InputHelper.checkNull(editEmail)) isOK = false;

        if(isOK ==true) {
            String email = editEmail.getText().toString();
            RxHelper.performImmediately(() -> {
                CallService.getInstance().forgetAccount(email);
                runOnUiThread(() -> {
                    Toast.makeText(ForgetPasswordActivity.this, "Đã gửi thông tin tới tài khoản", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                });
            });
        }
    }

    public void gotoLogin() {
        finish();
    }

    private void hideSystemUI() {
        final View decorView = getWindow().getDecorView();

        Runnable setSystemUiVisibility = new Runnable() {
            @Override
            public void run() {
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                );
            }
        };

        setSystemUiVisibility.run();

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    setSystemUiVisibility.run();
                }
            }
        });
    }
}
