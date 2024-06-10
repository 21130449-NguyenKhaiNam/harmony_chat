package com.example.harmony_chat;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.harmony_chat.model.BlackList;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.harmony_chat.service.CallService;
import com.example.harmony_chat.util.RxHelper;

import java.util.ArrayList;
import java.util.List;

public class BlackListActivity extends AppCompatActivity {
    private ListView blackListView;
    private BlackListAdapter adapter;
    private ArrayList<BlackList> blackList;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_list);
//        back.findViewById(R.id.btnBackBlackList);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithResult();
            }
        });
        hideSystemUI();
        blackListView = findViewById(R.id.blackListView);
        blackList = new ArrayList<>();
        adapter = new BlackListAdapter(this, R.layout.item_block, blackList);
        blackListView.setAdapter(adapter);

//         Gọi phương thức để lấy danh sách chặn từ API
        getBlockListFromAPI("userId");
        updateBlackList(blackList);

    }

    private void getBlockListFromAPI(String userId) {
        // Thực hiện cuộc gọi API để lấy danh sách chặn từ userId bằng CallService
        List<BlackList> blacklist = CallService.getInstance().getBlackList(userId);
        updateBlackList(blackList);
    }

    private void updateBlackList(ArrayList<BlackList> blackList) {
        blackList.addAll(blackList);
        adapter.notifyDataSetChanged();
        // Tạo dữ liệu test


    }

    private void hideSystemUI() {
        final View decorView = getWindow().getDecorView();

        Runnable setSystemUiVisibility = new Runnable() {
            @Override
            public void run() {
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                );
            }
        };

        setSystemUiVisibility.run();

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    setSystemUiVisibility.run();
                }
            }
        });
    }

    private void finishWithResult() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("blockedCount", blackList.size());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
