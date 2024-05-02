package com.example.task61d;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private DBHelper dbHelper;

    private EditText confirmPasswordEditText;
    private EditText confirmEmailEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.buttonRegister);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        confirmEmailEditText = findViewById(R.id.confirmEmailEditText);

        registerButton.setOnClickListener(view -> registerUser());
    }

    //注册用户
    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String confirmEmail = confirmEmailEditText.getText().toString().trim();

        //检验是否有非空字段
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmEmail.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
        }
        List<String> selectedInterests = new ArrayList<>();
        //确认密码、电子邮箱
        if (password.equals(confirmPassword) && email.equals(confirmEmail)) {
            // Invoke a method in the database to insert new user information
            boolean registrationSuccessful;
            try (DBHelper dbHelper = new DBHelper(RegisterActivity.this)) {
                registrationSuccessful = dbHelper.addUser(username, email, password);
            }
            if (registrationSuccessful) {
                // 插入用户选择的兴趣到数据库
                for (String interest : selectedInterests) {
                    dbHelper.insertUserInterest(username, interest);
                }
                //注册成功
                Toast.makeText(RegisterActivity.this, "Registration is successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, InterestSelectionActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            } else {
                // 注册失败
                Toast.makeText(RegisterActivity.this, "Registration failed, please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "The password and confirmation password or email and confirmation email do not match", Toast.LENGTH_SHORT).show();
        }
    }
}
