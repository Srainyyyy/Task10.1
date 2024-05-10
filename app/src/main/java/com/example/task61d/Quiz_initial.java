package com.example.task61d;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Quiz_initial extends AppCompatActivity {
    private String Username;
    private String description;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_initial);

        // 获取用户名
        Intent intent = getIntent();
        if (intent != null) {
            Username = intent.getStringExtra("username");
        }

        // 初始化界面组件
        TextView usernameTextView = findViewById(R.id.textusername);
        ImageButton startButton = findViewById(R.id.buttonContinue);
        TextView descriptionTextView = findViewById(R.id.testtaskDescription);

        // 获取用户兴趣
        DBHelper dbHelper = new DBHelper(this);
        List<String> interests = dbHelper.getUserInterests(Username);

        // 随机选择一个兴趣作为测验描述
        if (!interests.isEmpty()) {
            int randomIndex = (int) (Math.random() * interests.size());
            description = interests.get(randomIndex);
        } else {
            // 如果用户兴趣为空，显示默认描述
            description = "default";
        }

        // 设置用户名和测验描述
        usernameTextView.setText(Username);
        descriptionTextView.setText(String.format("Click the following button to start the quiz on %s", description));

        // 设置启动按钮点击监听器
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
    }

    // 启动测验页面
    private void startQuiz() {
        Intent intent = new Intent(Quiz_initial.this, Quiz.class);
        intent.putExtra("description", description);
        startActivity(intent);
    }
}
