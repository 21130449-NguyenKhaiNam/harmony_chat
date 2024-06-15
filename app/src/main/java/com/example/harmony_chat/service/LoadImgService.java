package com.example.harmony_chat.service;

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

    public void injectImage(String url, ImageView target) {
        Picasso.get()
                .load(url)
                .resize(target.getWidth(), target.getHeight())
                .placeholder(R.drawable.avatar)
                .error(R.drawable.user)
                .into(target);
        target.setTag(url);
    }
}
