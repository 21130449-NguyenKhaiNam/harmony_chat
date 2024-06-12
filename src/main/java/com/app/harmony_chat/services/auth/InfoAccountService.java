package com.app.harmony_chat.services.auth;

import com.app.harmony_chat.configs.DefineInfomation;
import com.app.harmony_chat.models.Infomation;
import com.app.harmony_chat.models.Profile;
import com.app.harmony_chat.repositories.account.InfoAccountRepository;
import com.app.harmony_chat.services.image.CloudinaryServices;
import com.app.harmony_chat.utils.infomation.CheckInfomation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoAccountService {
    @Autowired
    private InfoAccountRepository dao;

    /**
     * Lấy ra thông tin của tài khoản
     * @param id
     * @return
     */
    public Infomation viewProfile(String id) {
        Profile profile = dao.findByUserId(id).orElse(null);
        Infomation info = new Infomation();
        if(profile == null) {
            info.setCode(DefineInfomation.ERROR_CLIENT);
            info.setContent(DefineInfomation.DEFAULT_NOT_ACCOUNT);
        } else {
            profile.getUser().setPassword(null);
            info.setCode(DefineInfomation.SUCCESS);
            // Giả ảnh
            profile.setAvatar(CloudinaryServices.getINSTANCE().getRandomAvatar());
            info.setContent(profile);
        }
        return info;
    }

    public Infomation updateProfile(Profile profile) {
        // Chưa thực hiện
        return null;
    }

    public Infomation getViewBlock(String userId) {
        // Chưa thực hiện
        return null;
    }

}
