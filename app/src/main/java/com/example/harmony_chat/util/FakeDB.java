package com.example.harmony_chat.util;

import com.example.harmony_chat.model.User;

public class FakeDB {
    final static String EMAIL = "khuongvo2105@gmail.com", PASSWORD = "khuongvo2105";

    public static FakeDB getInstance(){
        return new FakeDB();
    }

    public User loginAccount(String email, String password){
        User user = new User("myIdIs001");
        return user;
    }
}
