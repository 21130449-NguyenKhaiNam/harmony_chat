package com.app.harmony_chat.utils.infomation;

import com.app.harmony_chat.configs.DefineInfomation;
import com.app.harmony_chat.models.Infomation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilterInfomation {
    /**
     * Giúp kiểm tra và chỉ lấy 1 phần tử trong danh sách.
     * Được sử dụng khi cần đảm bảo chỉ duy nhất 1 tài khoản xuất hiện
     *
     * @param ts
     * @param <T>
     * @return
     */
    public <T> Infomation filterListGetOne(List<T> ts) {
        Infomation info = new Infomation();
        StringBuilder content = new StringBuilder();
        if (ts.size() > 1) {
            // Lỗi từ hệ thống do cách thức lưu trữ
            content.append("Có lỗi xuất hiện trên nhiều tài khoản giống nhau");
            info.setCode(DefineInfomation.ERROR_SERVER);
            System.err.println("ERROR DATABASE >>" + content);
        } else {
            info.setCode(DefineInfomation.SUCCESS);
            if (ts.isEmpty()) {
                info.setCode(DefineInfomation.SUCCESS_BUT_NOT_FOUND);
                content.append(DefineInfomation.DEFAULT_NOT_ACCOUNT);
            } else {
                T t = ts.get(0);
                info.setContent(t);
            }
        }
        if (!content.isEmpty()) {
            info.setCode(DefineInfomation.SUCCESS_BUT_NOT_FOUND);
            info.setContent(content.toString());
        }
        return info;
    }
}
