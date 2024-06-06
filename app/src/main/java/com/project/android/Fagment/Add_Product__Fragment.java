package com.project.android.Fagment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;
import com.project.android.R;
import com.project.android.activity.MainActivity;
import com.project.android.controller.PictureController;
import com.project.android.controller.ProductControl;
import com.project.android.model.Product;

import java.util.ArrayList;
import java.util.List;


public class Add_Product__Fragment extends Fragment {
    List<String> foodtype =new ArrayList<String>();;
    DatabaseReference database;
    Spinner select;
    EditText nametext;
    TextView foodtyp1e;
    EditText inputValue;
    EditText inputbasePrice;
    ImageView showAnh;
    Button camera;
    Button ok;
    Button gallery;
    TextView url;
    PictureController pictureController;

    EditText inputQuantity;
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable ViewGroup viewGroup,@Nullable Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.fragment_add_product__,viewGroup,false);
      oncreateFragment(view);
        database= FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("FoodType");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    getList(ds.getValue().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        List<String> city = new ArrayList<String>();
        city = foodtype;
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
                    Toast.makeText(getActivity(),"Ảnh không được để trống!",Toast.LENGTH_SHORT).show();
                }else{
                    pictureController = new PictureController(showAnh);
                    pictureController.uploadFileFromCamera().addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),"Không up được ảnh",Toast.LENGTH_SHORT).show();

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
                            pr.SaveProduct(up);
                            Toast.makeText(getActivity(),"Luư thành công",Toast.LENGTH_SHORT).show();
                            remove();
                        }
                    });
                }

            }
        });
        return view;

    }

    private void getList(String s) {
        foodtype.add(s);
        ArrayAdapter<String> myadapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,foodtype);
        select.setAdapter(myadapter);
    }
    private void remove(){
        nametext.getText().clear();
        inputQuantity.getText().clear();
        inputValue.getText().clear();
        inputbasePrice.getText().clear();
        showAnh.setImageDrawable(null);
    }
    private void oncreateFragment(View view){
        select = (Spinner) view.findViewById(R.id.spinner);
        nametext = (EditText) view.findViewById(R.id.nametext);
        foodtyp1e = (TextView) view.findViewById(R.id.foodtype);
        inputValue = (EditText) view.findViewById(R.id.inputValue);
        inputQuantity = (EditText) view.findViewById(R.id.inputQuantity);
        inputbasePrice = (EditText) view.findViewById(R.id.inputbasePrice);
        showAnh = (ImageView) view.findViewById(R.id.showAnh);
        url = (TextView) view.findViewById(R.id.url);
        camera = (Button) view.findViewById(R.id.camera);
        gallery = (Button) view.findViewById(R.id.gallery);
        ok = (Button) view.findViewById(R.id.ok);
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