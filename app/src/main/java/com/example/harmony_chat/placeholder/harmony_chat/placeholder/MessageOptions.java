package com.example.harmony_chat.placeholder.harmony_chat.placeholder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.harmony_chat.R;

public class MessageOptions extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_message_options, container, false);

//        my code

        return view;
    }

    public void changeVisible(boolean hide) {
        if (hide) {
            view.setVisibility(View.GONE);
        } else view.setVisibility(View.VISIBLE);
    }
}