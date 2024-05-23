package com.example.harmony_chat.service;

import android.util.Log;

import com.example.harmony_chat.model.Information;

import retrofit2.Call;
import retrofit2.Response;

public class Callback implements retrofit2.Callback<DataResponsive> {
    private Information info;

    public Callback() {
        info = new Information();
    }

    public Information getInfo() {
        return info;
    }

    @Override
    public void onResponse(Call<DataResponsive> call, Response<DataResponsive> response) {
        if (response.isSuccessful() && response.body() != null) {
            Log.i("Data", response.body().toString());
            info.setCode(response.body().getCode());
            info.setJson(response.body().getContent().toString());
            // In ra trạng thái nhận được
            Log.d("Trạng thái nhận được", info.toString());
        }
    }

    @Override
    public void onFailure(Call<DataResponsive> call, Throwable throwable) {
        Log.d("ERROR", throwable.getMessage());
    }
}
