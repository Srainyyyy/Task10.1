package com.example.task61d;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityUpgrade extends AppCompatActivity {

    AppCompatButton purchaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设定布局文件
        setContentView(R.layout.activity_upgrade);

        // 以ID获取按钮控件
        purchaseButton = findViewById(R.id.purchaseButton);
        // 设定按钮的点击监听器
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个新的FragmentTransaction实例
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // 创建一个新的PurchaseFragment实例
                PurchaseFragment fragment = new PurchaseFragment();

                // 用新的Fragment替换当前的Fragment或容器
                transaction.replace(R.id.fragment_container, fragment);

                // 添加事务到返回栈
                transaction.addToBackStack(null);

                // 提交事务
                transaction.commit();
            }
        });
    }
}
