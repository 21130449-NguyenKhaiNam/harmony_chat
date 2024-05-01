package com.app.harmony_chat.services.view;

import com.app.harmony_chat.configs.DefineInfomation;
import com.app.harmony_chat.models.Infomation;
import com.app.harmony_chat.repositories.view.SearchRepository;
import com.app.harmony_chat.utils.infomation.CheckInfomation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private SearchRepository dao;
    @Autowired
    private CheckInfomation checkInfomation;

    /**
     * Tìm kiếm bạn bè theo tên trong các cuộc trò chuyện
     * @return
     */
    public Infomation searchFriendMess(String name) {
        StringBuilder builder = new StringBuilder(name.trim());
        Infomation info = new Infomation();
        info.setCode(DefineInfomation.SUCCESS);
        if(builder.isEmpty()) {
            info.setContent(DefineInfomation.EMPTY);
        } else {
            List<String> usernames = dao.findByUserName(name);
            if(usernames.isEmpty()) {
                info.setContent(DefineInfomation.EMPTY);
            } else {
                info.setContent(usernames);
            }
        }
        return info;
    }
}
