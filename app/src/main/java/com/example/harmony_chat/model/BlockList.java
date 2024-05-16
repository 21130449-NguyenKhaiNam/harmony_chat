package com.example.harmony_chat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BlockList {
    private long id;


    private Room room;

    private User user;
}
