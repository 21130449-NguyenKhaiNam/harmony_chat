package com.app.harmony_chat.apis.view;

import com.app.harmony_chat.configs.DefinePath;
import com.app.harmony_chat.services.view.SearchService;
import com.app.harmony_chat.utils.infomation.MapperJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = DefinePath.SEARCH)
public class Search {
    @Autowired
    private MapperJson mapper;
    @Autowired
    private SearchService service;

    @PostMapping(path = DefinePath.SEARCH_FRIEND_MESS)
    public String search(@RequestParam String name) {
        return mapper.mapToJson(service.searchFriendMess(name));
    }
}
