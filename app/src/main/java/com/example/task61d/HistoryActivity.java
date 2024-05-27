package com.example.task61d;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView historyRecyclerView;
    // 布局管理器
    private LinearLayoutManager layoutManager;
    // 数据库助手类
    HistoryDBHelper dbHelper;
    // RecyclerView 的适配器
    HistoryAdapter adapter;
    // 删除按钮
    AppCompatButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // 初始化数据库助手类
        dbHelper = new HistoryDBHelper(this);

        // 在 dbHelper 初始化后从数据库中检索数据
        List<History> data = dbHelper.getAllData();

        // 获取 RecyclerView 的引用
        historyRecyclerView = findViewById(R.id.recyclerViewHistory);

        // 创建布局管理器并设置到 RecyclerView
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        historyRecyclerView.setLayoutManager(layoutManager);

        // 创建适配器并设置到 RecyclerView
        adapter = new HistoryAdapter(data);
        historyRecyclerView.setAdapter(adapter);
    }
}
