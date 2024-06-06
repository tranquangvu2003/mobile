package com.project.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.android.R;
import com.project.android.model.Account;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername, etPassword,SDT,email,CfPassword ,hoten;
    private Button btnSignUp;
    private TextView Login;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Account");
        SDT=findViewById(R.id.Sdt);
        email=findViewById(R.id.email);
        hoten=findViewById(R.id.hoten);
        CfPassword=findViewById(R.id.confpassword);
        etUsername = findViewById(R.id.usename);
        etPassword = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.buttonSigngup);
        Login = findViewById(R.id.Login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String sdt = SDT.getText().toString().trim();
        String emaill = email.getText().toString().trim();
        String CfPasswordd = CfPassword.getText().toString().trim();
        String hotenn= hoten.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        Account account = new Account(username, password,sdt,emaill,hotenn);

        // Lưu thông tin người dùng mới vào Firebase
        database.child("User").child(username).setValue(account, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Sign up failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
