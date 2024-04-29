package com.app.harmony_chat.utils.auths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Thực hiện mã hóa một chiều
 */
@Component
public class PasswordEncoder {
    private BCryptPasswordEncoder encoder;

    public PasswordEncoder() {
        encoder = new BCryptPasswordEncoder();
    }

    // Mã hóa password bằng thư viện BCrypt
    public String hashPass(String password) {
        return encoder.encode(password);
    }

    // Kiểm tra mật khẩu có chuẩn xác
    public boolean isCorrect(String password, String hashPass) {
        return encoder.matches(password, hashPass);
    }
}
