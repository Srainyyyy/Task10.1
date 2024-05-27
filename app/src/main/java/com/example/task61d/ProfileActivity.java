package com.example.task61d;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.Random;

public class ProfileActivity extends AppCompatActivity {
    CardView cardViewShareButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置 Activity 使用的布局文件
        setContentView(R.layout.activity_profile);

        // 初始化分享按钮卡片视图并设置点击监听器
        cardViewShareButton = findViewById(R.id.cardViewShareButton);
        cardViewShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareProfileLink(); // 调用分享个人资料链接方法
            }
        });
    }

    // 分享个人资料链接方法
    private void shareProfileLink() {
        // 生成个人资料链接
        String profileUrl = generateProfileUrl();

        // 创建意图以分享个人资料链接
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out my profile!");
        shareIntent.putExtra(Intent.EXTRA_TEXT, profileUrl);

        // 启动分享链接的活动
        startActivity(Intent.createChooser(shareIntent, "Share Profile Link"));
    }

    // 生成个人资料链接的方法
    private String generateProfileUrl() {
        return "https://10.1file/" + generateProfileId();
    }

    // 生成个人资料 ID 的方法
    private String generateProfileId() {
        return new Random().ints(10, 0, 26)
                .mapToObj(i -> String.valueOf((char) ('a' + i)))
                .reduce((acc, s) -> acc + s)
                .orElse("");
    }
}
