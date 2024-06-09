package com.example.harmony_chat.model;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String email;
    private String password;

    public User() {
    }

    public User(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static String encodePwd(String pwd) {
        String code ="";
        String temp = "";
        for(int i=pwd.length() - 1 ; i>=0;i--){
            code+= (int) pwd.charAt(i);
        }

        return code;
    }
}
