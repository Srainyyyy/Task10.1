package com.example.task61d;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化界面组件
        initializeViews();

        // 设置登录按钮点击监听器
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // 设置注册按钮点击监听器
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到注册页面
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // 初始化界面组件
    private void initializeViews() {
        editTextUsername = findViewById(R.id.edituserName);
        editTextPassword = findViewById(R.id.editpassword);
        loginButton = findViewById(R.id.buttonLogin);
        registerButton = findViewById(R.id.buttonRegister);
    }

    // 处理登录逻辑
    private void login() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // 调用数据库验证登录信息
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        boolean loginSuccessful = dbHelper.checkUser(username, password);

        if (loginSuccessful) {
            // 登录成功，启动测验Activity
            startQuizActivity(username);
        } else {
            // 登录失败，显示错误信息
            showErrorMessage();
        }
    }

    // 启动测验Activity
    private void startQuizActivity(String username) {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    // 显示登录失败的错误信息
    private void showErrorMessage() {
        Toast.makeText(MainActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
    }
}
