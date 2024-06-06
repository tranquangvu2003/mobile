package com.project.android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.android.R;
import com.project.android.model.Oder;
import com.project.android.model.Product;
import com.project.android.Utils.ultil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class detailProduct extends AppCompatActivity {
    DatabaseReference database;
    TextView name,totalproductsale1,basePrice,price, typr, totalquan;
    ImageView img ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        onstart();
        Intent intent = getIntent();
        if (intent.hasExtra("id2")) {
            getData(intent.getStringExtra("id2"));
            gettotalsale(intent.getStringExtra("id2"));
        } else {
            getData("001");
        }




    }
    private void getData(String id){
        database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Product");
        database.child("Product").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product pr = snapshot.getValue(Product.class);
                setDataM(pr);
                getImage(pr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getImage(Product pr){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child(pr.getImg());

        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                img.setImageBitmap(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Khong tim thay anh", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void gettotalsale(String id){
        database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Oder");
        database.child("Oder").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int rs=0;
                ArrayList<Oder> oder = new ArrayList<>();
                for (DataSnapshot snap: snapshot.getChildren()) {
                    oder.add(snap.getValue(Oder.class));
                }
                for (Oder e: oder) {
                    Map<String,Integer> map = e.getAllOderCart();
                    for(Map.Entry<String, Integer> entry : map.entrySet()) {
                        if (entry.getKey().equals(id)){
                            rs+=entry.getValue();
                        }
                    }
                }
                totalproductsale1.setText(rs + " Cái");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void onstart(){
        name = findViewById(R.id.name);
        totalproductsale1 = findViewById(R.id.totalproductsale1);
        basePrice = findViewById(R.id.basePrice);
        price = findViewById(R.id.price);
        typr = findViewById(R.id.typr);
        totalquan = findViewById(R.id.totalquan);
        img = findViewById(R.id.imageView2);

    }
    private void setDataM(Product pr){
        name.setText(pr.getFoodName());
        basePrice.setText(ultil.intToVND(pr.getPrice()));
        typr.setText(pr.getIdType());
        price.setText(ultil.intToVND(pr.getPrice()));
        totalquan.setText(Integer.toString(pr.getQuantity())+" cái");
    }
}