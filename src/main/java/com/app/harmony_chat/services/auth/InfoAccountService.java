package com.app.harmony_chat.services.auth;

import com.app.harmony_chat.models.Infomation;
import com.app.harmony_chat.models.Profile;
import com.app.harmony_chat.models.User;
import com.app.harmony_chat.repositories.account.InfoAccountRepository;
import com.app.harmony_chat.utils.infomation.CheckInfomation;
import com.app.harmony_chat.utils.infomation.FilterInfomation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InfoAccountService {
    @Autowired
    private InfoAccountRepository dao;
    @Autowired
    private FilterInfomation filterInfomation;
    @Autowired
    private CheckInfomation checkInfomation;

    /**
     * Lấy ra thông tin của tài khoản
     * @param id
     * @return
     */
    public Infomation viewProfile(String id) {
        List<User> users = dao.findById(UUID.fromString(id)).stream().toList();
        return filterInfomation.filterListGetOne(users);
    }

    public Infomation updateProfile(Profile profile) {
        // Chưa thực hiện
        return null;
    }

}
