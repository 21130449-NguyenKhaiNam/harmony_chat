package com.example.harmony_chat.service;

import android.util.Log;

import com.example.harmony_chat.config.DefinePropertyJson;
import com.example.harmony_chat.config.DefineStatusResponsive;
import com.example.harmony_chat.model.Information;
import com.example.harmony_chat.model.User;
import com.example.harmony_chat.util.MapFactory;
import com.example.harmony_chat.util.RxHelper;

import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CallService {
    private static CallService service;

    private CallService() {}

    public static CallService getInstance() {
        return service == null ? (service = new CallService()) : service;
    }

    public User loginAccount(String email, String password) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.EMAIL, DefinePropertyJson.PASSWORD);
        String[] values = MapFactory.createArrayString(email, password);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        User user = new User();
        Callback callback = new Callback();
        RxHelper.performImmediately(() -> {
            ApiService.service.login(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if(code == DefineStatusResponsive.SUCCESS) {
                user.setId(content);
            } else if(code == DefineStatusResponsive.SUCCESS_BUT_NOT_CORRECT) {
                // Tìm thấy nhưng thông tin không đúng
            } else if (code == DefineStatusResponsive.SUCCESS_BUT_NOT_FOUND){
                // Không tìm thấy tài khoản
            } else if(code == DefineStatusResponsive.ERROR_CLIENT) {
                // Lỗi do người dùng hoặc lập trình viên
            } else {
                // Lỗi hệ thống
            }
        });
        return user;
    }

    public User registerAccount(String email, String password) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.EMAIL, DefinePropertyJson.PASSWORD);
        String[] values = MapFactory.createArrayString(email, password);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        return null;
    }

    public User forgetAccount(String email) {
        return null;
    }
}
