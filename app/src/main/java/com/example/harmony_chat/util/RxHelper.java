package com.example.harmony_chat.util;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxHelper {
    public static void performImmediately(Runnable immediateAction) {
        // Sử dụng defer để thực hiện hành động ngay lập tức trên một luồng khác
        Observable.defer(() -> {
                    immediateAction.run();
                    return Observable.empty();
                })
                .subscribeOn(Schedulers.newThread()) // Lập tức thực hiện trên một luồng mới
                .observeOn(AndroidSchedulers.mainThread()) // Kết quả được nhận trên main thread để cập nhật giao diện
                .subscribe();
    }
}
