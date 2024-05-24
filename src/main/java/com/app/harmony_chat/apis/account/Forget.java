package com.app.harmony_chat.apis.account;

import com.app.harmony_chat.configs.DefineInfomation;
import com.app.harmony_chat.configs.DefinePath;
import com.app.harmony_chat.configs.DefinePropertyJson;
import com.app.harmony_chat.services.auth.AuthServices;
import com.app.harmony_chat.utils.infomation.MapperJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = DefinePath.ACCOUNT)
public class Forget {
    @Autowired
    private AuthServices services;
    @Autowired
    private MapperJson mapper;
    @PostMapping(path = DefinePath.ACCOUNT_FORGET)
    public String forget(@RequestBody Map<String, String> json) {
        List<String> params = mapper.getParam(json, DefinePropertyJson.EMAIL);
        return mapper.mapToJson(services.getAccountBack(params.get(0)));
    }
}
