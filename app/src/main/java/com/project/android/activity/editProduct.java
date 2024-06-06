package com.project.android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.project.android.controller.PictureController;
import com.project.android.controller.ProductControl;
import com.project.android.model.Product;

import java.util.ArrayList;
import java.util.List;

public class editProduct extends AppCompatActivity {


    DatabaseReference database;

    Spinner select;
    EditText nametext;
    TextView foodtyp1e;
    TextView id;
    EditText inputValue;
    EditText inputbasePrice;
    ImageView showAnh;
    Button camera;
    Button ok;
    Button gallery;
    TextView url;

    PictureController pictureController;

    EditText inputQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onceate();
        Intent intent = getIntent();
        if (intent.hasExtra("id3")) {
            getData(intent.getStringExtra("id3"));
            setSpiner();
            clickcontrol();
        } else {

        }

    }

    private void setSpiner() {
        database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("FoodType");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> foodtype =new ArrayList<String>();;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    foodtype.add(ds.getValue().toString());
                }
                ArrayAdapter<String> myadapter=new ArrayAdapter<>(editProduct.this,android.R.layout.simple_spinner_item,foodtype);
                select.setAdapter(myadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void onceate(){
        setContentView(R.layout.activity_edit_product);
        select = (Spinner) findViewById(R.id.spinner);
        nametext = (EditText) findViewById(R.id.nametext);
        foodtyp1e = (TextView) findViewById(R.id.foodtype);
        inputValue = (EditText) findViewById(R.id.inputValue);
        inputQuantity = (EditText) findViewById(R.id.inputQuantity);
        inputbasePrice = (EditText) findViewById(R.id.inputbasePrice);
        showAnh = (ImageView) findViewById(R.id.showAnh);
//        url = (TextView) findViewById(R.id.url);
        camera = (Button) findViewById(R.id.camera);
        id = (TextView) findViewById(R.id.productId);
        gallery = (Button) findViewById(R.id.gallery);
        ok = (Button) findViewById(R.id.ok);
    }
    private void setDataM(Product pr){
        nametext.setText(pr.getFoodName());
        select.setSelection(((ArrayAdapter<String>)select.getAdapter()).getPosition(pr.getIdType()));
        inputbasePrice.setText(Integer.toString(pr.getBasePrice()));
        inputQuantity.setText(Integer.toString(pr.getQuantity()));
        inputValue.setText(Integer.toString(pr.getPrice()));
        id.setText(pr.getId());
        id.setVisibility(View.INVISIBLE);
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
                showAnh.setImageBitmap(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Khong tim thay anh", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void clickcontrol(){
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 2);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(nametext.getText().toString())){
                    nametext.setError("Không được để trống");
                }else if(TextUtils.isEmpty(inputValue.getText().toString())){
                    inputValue.setError("Không được để trống");
                }else if(TextUtils.isEmpty(inputQuantity.getText().toString())){
                    inputQuantity.setError("Không được để trống");
                }else if(TextUtils.isEmpty(inputbasePrice.getText().toString())){
                    inputbasePrice.setError("Không được để trống");
                }else if(null == showAnh.getDrawable()){
                    Toast.makeText(getApplicationContext(),"Ảnh không được để trống!",Toast.LENGTH_SHORT).show();
                }else{
                    pictureController = new PictureController(showAnh);
                    pictureController.uploadFileFromCamera().addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Không up được ảnh",Toast.LENGTH_SHORT).show();

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            Product up = new Product(nametext.getText().toString(),
                                    select.getSelectedItem().toString(),
                                    Integer.parseInt(inputQuantity.getText().toString()),
                                    taskSnapshot.getMetadata().getReference().getName().toString(),
//                                    taskSnapshot.getMetadata().getReference().getPath().toString(),
                                    Integer.parseInt(inputValue.getText().toString()),
                                    Integer.parseInt(inputbasePrice.getText().toString()));
                            ProductControl pr = new ProductControl();
                            pr.editProduct(up,id.getText().toString());
                            Toast.makeText(getApplicationContext(),"Luư thành công",Toast.LENGTH_SHORT).show();
                            remove();
                        }
                    });
                }

            }
        });
    }
    private void remove(){
        nametext.getText().clear();
        inputQuantity.getText().clear();
        inputValue.getText().clear();
        inputbasePrice.getText().clear();
        showAnh.setImageDrawable(null);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                showAnh.setImageBitmap((Bitmap) data.getExtras().get("data"));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if (requestCode == 2 && resultCode == RESULT_OK) {
            try {
                Uri selectedImage = data.getData();
                showAnh.setImageURI(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}