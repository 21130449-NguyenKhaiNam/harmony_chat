package com.example.harmony_chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import model.User1;

public class SearchUserActivity extends AppCompatActivity {

    RecyclerView rcvUsers;
    User1Adapter userAdapter;

    TextView backBtn;
    EditText edtSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        hideSystemUI();

        backBtn = findViewById(R.id.backBtn);
        edtSearch = findViewById(R.id.editSearchUser);

        rcvUsers = findViewById(R.id.rcv_userSearchedList);
        userAdapter = new User1Adapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvUsers.setLayoutManager(linearLayoutManager);

        userAdapter.setData(getListUser());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (edtSearch.getRight() - edtSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        gotoOtherProfile();
                        return true;
                    }
                }
                return false;

            }
        });
    }

    public List<User1> getListUser() {
        List<User1> users = new ArrayList<>();
        User1 u1 = new User1(R.id.avatar,"To minh Nhat");
        User1 u2 = new User1(R.id.avatar,"Tm nhat");
        User1 u3 = new User1(R.id.avatar,"nhat minh");
        users.add(u1);
        users.add(u2);
        users.add(u3);
        System.out.println(users);
        return users;
    }

    public void back(){
        finish();;
    }

    public void gotoOtherProfile() {
        Intent intent = new Intent(this, ProfileOther.class);
        startActivity(intent);
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
