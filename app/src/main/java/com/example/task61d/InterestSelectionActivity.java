package com.example.task61d;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class InterestSelectionActivity extends AppCompatActivity {

    // 声明界面中的复选框和按钮
    private CheckBox[] checkBoxes;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_selection);

        // 初始化
        initializeViews();

        // 绑定继续按钮的点击监听器
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 存储选中兴趣
                List<String> selectedInterests = getSelectedInterests();

                // 创建 Intent 对象，返回到 MainActivity
                Intent intent = new Intent(InterestSelectionActivity.this, MainActivity.class);
                // 设置标志位，清除中间的 Activity，避免用户返回到此页面
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // 启动新的 Activity
                startActivity(intent);
            }
        });

        // 为每个 CheckBox 添加点击监听器
        setCheckBoxListeners();
    }

    private void initializeViews() {
        checkBoxes = new CheckBox[]{
                findViewById(R.id.checkBoxTechnology),
                findViewById(R.id.checkBoxBasketball),
                findViewById(R.id.checkBoxMusic),
                findViewById(R.id.checkBoxDance),
                findViewById(R.id.checkBoxAlgorithms),
                findViewById(R.id.checkBoxWebDevelopment),
                findViewById(R.id.checkBoxAI),
                findViewById(R.id.checkBoxDataStructure),
                findViewById(R.id.checkBoxTesting)
        };

        continueButton = findViewById(R.id.continueButton);
    }

    private List<String> getSelectedInterests() {
        List<String> selectedInterests = new ArrayList<>();
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                selectedInterests.add(checkBox.getText().toString());
            }
        }
        return selectedInterests;
    }

    private void setCheckBoxListeners() {
        for (final CheckBox checkBox : checkBoxes) {
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 更改选中项的背景颜色
                    if (checkBox.isChecked()) {
                        checkBox.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    } else {
                        // 如果取消选中，恢复原来的背景颜色
                        checkBox.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    }
                }
            });
        }
    }
}
