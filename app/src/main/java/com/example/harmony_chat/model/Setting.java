package com.example.harmony_chat.model;

public class Setting {
    private int icon;
    private String name;
    private String thong_so;
    private int ic_greater;

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getThong_so() {
        return thong_so;
    }

    public int getIc_greater() {
        return ic_greater;
    }

    public Setting(int icon, String name, String thong_so, int ic_greater) {
        this.icon = icon;
        this.name = name;
        this.thong_so = thong_so;
        this.ic_greater = ic_greater;
    }
}
