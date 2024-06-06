package com.project.android.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.android.R;
import com.project.android.model.Oder;
import com.project.android.model.Product;

import java.util.List;

public class ProAdapter extends RecyclerView.Adapter<ProAdapter.ProView> {
    private Context context;
    private List<Product> myListpro ;
    private ProAdapter.IClickListener iClickListener;

    public ProAdapter(List<Product> productList, ProAdapter.IClickListener iClickListener) {
        this.myListpro = productList;
        this.iClickListener = iClickListener;
        notifyDataSetChanged();
    }
    public interface IClickListener{
        void onClickDetails(Product product);
        void onClickDelete(Product product);
        void onClickEdit(Product product);
    }

    public ProAdapter(List<Product> productList) {
        this.myListpro = productList;
        notifyDataSetChanged();
    }

    public void setData(List<Product> pro2){
        this.myListpro = pro2;
        notifyDataSetChanged();


    }
    @NonNull
    @Override
    public ProView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pro,parent,false);
        return new ProView(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ProView holder, int position) {
        Product product = myListpro.get(position);
         if (product  == null) {
            return;
        }
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child(product.getImg());

        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imageView.setImageBitmap(bmp);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
              }
        });
        holder.textView.setText(product.getFoodName());
        holder.textView2.setText(product.getIdType());
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickListener.onClickDelete(product);
            }
        });
        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickListener.onClickDetails(product);
            }
        });
        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickListener.onClickEdit(product);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (myListpro != null){
            return myListpro.size();
        }
        return 0;
    }

    public class ProView extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        private TextView textView2;
        private Button btndelete, btnDetails, btnedit;

        public ProView(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_pro);
            textView = itemView.findViewById(R.id.name_pro);
            textView2 = itemView.findViewById(R.id.type_pro);
            btndelete = itemView.findViewById(R.id.detele_pro);
            btnDetails = itemView.findViewById(R.id.detail_pro);
            btnedit = itemView.findViewById(R.id.edit_pro);
        }
    }
}
