package com.app.harmony_chat.services.view;

import com.app.harmony_chat.configs.DefineInfomation;
import com.app.harmony_chat.models.Infomation;
import com.app.harmony_chat.models.Profile;
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
       List<Profile> profiles = dao.findByUserName(name);
       Infomation info = new Infomation();
       if(profiles == null || profiles.isEmpty()) {
           info.setCode(DefineInfomation.SUCCESS_BUT_NOT_FOUND)
                   .setContent(DefineInfomation.EMPTY);
       } else {
           profiles.forEach(profile -> profile.getUser().setPassword(null));
           info.setCode(DefineInfomation.SUCCESS)
                   .setContent(profiles);
       }
       return info;
    }
}
