package com.app.harmony_chat.apis.account;

import com.app.harmony_chat.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api/v1/account")
public class Login {
    @PostMapping()
    public User selectAccount(User inp) {
        return null;
    }
}
