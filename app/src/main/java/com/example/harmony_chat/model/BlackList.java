package com.example.harmony_chat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BlackList {
    private long id;
    private User user;
    private User blockUser;
}
