package com.project.android.Fagment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.storage.UploadTask;
import com.project.android.R;
import com.project.android.activity.UserActitity;
import com.project.android.controller.PictureController;
import com.project.android.controller.ProductControl;
import com.project.android.model.Account;
import com.project.android.model.Product;


public class editUserPage extends Fragment {
    DatabaseReference database;
    EditText userName,gmail,phoneNumber;
    EditText userName1;
    TextView password3;
    ImageView camerau, galleryu;

    ImageView image;
    Button button2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user_page, container, false);
        userName = (EditText) view.findViewById(R.id.userName);
        userName1 = (EditText) view.findViewById(R.id.userName1);
        userName1.setVisibility(View.INVISIBLE);
        gmail = (EditText) view.findViewById(R.id.gmail);
        phoneNumber = (EditText) view.findViewById(R.id.phoneNumber);
        image = (ImageView) view.findViewById(R.id.image);
        button2 =(Button) view.findViewById(R.id.button2);
        camerau =(ImageView) view.findViewById(R.id.camerau);
        galleryu  =(ImageView) view.findViewById(R.id.galleryu);
        password3 = (TextView) view.findViewById(R.id.password3);
        password3.setVisibility(View.INVISIBLE);

        Intent intent = getActivity().getIntent();
        Account object = (Account) intent.getSerializableExtra("account");
        userName.setText(object.getName());
        userName1.setText(object.getUserName());
        gmail.setText(object.getEmail());
        phoneNumber.setText(object.getPhoneNumber());
        password3.setText(object.getPassword());
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
        btnclink();
        return view;
    }
    private void btnclink(){
        galleryu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 2);
            }
        });
        camerau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureController pictureController = new PictureController(image);
                pictureController.uploadFileFromCamera().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Không up được ảnh",Toast.LENGTH_SHORT).show();

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        database= FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Account");
                        Account account = new Account(password3.getText().toString()
                                ,userName1.getText().toString()
                                ,gmail.getText().toString()
                                ,phoneNumber.getText().toString()
                                , taskSnapshot.getMetadata().getReference().getName().toString()
                                ,userName.getText().toString()
                                );
                        database.child("User").child(userName1.getText().toString()).setValue(account);
                        Toast.makeText(getActivity(),"Luư thành công",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private void getdata(String id){
        database= FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Account");
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
        password3.setText(acc.getPassword());
    }
    private void create(View view){
        userName = (EditText) view.findViewById(R.id.userName);
        userName1 = (EditText) view.findViewById(R.id.userName1);
        userName1.setVisibility(View.INVISIBLE);
        gmail = (EditText) view.findViewById(R.id.gmail);
        phoneNumber = (EditText) view.findViewById(R.id.phoneNumber);
        image = (ImageView) view.findViewById(R.id.image);
        button2 =(Button) view.findViewById(R.id.button2);
        camerau =(ImageView) view.findViewById(R.id.camerau);
                galleryu  =(ImageView) view.findViewById(R.id.galleryu);
        password3 = (TextView) view.findViewById(R.id.password3);
        password3.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                image.setImageBitmap((Bitmap) data.getExtras().get("data"));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if (requestCode == 2 && resultCode == RESULT_OK) {
            try {
                Uri selectedImage = data.getData();
                image.setImageURI(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}