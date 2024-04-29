package com.app.harmony_chat.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Dùng cho việc định hình các thông tin
 */
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class Infomation {
    private int code;
    private String content;
}
