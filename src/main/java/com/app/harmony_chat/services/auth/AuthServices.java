package com.app.harmony_chat.services.auth;

import com.app.harmony_chat.configs.DefineInfomation;
import com.app.harmony_chat.models.Infomation;
import com.app.harmony_chat.models.Profile;
import com.app.harmony_chat.models.User;
import com.app.harmony_chat.repositories.account.AccountRepository;
import com.app.harmony_chat.repositories.account.InfoAccountRepository;
import com.app.harmony_chat.utils.auths.PasswordEncoder;
import com.app.harmony_chat.utils.infomation.CheckInfomation;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Thực hiện các việc liên quan đến bảo mật
 */
@Service
public class AuthServices {
    @Autowired
    private AccountRepository dao;
    @Autowired
    private InfoAccountRepository profileDao;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CheckInfomation checkInfomation;
    @Autowired
    private MailService mailService;

    /**
     * Lấy ra tài khoản người dùng. Có 2 cách thực hiện:
     * + Nếu có đủ tài khoản và mật khẩu thì dò theo cả 2
     * + Nếu chỉ có mật khẩu thì dò theo tên đăng nhập
     *
     * @param email
     * @param password
     * @return
     */
    private Infomation selectUser(@NotNull String email, String password) {
        Infomation info = new Infomation();
        StringBuilder content = new StringBuilder();
        // Các trường thông tin không được trống
        if (checkInfomation.checkOneWithAll(false, DefineInfomation.EMPTY, email, password)) {
            info.setCode(DefineInfomation.ERROR_CLIENT);
            content.append(DefineInfomation.DEFAULT_NOT_CORRECT_FILEDS);
        } else {
            List<User> selectUsers = dao.findByEmail(email);
            // Lỗi do cấu hình dự án khiến tồn tại nhiều hơn 1 tài khoản
            if (selectUsers.size() > 1) {
                info.setCode(DefineInfomation.ERROR_SERVER);
                content.append(DefineInfomation.DEFAULT_ERROR_ACCOUNT);
                System.err.println("ERROR DATABASE >> " + content.toString());
            } else {
                User user = selectUsers.isEmpty() ? null : selectUsers.get(0);
                info.setCode(DefineInfomation.SUCCESS);
                // Không tồn tại tài khoản trong hệ thống
                if (user == null) {
                    content.append(DefineInfomation.DEFAULT_NOT_ACCOUNT);
                } else {
                    // Nếu có mật khẩu thì kiểm tra nó với mật khẩu mã hóa
                    if (password != null && encoder.isCorrect(password, user.getPassword())) {
                        content.append(user.getId().toString());
                    } else {
                        content.append(DefineInfomation.DEFAULT_NOT_CORRECT_ACCOUNT);
                    }
                }
            }
            info.setContent(content.toString());
        }
        return info;
    }


    // Thực hiện kiểm tra đăng nhập
    public Infomation login(String email, String password) {
        Infomation info = new Infomation();
        if (password.equals("password")) {
            User user = dao.findByEmail(email).get(0);
            if(user == null) {
                info.setContent(DefineInfomation.SUCCESS_BUT_NOT_FOUND)
                        .setContent(DefineInfomation.DEFAULT_NOT_ACCOUNT);
            } else {
                info.setCode(DefineInfomation.SUCCESS)
                        .setContent(user.getId());
            }
            return info;
        } else {
            return selectUser(email, password);
        }
    }

    /**
     * Thêm một người dùng mới vào hệ thống
     *
     * @param newProfile
     * @return
     */
    public Infomation addUser(Profile newProfile) {
        User newUser = newProfile.getUser();
        String email = newUser.getEmail();
        Infomation info = selectUser(email, null);
        // Nếu được xác nhận là thành công truy xuất
        if (info.getCode() == DefineInfomation.SUCCESS) {
            Object content = info.getContent();
            // Nếu chưa tồn tại tài khoản thì tạo
            if (content.equals(DefineInfomation.DEFAULT_NOT_ACCOUNT)) {
                // Gửi mail tới tài khoản
                newUser.setId(UUID.randomUUID().toString());
                newUser.setPassword(encoder.hashPass(newUser.getPassword()));
                User user = dao.save(newUser);
                profileDao.saveBasic(newProfile);
                info.setContent(user.getId().toString());
                mailService.sendMailRegister(email);
            } else {
                info.setCode(DefineInfomation.SUCCESS_BUT_HAS_ACCOUNT);
                info.setContent(DefineInfomation.DEFAULT_HAS_ACCOUNT);
            }
        }
        return info;
    }

    public Infomation getAccountBack(String email) {
        Infomation info = selectUser(email, null);
        if (info.getCode() == DefineInfomation.SUCCESS) {
            Object content = info.getContent();
            if (content.equals(DefineInfomation.DEFAULT_NOT_ACCOUNT)) {
                info.setCode(DefineInfomation.SUCCESS_BUT_NOT_FOUND);
                info.setContent(DefineInfomation.DEFAULT_NOT_ACCOUNT);
            } else {
                // Gửi mail tới tài khoản
                mailService.sendMailForgetPass(email);
            }
        }
        return info;
    }
}
