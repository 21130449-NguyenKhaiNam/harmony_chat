package com.example.harmony_chat.service;

import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.example.harmony_chat.R;
import com.squareup.picasso.Picasso;

public class LoadImgService {
    private static LoadImgService service;

    private LoadImgService() {
    }

    public static LoadImgService getInstance() {
        return service == null ? (service = new LoadImgService()) : service;
    }

    public void injectImage(final String url, final ImageView target) {
        // Sử dụng ViewTreeObserver để đảm bảo ImageView đã được đặt kích thước
        target.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                // Đảm bảo rằng phương thức này chỉ được gọi một lần
                target.getViewTreeObserver().removeOnPreDrawListener(this);

                // Lấy kích thước của ImageView
                int width = target.getMeasuredWidth();
                int height = target.getMeasuredHeight();

                // Tải ảnh với kích thước phù hợp
                Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.user)
                        .resize(width, height)
                        .centerInside() // giữ nguyên tỷ lệ ảnh và đảm bảo ảnh nằm hoàn toàn trong ImageView
                        .into(target);
                target.setTag(url);

                return true;
            }
        });
    }
}
