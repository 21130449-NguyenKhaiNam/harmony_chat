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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.harmony_chat.model.User;
import com.example.harmony_chat.service.CallService;
import com.example.harmony_chat.util.RxHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {
    EditText editEmail, editUsername, editPassword, editRepassword;
    TextView txtPasswordError;
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
        txtPasswordError = findViewById(R.id.txtPasswordError);

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

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loadLogo();
    }

    private void loadLogo() {
        Picasso.get()
                .load(R.drawable.logo_black)
                .into((ImageView) findViewById(R.id.avatarImg));
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

    public void signup() {
        boolean isOK = true;
        if (checkNull(editEmail) || editEmail.getText().toString().contains(" ")) isOK = false;
        if (checkNull(editUsername)) isOK = false;
        if (checkNull(editPassword)) isOK = false;
        if (checkNull(editRepassword)) isOK = false;

        if (!editPassword.getText().toString().equals(editRepassword.getText().toString())) {
            isOK = false;
            txtPasswordError.setText("Mật khẩu không trùng khớp");
            changeEditStroke(editPassword, Color.RED);
            changeEditStroke(editRepassword, Color.RED);
        } else {
            txtPasswordError.setText("");
            changeEditStroke(editPassword, Color.TRANSPARENT);
            changeEditStroke(editRepassword, Color.TRANSPARENT);
        }

        if (isOK) {
            String email = editEmail.getText().toString();
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            RxHelper.performImmediately(() -> {
                User user = CallService.getInstance().registerAccount(email, password, username);
                runOnUiThread(() -> {
                    if (user.getId() != null) {
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.putExtra("status", "signupSuccessful");
                        setResult(RESULT_OK, intent);

                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "Tài khoản đã tồn tại hoặc thông tin không chính xác!!!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }


    }

    public boolean checkNull(EditText e) {
        boolean re = false;
        if (e.getText().toString().equals("")) {
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
