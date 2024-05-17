package com.example.harmony_chat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

public class ChatScreen extends AppCompatActivity {

    private boolean isFragmentDisplayed = false;
    private Fragment reactsFragment, messageOptionsFragment;
    private View options, reacts;
    private TextView txtMessage;

    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_chat_screen);

        hideSystemUI();

        String img_url = "https://images.unsplash.com/photo-1627087820883-7a102b79179a?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
        ImageView imageView = findViewById(R.id.img_avatar);

        Picasso.get()
                .load(img_url)
                .into(imageView);

        txtMessage = findViewById(R.id.message);
        backBtn = findViewById(R.id.backBtn);
        reactsFragment = getSupportFragmentManager().findFragmentById(R.id.reacts);
        messageOptionsFragment = getSupportFragmentManager().findFragmentById(R.id.message_options_fragment);

        txtMessage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showFragments();
                return true;
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

    }

    private void showFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (reactsFragment != null) {
            transaction.show(reactsFragment);
        }

        if (messageOptionsFragment != null) {
            transaction.show(messageOptionsFragment);
        }

        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (isFragmentsVisible()) {
            hideFragments();
        } else {
            super.onBackPressed();
        }
    }

    private boolean isFragmentsVisible() {
        return reactsFragment != null && reactsFragment.isVisible() ||
                messageOptionsFragment != null && messageOptionsFragment.isVisible();
    }

    private void hideFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (reactsFragment != null) {
            transaction.hide(reactsFragment);
        }

        if (messageOptionsFragment != null) {
            transaction.hide(messageOptionsFragment);
        }

        transaction.commit();
    }

    public void back() {
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
