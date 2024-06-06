package com.project.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.android.R;
import com.project.android.model.Account;

// LoginActivity.java
public class LoginADActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private TextView textView;
    private Button btnLogin;
    ImageView imageView;
    DatabaseReference database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Account");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login_ad);
        etUsername = findViewById(R.id.usenameAD);
        etPassword = findViewById(R.id.passwordAD);
        btnLogin = findViewById(R.id.buttonLoginAD);
        imageView =findViewById(R.id.showAnhAD);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginADActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                database.child("Admin").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Account account = snapshot.getValue(Account.class);
                            if (account.getPassword().equals(password)) {
                                Toast.makeText(LoginADActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginADActivity.this, AdminActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginADActivity.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginADActivity.this, "Tên đăng nhập không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginADActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
