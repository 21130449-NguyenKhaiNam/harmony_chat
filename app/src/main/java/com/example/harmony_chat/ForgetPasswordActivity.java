package com.example.harmony_chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.harmony_chat.JavaMail.JavaMailAPI;
import com.example.harmony_chat.model.User;
import com.example.harmony_chat.util.InputHelper;

import java.util.Random;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText editUsername, editEmail;
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
    }

    public void forgetPassword() {
        boolean isOK= true;
        if(InputHelper.checkNull(editEmail)) isOK = false;

        if(isOK ==true) {
            String email = editEmail.getText().toString();
            Random rand = new Random();
            String pwd ="";
            for(int i=0;i<6; i++) {
                pwd+= String.valueOf(rand.nextInt(9)+1);
            }
            String pwdEncoded = User.encodePwd(pwd);
//            cap  nhat mau khau moi cho nguoi dung vao database
//           pwdEncoded: day la mat khau da duoc ma hoa, dung de them vao database




//            gui mat khau moi toi nguoi dung
            String title="Quên mật khẩu Harmony chat";
            String message = "Mật khẩu mới của bạn là: " + pwd +"\nLưu ý: Đây là mật khẩu hệ thống tạo tự động. Hãy thay đổi mật khẩu khác để bạn có thể dễ nhớ hơn.";
            JavaMailAPI javaMailAPI = new JavaMailAPI(this,email,title,message);
            javaMailAPI.execute();

        }
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
