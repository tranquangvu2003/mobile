package com.project.android.Fagment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.android.Adapter.ISenDataListener;
import com.project.android.Adapter.ProAdapter;
import com.project.android.R;
import com.project.android.activity.detailProduct;
import com.project.android.activity.editProduct;
import com.project.android.model.Product;

import java.util.ArrayList;
import java.util.List;


public class Ql_product__Fragment extends Fragment {
    private RecyclerView recyclerView;

    private ProAdapter proAdapter;
    private static ISenDataListener iSenDataListener;
    private List<Product> productList;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable ViewGroup viewGroup,@Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_ql_product__,viewGroup,false);
        recyclerView = view.findViewById(R.id.rcv_pro);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        getList();

        productList = new ArrayList<>();
         proAdapter = new ProAdapter(productList);
//        proAdapter.setData(getListPro());
        proAdapter = new ProAdapter(productList, new ProAdapter.IClickListener() {
            @Override
            public void onClickDetails(Product product) {
                Intent intent = new Intent(getActivity(), detailProduct.class);
                intent.putExtra("id2",product.getId());
                startActivity(intent);

            }
            public void onClickEdit(Product product) {
                Intent intent = new Intent(getActivity(), editProduct.class);
                intent.putExtra("id3",product.getId());
                startActivity(intent);

            }

            @Override
            public void onClickDelete(Product product) {
                onclickDeleData(product);
            }
        });
        recyclerView.setAdapter(proAdapter);
        return view;
    }
    private List<Product> getListPro(){
        List<Product> list = new ArrayList();
        list.add(new Product(R.drawable.abcxyz,"Pro2","Type1"));
        list.add(new Product(R.drawable.abcxyz,"Pro2","Type1"));
        list.add(new Product(R.drawable.abcxyz,"Pro3","Type1"));
        list.add(new Product(R.drawable.abcxyz,"Pro4","Type1"));
        return list;
    }

    public void onclickDeleData(Product product){
        new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.app_name))
                .setMessage("Đạo hữu có chắc không ?").setPositiveButton("Yah Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        DatabaseReference myRef = database.getReference("Product/Product");
                        myRef.child(String.valueOf(product.getId())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getActivity(), "Đã xong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Ta cần nghĩ lại",null).show();
    }
    public void getList(){

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Product/Product");
//        List<Product> list = new ArrayList();
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product product = snapshot.getValue(Product.class);
                if(product != null){
                    productList.add(product);
                    proAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Product product = snapshot.getValue(Product.class);
                if (product != null){
                    for (int i = 0 ; i< productList.size();i++){
                        if (product.getId() == productList.get(i).getId()){
                            productList.remove(productList.get(i));
                        }
                    }
                }
                proAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}