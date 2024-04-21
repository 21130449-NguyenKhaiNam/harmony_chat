package com.app.harmony_chat.apis.account;

import com.app.harmony_chat.services.auth.AuthServices;
import com.app.harmony_chat.utils.auths.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/account")
public class Login {
    @Autowired
    private AuthServices services;

    @PostMapping(path = "/login")
    public UUID login(@PathVariable String username, @PathVariable String password) {
        return services.login(username, password);
    }
}
