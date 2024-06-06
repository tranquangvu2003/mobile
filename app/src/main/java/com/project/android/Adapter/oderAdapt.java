package com.project.android.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.android.R;
import com.project.android.controller.ProductOfOder;
import com.project.android.model.Oder;
import com.project.android.model.Product;
import com.project.android.Utils.ultil;

import java.util.ArrayList;
import java.util.List;

public class oderAdapt extends RecyclerView.Adapter<oderAdapt.OderViewHolder>{
    private ArrayList<ProductOfOder> products;

    public oderAdapt(ArrayList<ProductOfOder> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public OderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitemforoder,parent,false);
        return new OderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OderViewHolder holder, int position) {
        ProductOfOder product = products.get(position);
        if (product == null){
            return;
        }
        holder.textName.setText(product.getName());
        holder.textTotal.setText(Integer.toString(product.getQuantity())+" cái");
        holder.textCost.setText(ultil.intToVND(product.getCost()));
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child(product.getPic());

        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.picture.setImageBitmap(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });

    }

    @Override
    public int getItemCount() {
        if(products != null){
            return products.size();
        }
        return 0;
    }

    public class OderViewHolder extends RecyclerView.ViewHolder{
        private TextView textName, textTotal, textCost;
        private ImageView picture;


        public OderViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.productName);
            textTotal = itemView.findViewById(R.id.total);
            textCost = itemView.findViewById(R.id.cost);
            picture = itemView.findViewById(R.id.oderVIew);
        }
    }
}