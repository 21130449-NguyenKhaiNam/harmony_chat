package com.app.harmony_chat.apis.chat;

import com.app.harmony_chat.configs.DefinePath;
import com.app.harmony_chat.configs.DefinePropertyJson;
import com.app.harmony_chat.models.Message;
import com.app.harmony_chat.services.chat.ChatService;
import com.app.harmony_chat.utils.infomation.MapperJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = DefinePath.CHAT)
public class Chat {
    @Autowired
    private MapperJson mapper;
    @Autowired
    private ChatService service;
    @GetMapping
    public String sendMess(@RequestBody Map<String, String> json) {
        List<String> params = mapper.getParam(json, DefinePropertyJson.USER_ID, DefinePropertyJson.OTHER_ID, DefinePropertyJson.MESSAGE);
        Message message = new Message(UUID.fromString(params.get(0)), UUID.fromString(params.get(1)), params.get(2));
        // Chưa được xử lý
        return mapper.mapToJson(service.sendMessage(message));
    }
}
