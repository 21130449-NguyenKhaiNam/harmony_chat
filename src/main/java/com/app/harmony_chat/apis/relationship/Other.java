package com.app.harmony_chat.apis.relationship;

import com.app.harmony_chat.configs.DefinePath;
import com.app.harmony_chat.configs.DefinePropertyJson;
import com.app.harmony_chat.services.relationship.OtherUserService;
import com.app.harmony_chat.utils.infomation.MapperJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = DefinePath.RELATIONSHIP)
public class Other {
    @Autowired
    private MapperJson mapper;
    @Autowired
    private OtherUserService service;

    @GetMapping(path = DefinePath.OTHER_PROFILE)
    public String viewProfileOtherUser(@RequestBody Map<String, String> json) {
        List<String> params = mapper.getParam(json, DefinePropertyJson.OTHER_ID);
        return mapper.mapToJson(service.getProfile(params.get(0)));
    }

    @GetMapping(path = DefinePath.OTHER_FRIEND)
    public String listFriendOtherUser(@RequestBody Map<String, String> json) {
        List<String> params = mapper.getParam(json, DefinePropertyJson.OTHER_ID);
        return mapper.mapToJson(service.getFriends(params.get(0)));
    }
}
