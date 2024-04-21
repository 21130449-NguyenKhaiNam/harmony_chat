package com.app.harmony_chat.services.auth;

import com.app.harmony_chat.daos.account.AuthAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServices {
    @Autowired
    private AuthAccountDao dao;

    public UUID login(String username, String password) {
        return dao.selectId(username, password);
    }
}
