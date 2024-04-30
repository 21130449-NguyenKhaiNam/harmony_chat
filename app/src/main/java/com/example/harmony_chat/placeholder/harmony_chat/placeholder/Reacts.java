package com.example.harmony_chat.placeholder.harmony_chat.placeholder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.harmony_chat.R;

public class Reacts extends Fragment {
    private View view;

    public Reacts() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reacts, container, false);
        return view;
    }

    public void changeVisible(boolean hide) {
        if (hide) {
            view.setVisibility(View.GONE);
        } else view.setVisibility(View.VISIBLE);
    }
}