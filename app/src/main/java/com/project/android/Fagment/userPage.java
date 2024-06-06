package com.project.android.Fagment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.project.android.Adapter.usertabadapter;
import com.project.android.R;
import com.project.android.activity.UserActitity;
import com.project.android.model.Account;


public class userPage extends Fragment {
    DatabaseReference database;
    TextView userName,userName1,gmail,phoneNumber;
    ImageView image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_page, container, false);
        Intent intent = getActivity().getIntent();
        Account object = (Account) intent.getSerializableExtra("account");
        userName = (TextView) view.findViewById(R.id.userName);
        userName1 = (TextView) view.findViewById(R.id.userName1);
        gmail = (TextView) view.findViewById(R.id.gmail);
        phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);
        image = (ImageView) view.findViewById(R.id.image);
        userName.setText(object.getName());
        userName1.setText(object.getUserName());
        gmail.setText(object.getEmail());
        phoneNumber.setText(object.getPhoneNumber());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child(object.getAvatar());
        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                image.setImageBitmap(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
        return view;
    }
    private void getdata(String id){
        database= FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Account");
        database.child("User").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                setData(account);
                getImage(account);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getImage(Account pr){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child(pr.getAvatar());
        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                image.setImageBitmap(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
    }
    private void setData(Account acc){
        userName.setText(acc.getName());
        userName1.setText(acc.getUserName());
        gmail.setText(acc.getEmail());
        phoneNumber.setText(acc.getPhoneNumber());
    }
    private void create(View view){
        userName = (TextView) view.findViewById(R.id.userName);
        userName1 = (TextView) view.findViewById(R.id.userName1);
        gmail = (TextView) view.findViewById(R.id.gmail);
        phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);
        image = (ImageView) view.findViewById(R.id.image);


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_user_page, container, false);
//
//    }
}}

