package com.example.task61d;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

// 定义 MenuActivity 类，继承自 AppCompatActivity
public class MenuActivity extends AppCompatActivity {
    // 声明按钮变量
    AppCompatButton ProfileButton;
    AppCompatButton historyButton;
    AppCompatButton questionButton;
    AppCompatButton upgradeButton;

    // 重写 onCreate 方法，在 Activity 创建时调用
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置 Activity 使用的布局文件
        setContentView(R.layout.activity_menu);

        // 初始化问题按钮并设置点击监听器
        questionButton = findViewById(R.id.questionButton);
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个新的 Intent，启动 Quiz_initial 并传递用户名
                Intent intent = new Intent(MenuActivity.this, Quiz_initial.class);
                // 获取传递给 Quiz_initial 的用户名
                String username = getIntent().getStringExtra("username");
                // 将用户名作为额外信息传递给 Quiz_initial
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        // 初始化历史记录按钮并设置点击监听器
        historyButton = findViewById(R.id.historyButton);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个新的 Intent，启动 HistoryActivity
                Intent intent = new Intent(MenuActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        // 初始化个人资料按钮并设置点击监听器
        ProfileButton = findViewById(R.id.ProfileButton);
        ProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个新的 Intent，启动 ProfileActivity
                Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });





        // 初始化升级按钮并设置点击监听器
        upgradeButton = findViewById(R.id.upgradeButton);
        upgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个新的 Intent，启动 ActivityUpgrade
                Intent intent = new Intent(MenuActivity.this, ActivityUpgrade.class);
                startActivity(intent);
            }
        });
    }
}
