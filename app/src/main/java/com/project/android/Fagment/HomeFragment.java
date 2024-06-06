package com.project.android.Fagment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.project.android.Adapter.ProhomeAdapter;
import com.project.android.R;
import com.project.android.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;

    private ProhomeAdapter proAdapter;
    private List<Product> productList;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_home,viewGroup,false);
        recyclerView = view.findViewById(R.id.rcv_home);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        getList();

        productList = new ArrayList<>();
        proAdapter = new ProhomeAdapter(productList);
//        proAdapter.setData(getListPro());
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
    public void getList(){

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Product/Product");
        List<Product> list = new ArrayList();
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product product = snapshot.getValue(Product.class);
                Log.d("HomeFragment", "onChildAdded " + product.getFoodName());
                if(product != null && productList.size() < 4){
                    productList.add(product);
                    proAdapter.notifyDataSetChanged();
                    // Log thông tin về sản phẩm mới được thêm vào
                    Log.d("HomeFragment", "New product added: " + product.getFoodName());
                }else{
                    Log.d("HomeFragment errors", "KHÔNG TÌM THẤY " + product.getFoodName());
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

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

