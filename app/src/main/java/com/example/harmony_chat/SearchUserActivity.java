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

import com.example.harmony_chat.model.Profile;
import com.example.harmony_chat.service.CallService;
import com.example.harmony_chat.util.RxHelper;

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
                        RxHelper.performImmediately(() -> {
                            List<Profile> profileList = CallService.getInstance().searchUser(edtSearch.getText().toString());
                            runOnUiThread(() -> {
                                if(profileList == null || profileList.isEmpty()) {

                                } else {

                                }
                            });
                        });
                        return true;
                    }
                }
                return false;

            }
        });
    }

    public void back(){
        finish();;
    }

    public void gotoOtherProfile() {
        Intent intent = new Intent(this, ProfileOther.class);
        intent.putExtra("name", edtSearch.getText().toString());
        startActivity(intent);
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
