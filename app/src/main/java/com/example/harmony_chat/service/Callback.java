package com.example.harmony_chat.service;

import android.util.Log;

import com.example.harmony_chat.model.Information;

import retrofit2.Call;
import retrofit2.Response;

public class Callback implements retrofit2.Callback<Information> {
    private static Callback thisClass;
    private Information info;

    private Callback() {
        info = new Information();
    }

    public static Callback getInstance() {
        return thisClass == null ? (thisClass = new Callback()) : thisClass;
    }

    public Information getInfo() {
        return info;
    }

    @Override
    public void onResponse(Call<Information> call, Response<Information> response) {
        info.setCode(response.body().getCode());
        info.setJson(response.body().getJson());
        Log.d("obj", info.toString());
    }

    @Override
    public void onFailure(Call<Information> call, Throwable throwable) {
        Log.d("ERROR", "Some error");
    }
}
