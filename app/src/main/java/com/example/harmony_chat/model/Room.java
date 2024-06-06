package com.example.harmony_chat.model;

import java.time.LocalDate;
import java.util.UUID;

public class Room {
    private long id;

    private String published;

    private boolean visible;

    // Đường dẫn
    private String image;

    // Đường dẫn
    private String background;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
