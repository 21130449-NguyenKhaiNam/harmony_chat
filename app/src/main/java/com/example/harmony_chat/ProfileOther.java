package com.example.harmony_chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.harmony_chat.model.Profile;
import com.example.harmony_chat.service.CallService;
import com.example.harmony_chat.util.RxHelper;

import java.util.List;

public class ProfileOther extends AppCompatActivity {

    ImageButton backBtn;

    Button relationshipBtn, sendMessageBtn;

    boolean isFriend = false;
    boolean isSendAddFriend = false;

    int colorPrimarySky;
    int colorWhite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_other);
        String name = getIntent().getStringExtra("name");
        colorPrimarySky = ContextCompat.getColor(this, R.color.primary_sky);
        colorWhite = ContextCompat.getColor(this, R.color.primary_white);

        backBtn = findViewById(R.id.btn_back);
        relationshipBtn = findViewById(R.id.btn_relationship);
        sendMessageBtn = findViewById(R.id.btn_message);

//        thiet lap trang thai ban dau cho nut relation ship
//        chua ket ban va chua gui ket ban

        setRelationshipBtnState(isFriend, isSendAddFriend);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        relationshipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFriend == false) {
                    if(isSendAddFriend == false) {
                        sendAddFriend();
                        isSendAddFriend = true;
                    } else {
                        cancelAddFriend();
                        isSendAddFriend = false;
                    }
                } else {
                    deleteFiend();
                    isFriend = false;
                }
            }
        });

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    public void back(){
        finish();
    }


//    thiet lap trang thai ban dau cho nut relationship
    public void setRelationshipBtnState(boolean isFriend, boolean isSendAddFriend) {
        if(isFriend) { //neu da la ban
            relationshipBtn.setText(R.string.friend);
            relationshipBtn.setTextColor(colorWhite);
            relationshipBtn.setBackgroundResource(R.drawable.button_active);
        } else { //chua la ban
            if(isSendAddFriend) {  // da gui ket ban
                relationshipBtn.setText(R.string.cancelAddFriend);
                relationshipBtn.setTextColor(colorPrimarySky);
                relationshipBtn.setBackgroundResource(R.drawable.background_border);

            } else { // chua gui ket ban
                relationshipBtn.setText(R.string.addFriend);
                relationshipBtn.setTextColor(colorWhite);
                relationshipBtn.setBackgroundResource(R.drawable.button_active);
            }

        }
    }


//    gui ket ban
    public void sendAddFriend() {
        relationshipBtn.setText(R.string.cancelAddFriend);
        relationshipBtn.setTextColor(colorPrimarySky);
        relationshipBtn.setBackgroundResource(R.drawable.background_border);
    }

//    huy gui ket ban
    public void cancelAddFriend() {
        relationshipBtn.setText(R.string.addFriend);
        relationshipBtn.setTextColor(colorWhite);
        relationshipBtn.setBackgroundResource(R.drawable.button_active);
    }

//    huy ket ban
    public void deleteFiend() {
        //hien ra hop thoai xac nhan
    }

    public void sendMessage() {
        Intent intent = new Intent(this, ChatScreen.class);
        startActivity(intent);
    }
}