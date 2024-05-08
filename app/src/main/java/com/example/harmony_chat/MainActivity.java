package com.example.harmony_chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;

public class MainActivity extends AppCompatActivity {
//    ImageView userAvatar;
    FrameLayout fragmentContainer1;
    FrameLayout fragmentContainer2;
    Fragment currentFragment;
    boolean isSearchVisible = false;

    RoundedImageView avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        userAvatar = findViewById(R.id.user_avatar);
        fragmentContainer1 = findViewById(R.id.fragment_container1);
//        fragmentContainer2 = findViewById(R.id.fragment_container2);
        avatar = findViewById(R.id.user_avatar);

        // Hiển thị hoặc ẩn button tìm kiếm tùy thuộc vào trạng thái của thanh tìm kiếm
        ImageView find = findViewById(R.id.search_icon);
        find.setVisibility(isSearchVisible ? View.GONE : View.VISIBLE);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SearchFragment searchFragment = new SearchFragment();
//                isSearchVisible = true;
//                hideCurrentFragment();
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container2, searchFragment)
//                        .addToBackStack(null)
//                        .commit();
//
//                currentFragment = searchFragment;
                gotoSearchUser();
            }
        });

//        userAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ProfileSettingsFragment fragment = new ProfileSettingsFragment();
//                if (!isSearchVisible) {
//                    find.setVisibility(View.VISIBLE);
//                }
//                isSearchVisible = false;
//
//                hideCurrentFragment();
//
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container1, fragment)
//                        .addToBackStack(null)
//                        .commit();
//                currentFragment = fragment;
//            }
//        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 showSelection();
            }
        });
    }

    private void hideCurrentFragment() {
        if (currentFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .hide(currentFragment)
                    .commit();
        }
    }


//    an fragment
    public boolean onTouchEvent(MotionEvent event) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container1);
        if (fragment != null && event.getAction() == MotionEvent.ACTION_DOWN) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void gotoSearchUser(){
        Intent intent = new Intent(this, SearchUserActivity.class);
        startActivity(intent);
    }

    public void gotoMyProfile() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void showSelection() {
        ProfileFragment profileFragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container1, profileFragment)
                .addToBackStack(null)
                .commit();
    }
}