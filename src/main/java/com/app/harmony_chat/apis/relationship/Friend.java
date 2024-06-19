package com.app.harmony_chat.apis.relationship;

import com.app.harmony_chat.configs.DefinePath;
import com.app.harmony_chat.configs.DefinePropertyJson;
import com.app.harmony_chat.services.relationship.FriendService;
import com.app.harmony_chat.utils.infomation.MapperJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = DefinePath.RELATIONSHIP)
public class Friend {
    @Autowired
    private FriendService service;

    @Autowired
    private MapperJson mapper;

    @PostMapping(path = DefinePath.FRIEND_ADD)
    public String addFriend(@RequestBody Map<String, String> json) {
        List<String> params = mapper.getParam(json, DefinePropertyJson.USER_ID, DefinePropertyJson.OTHER_ID);
        return mapper.mapToJson(service.addFriend(params.get(0), params.get(1)));
    }

    @PostMapping(path = DefinePath.FRIEND_DELETE)
    public String removeFriend(@RequestBody Map<String, String> json) {
        List<String> params = mapper.getParam(json, DefinePropertyJson.USER_ID, DefinePropertyJson.OTHER_ID);
        return mapper.mapToJson(service.deleteFriend(params.get(0), params.get(1)));
    }

    @PostMapping(path = DefinePath.FRIEND_RENAME_NICKNAME)
    public String changeNickNameFriend(@RequestBody Map<String, String> json) {
        List<String> params = mapper.getParam(json, DefinePropertyJson.USER_ID, DefinePropertyJson.OTHER_ID, DefinePropertyJson.NICKNAME);
        return mapper.mapToJson(service.updateNickName(params.get(0), params.get(1), params.get(2)));
    }

    @PostMapping(path = DefinePath.FRIEND_LIST)
    public String getListFriends(@RequestBody Map<String, String> json) {
        List<String> params = mapper.getParam(json, DefinePropertyJson.USER_ID);
        return mapper.mapToJson(service.getListFriends(params.get(0)));
    }
}
