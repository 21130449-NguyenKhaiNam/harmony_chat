package com.app.harmony_chat.apis.account;

import com.app.harmony_chat.configs.DefinePath;
import com.app.harmony_chat.configs.DefinePropertyJson;
import com.app.harmony_chat.services.auth.AuthServices;
import com.app.harmony_chat.utils.infomation.MapperJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Bắt sự kiện đăng nhập
 */
@RestController
@RequestMapping(path = DefinePath.ACCOUNT)
public class Login {
    @Autowired
    private AuthServices services;

    @Autowired
    private MapperJson mapper;

    @PostMapping(path = DefinePath.ACCOUNT_LOGIN)
    public String login(@RequestBody Map<String, String> json) {
        List<String> params = mapper.getParam(json, DefinePropertyJson.EMAIL, DefinePropertyJson.PASSWORD);
        return mapper.mapToJson(services.login(params.get(0), params.get(1)));
    }
}
