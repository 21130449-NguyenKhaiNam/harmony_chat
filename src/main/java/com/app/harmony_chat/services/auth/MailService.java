package com.app.harmony_chat.services.auth;

import com.app.harmony_chat.configs.DefineEmail;
import com.app.harmony_chat.repositories.account.AccountRepository;
import com.app.harmony_chat.utils.auths.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MailService {
    // Tài khoản phải bất xác thực 2 lớp và cho phép truy cập mật khẩu tự động
    private static final String adminMail = DefineEmail.ACCOUNT_ADMIN;
    private static final String adminPass = DefineEmail.ACCOUNT_TOKEN;
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder encoder;

    // Nội dung trả về chính là mã code phải nhập
    public static void sendEmail(String toEmail, String subject, String mess) {
        try {
            // Cấu hình các thuộc tính cho việc gửi email thông qua Gmail
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            // Tạo một phiên gửi email
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(adminMail, adminPass);
                }
            });
            // Tạo đối tượng MimeMessage
            Message message = new MimeMessage(session);

            // Đặt địa chỉ email người gửi
            message.setFrom(new InternetAddress(adminMail));

            // Đặt địa chỉ email người nhận
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            // Đặt chủ đề email
            message.setSubject(subject);

            // Đặt nội dung email
            message.setContent(mess, "text/html; charset = UTF-8");
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    // Gửi email
                    try {
                        Transport.send(message);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        System.out.println("[MailService-sendEmail] >> Có lỗi trong quá trình gửi");
                    }
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("[MailService-sendEmail] >> Có lỗi trong tài khoản");
        }
    }

    public void sendMailForgetPass(String toEmail) {
        int lengthPassDefault = 8;
        String generatePass = encoder.generatePassword(lengthPassDefault);
        String title = DefineEmail.TITLE_FORGET;
        String content = String.format(DefineEmail.CONTENT_FORGET, generatePass);
        accountRepository.updatePassword(toEmail, encoder.hashPass(generatePass));
        sendEmail(toEmail, title, content);
    }

    public void sendMailRegister(String toEmail) {
        int lengthPassDefault = 8;
        String title = DefineEmail.TITLE_REGISTER;
        String content = DefineEmail.CONTENT_REGISTER;
        sendEmail(toEmail, title, content);
    }
}