package com.example.harmony_chat;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class ProfileSettingsFragment extends Fragment {
    private View view;

    public ProfileSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_settings, container, false);

        view.findViewById(R.id.text_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start settings activity
                Intent intent = new Intent(getActivity(), SettingScreen.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.text_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start profile settings activity
                Intent intent = new Intent(getActivity(), Profile.class);
                startActivity(intent);
            }
        });

        return view;
    }
    public void changeVisible(boolean hide) {
        if (hide) {
            view.setVisibility(View.GONE);
        } else view.setVisibility(View.VISIBLE);
    }

}

