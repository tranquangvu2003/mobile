package com.project.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.android.R;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Khởi tạo Firebase Database với URL cụ thể
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("message");

        // Ghi dữ liệu vào Firebase Database
        myRef.setValue("Hello, World!").addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Dữ liệu đã được ghi thành công!");
            } else {
                Log.e("Firebase", "Ghi dữ liệu thất bại: " + task.getException().getMessage());
            }
        });

        // Ghi dữ liệu con vào Firebase Database
        myRef.child("ABC").setValue("1234").addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Dữ liệu con đã được ghi thành công!");
            } else {
                Log.e("Firebase", "Ghi dữ liệu con thất bại: " + task.getException().getMessage());
            }
        });
    }
}
