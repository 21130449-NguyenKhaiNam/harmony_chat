package com.app.harmony_chat.apis.relationship;

import com.app.harmony_chat.configs.DefinePath;
import com.app.harmony_chat.configs.DefinePropertyJson;
import com.app.harmony_chat.services.relationship.RoomService;
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
public class Room {
    @Autowired
    private RoomService service;
    @Autowired
    private MapperJson mapper;
    @PostMapping(DefinePath.ROOM)
    public String getAllRoom(@RequestBody Map<String, String> json) {
        List<String> params = mapper.getParam(json, DefinePropertyJson.USER_ID);
        return mapper.mapToJson(service.getAllRoom(params.get(0)));
    }
}