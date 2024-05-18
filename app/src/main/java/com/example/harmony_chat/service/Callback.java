package com.example.harmony_chat.service;

import android.util.Log;

import com.example.harmony_chat.model.Information;

import retrofit2.Call;
import retrofit2.Response;

public class Callback implements retrofit2.Callback<Information> {
    private static Callback thisClass;
    private Information info;

    public Callback() {
        info = new Information();
    }

    public Information getInfo() {
        return info;
    }

    @Override
    public void onResponse(Call<Information> call, Response<Information> response) {
        if (response.isSuccessful() && response.body() != null) {
            info.setCode(response.body().getCode());
            info.setJson(response.body().getJson());
            // In ra trạng thái nhận được
            Log.d("Trạng thái nhận được", info.toString());
        }
    }

    @Override
    public void onFailure(Call<Information> call, Throwable throwable) {
        Log.d("ERROR", "Some error");
    }
}
