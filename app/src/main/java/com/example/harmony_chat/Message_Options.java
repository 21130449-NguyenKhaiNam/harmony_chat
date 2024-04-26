package com.example.harmony_chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Message_Options extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_message__options, container, false);

//        my code

        return view;
    }

    public void changeVisible(boolean hide) {
        if (hide) {
            view.setVisibility(View.GONE);
        } else view.setVisibility(View.VISIBLE);
    }
}