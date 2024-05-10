package com.example.task61d;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private EditText confirmPasswordEditText;
    private EditText confirmEmailEditText;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 初始化界面组件
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        confirmEmailEditText = findViewById(R.id.confirmEmailEditText);
        registerButton = findViewById(R.id.buttonRegister);

        // 设置注册按钮点击监听器
        registerButton.setOnClickListener(view -> registerUser());
    }

    // 注册用户
    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String confirmEmail = confirmEmailEditText.getText().toString().trim();

        // 检验是否有非空字段
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmEmail.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all the information", Toast.LENGTH_SHORT).show();
            return;
        }

        // 检查密码和确认密码是否一致
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Please make sure passwords match", Toast.LENGTH_SHORT).show();
            return;
        }

        // 检查电子邮箱和确认电子邮箱是否一致
        if (!email.equals(confirmEmail)) {
            Toast.makeText(this, "Please make sure email addresses match", Toast.LENGTH_SHORT).show();
            return;
        }

        // 进行用户注册
        try (DBHelper dbHelper = new DBHelper(RegisterActivity.this)) {
            boolean registrationSuccessful = dbHelper.addUser(username, email, password);

            if (registrationSuccessful) {
                // 注册成功
                Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, InterestSelectionActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            } else {
                // 注册失败
                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}
