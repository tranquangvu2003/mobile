package com.project.android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.android.Adapter.ISenDataListener;
import com.project.android.Adapter.oderAdapt;
import com.project.android.R;
import com.project.android.controller.ProductOfOder;
import com.project.android.model.Oder;
import com.project.android.model.Product;
import com.project.android.Utils.ultil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class oderDetails extends AppCompatActivity implements ISenDataListener {
    TextView userby, daycreate, note, phone, status, address, totalcost, id;
    RecyclerView recycler;
    Button btn;
    DatabaseReference database;

    @Override
    public void sendData(String id) {
        String ido = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oncreate();
        Intent intent = getIntent();
        if (intent.hasExtra("id1")) {
            getData(intent.getStringExtra("id1"));
        } else {
            getData("001");
        }

    }

    private void oncreate() {
        setContentView(R.layout.activity_oder_details);
        userby = (TextView) findViewById(R.id.userBuy);
        daycreate = (TextView) findViewById(R.id.dayCreate);
        note = (TextView) findViewById(R.id.note);
        phone = (TextView) findViewById(R.id.phone);
        status = (TextView) findViewById(R.id.status);
        address = (TextView) findViewById(R.id.address);
        totalcost = (TextView) findViewById(R.id.totalcost);
        recycler = findViewById(R.id.recyclerView);
        id = findViewById(R.id.idoder);
        btn = findViewById(R.id.button);
    }

    private void setText(Oder od) {
        userby.setText(od.getPbuyName());
        daycreate.setText(od.getDayCrate());
        note.setText(od.getNote());
        phone.setText(od.getPhoneNumber());
        status.setText(od.getStatus());
        address.setText(od.getAddress());
        totalcost.setText("od.getTotal()");
        id.setText(od.getId());
        id.setVisibility(View.INVISIBLE);
        btnAction();
        if (!status.getText().toString().equals("Hoàn thành")) {
            btn.setVisibility(View.INVISIBLE);
        }
    }

    private void showList(ArrayList<ProductOfOder> e) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(linearLayoutManager);
        oderAdapt oderAdapter = new oderAdapt(e);
        recycler.setAdapter(oderAdapter);
    }

    private void btnAction() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(oderDetails.this);

                builder.setTitle("Confirm");
                builder.setMessage("Anh trai à, anh chắc chứ?");

                builder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Oder");
                        database.child("Oder").child(id.getText().toString()).child("status").setValue("Huỷ");
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Không !", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    private void getData(String id) {
        database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Oder");
        database.child("Oder").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Oder od = snapshot.getValue(Oder.class);
                setText(od);
                ArrayList<ProductOfOder> list = new ArrayList<>();
                Map<String, Integer> map = od.getAllOderCart();
                database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Product");
                database.child("Product").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int total = 0;

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Product product = snap.getValue(Product.class);
                            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                                if (entry.getKey().equals(product.getId())) {
                                    ProductOfOder pr = new ProductOfOder(
                                            product.getId()
                                            , product.getFoodName()
                                            , entry.getValue()
                                            , product.getPrice()
                                            , product.getImg()
                                    );
                                    list.add(pr);
                                    total += pr.getCost();

                                }

                            }
                        }
                        totalcost.setText(ultil.intToVND(total));
                        showList(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}