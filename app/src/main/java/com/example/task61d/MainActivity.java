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
    private Button Loginbutton;
    private Button Registerbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.edituserName);
        editTextPassword = findViewById(R.id.editpassword);
        Loginbutton = findViewById(R.id.buttonLogin);
        Registerbutton = findViewById(R.id.buttonRegister);

        //登录按钮监听器
        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //注册按钮监听器
        Registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到注册页面
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // 调用数据库验证登录信息
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        boolean loginSuccessful = dbHelper.checkUser(username, password);

        if (loginSuccessful) {
            // 登录成功
            Intent intent = new Intent(MainActivity.this, Quiz_initial.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
        } else {
            //登录失败
            Toast.makeText(MainActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
        }
    }

}