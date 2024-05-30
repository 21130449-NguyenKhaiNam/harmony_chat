package com.example.harmony_chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.harmony_chat.JavaMail.JavaMailAPI;
import com.example.harmony_chat.model.User;
import com.example.harmony_chat.service.CallService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText editEmail, editPassword;
    ImageView avatar;
    Button loginBtn, gotoSignupBtn;
    private ImageButton btn_facebook, btn_github,btn_google;
    TextView forgetPasswordBtn;
    String status;
    boolean isPasswordVisible;

//    dang nhap bang google
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ẩn cả thanh trạng thái và thanh điều hướng
        hideSystemUI();

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        avatar = findViewById(R.id.avatarImg);
        forgetPasswordBtn = findViewById(R.id.forgetPasswordBtn);
        loginBtn = findViewById(R.id.loginBtn);
        gotoSignupBtn = findViewById(R.id.gotoSignupBtn);

        btn_facebook = findViewById(R.id.facebookBtn);
        btn_github = findViewById(R.id.githubBtn);
        btn_google = findViewById(R.id.googleBtn);

        isPasswordVisible =false;

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
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

        btn_facebook.setOnClickListener(e->{
            futureFeatures();
        });
        btn_google.setOnClickListener(e->{
            futureFeatures();
        });
        btn_github.setOnClickListener(e->{
            futureFeatures();
        });

//        dang nhap bang google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        btn_google.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loginWithGoogle();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String status = (String) data.getStringExtra("status");
                if (status != null && status.equalsIgnoreCase("signupSuccessful")) {
                    Toast.makeText(LoginActivity.this, "Đăng ký thành công, vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                }
            }
        } else if(requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
//            finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } catch (ApiException e) {
               Toast.makeText(getApplicationContext(),"Có gì đó hoạt động sai",Toast.LENGTH_SHORT).show();

            }
        } else if(requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String status = (String) data.getStringExtra("status");
                if (status != null && status.equalsIgnoreCase("sendSuccessful")) {
                    Toast.makeText(LoginActivity.this, "Đã gửi mật khẩu mới về email của bạn", Toast.LENGTH_SHORT).show();
                }
            }

        }
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
        Toast.makeText(this,"Tính năng sẽ được cập nhật trong phiên bản sau", Toast.LENGTH_SHORT).show();
    }

    public void loginWithGoogle() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent,1000);

    }

    public void gotoSignup() {
        Intent intent = new Intent(this, SignupActivity.class);
//        startActivity(intent);
        startActivityForResult(intent, 1);

    }

    public void gotoForgetPassword() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivityForResult(intent, 2);

    }

    public void gotoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public int checkLogin() {
        int re =0;
        String password = editPassword.getText().toString();
//        String email = editUsername
        return re;
    }

    public void login() {
        boolean isOK = true;
        if(checkNull(editEmail)) isOK = false;
        if(checkNull(editPassword)) isOK = false;


        if(isOK) {
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();
//            ma hoa password
            password = User.encodePwd(password);
            User u = CallService.getInstance().loginAccount(email,password);
            if(u.getEmail()==null) {
                changeEditStroke(editEmail,Color.RED);
                changeEditStroke(editPassword,Color.RED);
                Toast.makeText(LoginActivity.this, "Thông tin đăng nhập không chính xác" , Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

//
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
        }
    }

    public boolean checkNull(EditText e) {
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

    public int changeEditStroke(EditText e, int color) {
        Drawable background = e.getBackground();
        if (background instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) background;
            gradientDrawable.setStroke(4, color);
            return 1;
        }
        return 0;

    }
}
