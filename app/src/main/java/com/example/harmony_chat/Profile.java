package com.example.harmony_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.harmony_chat.model.Country;
import com.example.harmony_chat.model.User;
import com.example.harmony_chat.service.CallService;

import java.time.LocalDate;
import java.util.List;

public class Profile extends AppCompatActivity {
    private ImageButton btnBack;
    private TextView textUsername, textDescription, textEmail,
            textPhone, textDob, textSex, textAdress;
    private com.example.harmony_chat.model.Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        hideSystemUI();

        // Ánh xạ phần tử
        btnBack = findViewById(R.id.btn_back);

        textUsername = findViewById(R.id.text_username);
        textDescription = findViewById(R.id.text_description);
        textEmail = findViewById(R.id.user_email);
        textPhone = findViewById(R.id.user_phone);
        textDob = findViewById(R.id.user_dob);
        textSex = findViewById(R.id.user_sex);
        textAdress = findViewById(R.id.user_address);

        // Lấy dữ liệu
        profile = CallService.getInstance().viewMyProfile("316199df-4227-4efb-8af3-42e4a5dd8c4a");
        List<com.example.harmony_chat.model.Profile> listFriends = CallService.getInstance().getMyListFriends("316199df-4227-4efb-8af3-42e4a5dd8c4a");

        work();
    }

    // Luồng thực hiện hàm
    private void work() {
        setupButton();
        injectData();
    }

    // Truyền dữ liệu vào
    private void injectData() {
        textUsername.setText(profile.getUsername());
        textDescription.setText("Hiện chưa có");
        textSex.setText(profile.getGender() + "");
        LocalDate dob = profile.getDob();
        textDob.setText(dob == null ? "" : dob.toString());
        Country country = profile.getCountry();
        textAdress.setText(country == null ? "" : country.toString());
        textPhone.setText(profile.getPhone());
        User user = profile.getUser();
        textEmail.setText(user == null ? "" : user.getEmail());
    }

    private void setupButton() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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