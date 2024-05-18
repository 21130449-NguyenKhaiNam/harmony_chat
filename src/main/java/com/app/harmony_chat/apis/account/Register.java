package com.app.harmony_chat.apis.account;

import com.app.harmony_chat.configs.DefinePath;
import com.app.harmony_chat.configs.DefinePropertyJson;
import com.app.harmony_chat.models.Profile;
import com.app.harmony_chat.models.User;
import com.app.harmony_chat.services.auth.AuthServices;
import com.app.harmony_chat.utils.infomation.MapperJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  Bắt sự kiện đăng ký tài khoản
 */
@RestController
@RequestMapping(path = DefinePath.ACCOUNT)
public class Register {

    @Autowired
    private AuthServices services;
    @Autowired
    private MapperJson mapper;
    @PostMapping(path = DefinePath.ACCOUNT_REGISTER)
    public String register(@RequestBody Map<String, String> json) {
        List<String> params = mapper.getParam(json,DefinePropertyJson.EMAIL,DefinePropertyJson.PASSWORD , DefinePropertyJson.USERNAME);
        User newUser = new User(params.get(0), params.get(1));
        Profile newProfile = new Profile(newUser, params.get(2));
        return mapper.mapToJson(services.addUser(newProfile));
    }
}
