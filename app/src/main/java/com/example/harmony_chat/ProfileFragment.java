package com.example.harmony_chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    LinearLayout settingBtn, profileBtn;
    TextView setting, profile;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_settings, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settingBtn = view.findViewById(R.id.settingBtn);
        profileBtn = view.findViewById(R.id.profileBtn);
        setting = view.findViewById(R.id.text_setting);
        profile = view.findViewById(R.id.text_profile);

        settingBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoSetting();
            }
        });

        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoSetting();
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoMyProfile();
            }
        });

        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoMyProfile();
            }
        });
    }

    public void gotoSetting() {
        Intent intent = new Intent(getActivity(), SettingScreen.class);
        startActivity(intent);
    }

    public void gotoMyProfile() {
        Intent intent = new Intent(getActivity(), Profile.class);
        startActivity(intent);
    }

}

