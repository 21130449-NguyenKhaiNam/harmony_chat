package com.app.harmony_chat.utils.auths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    @Autowired
    private BCryptPasswordEncoder encoder;

    // Mã hóa password bằng thư viện BCrypt
    public String hashPass(String password) {
        return encoder.encode(password);
    }
}
