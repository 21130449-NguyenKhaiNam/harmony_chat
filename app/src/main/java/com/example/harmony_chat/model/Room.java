package com.example.harmony_chat.model;

import java.time.LocalDate;
import java.util.UUID;

public class Room {
    private UUID id;

    private LocalDate published;

    private boolean visible;

    // Đường dẫn
    private String image;

    // Đường dẫn
    private String background;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getPublished() {
        return published;
    }

    public void setPublished(LocalDate published) {
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
