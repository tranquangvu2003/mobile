package com.project.android.Fagment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.project.android.R;
import com.project.android.controller.OderControl;
import com.project.android.controller.ProfitControl;
import com.project.android.model.Account;
import com.project.android.model.Oder;
import com.project.android.model.Product;
import com.project.android.Utils.ultil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class ThongKe__Fragment extends Fragment {
    Spinner select;
    Button button;
    TextView doanhthu;
    TextView sodonban;
    TextView soluongnguoidung;
    TextView mostfoof;
    TextView cost;
    TextView tienLoi;
    TextView complete;
    TextView destroyoder;
    ProfitControl pr;
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable ViewGroup viewGroup,@Nullable Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.fragment_thong_ke__,viewGroup,false);
        oncreateFragment(view);
        getBymoth();
        getTotalUser();
        return view;
    }
    private void oncreateFragment(View view) {
        select = (Spinner) view.findViewById(R.id.spinner2);
        getList();
        doanhthu = (TextView) view.findViewById(R.id.doanhThu);
        sodonban = (TextView) view.findViewById(R.id.totalDon);
        soluongnguoidung = (TextView) view.findViewById(R.id.totalUser);
        mostfoof = (TextView) view.findViewById(R.id.mostProduct);
        cost = (TextView) view.findViewById(R.id.totalCost);
        destroyoder = (TextView) view.findViewById(R.id.cancelOder);
        complete = (TextView) view.findViewById(R.id.complete);
        tienLoi = (TextView) view.findViewById(R.id.tienLoi);
    }
    public void getBymoth(){
        select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String text = select.getSelectedItem().toString();
                if(text.equals("all")){
                StringTokenizer tk = new StringTokenizer(text," ");
                getProductList(tk.nextToken());
                }
                else {
                    StringTokenizer tk = new StringTokenizer(text," ");
                    tk.nextToken();
                    getProductList(tk.nextToken());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private void setter(ProfitControl pr){
        int profit = pr.getProfit();
        int costt = pr.getCost();
        doanhthu.setText(ultil.intToVND(profit));
        sodonban.setText(Integer.toString(pr.getAllOder()) + " đơn");
        destroyoder.setText(Integer.toString(pr.getTotalOderdestroy()) + " đơn");
        cost.setText(ultil.intToVND(costt));
        mostfoof.setText(pr.getMostFood());
        complete.setText(pr.getComplete()+ " đơn");
        tienLoi.setText(ultil.intToVND(profit-costt));
    }
    private DatabaseReference getOder(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quanlyquancom-default-rtdb.asia-southeast1.firebasedatabase.app/");
         DatabaseReference myRef = database.getReference("Oder/Oder");
        return myRef;
    }
    private void getList(){
        ArrayList<String> thang = new ArrayList<>();

        for (int i=1; i<13 ; i++){
            thang.add("Tháng " + i);
        }
        thang.add("all");
        ArrayAdapter<String> myadapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,thang);
        select.setAdapter(myadapter);
    }

//    private void addaction(){
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {}
//        });
//
//    }
    private void getProductList(String input){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quanlyquancom-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Product/Product");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProfitControl pr = new ProfitControl();
                pr.setMonth(input);
                ArrayList<Product> array = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    array.add(ds.getValue(Product.class));
                }
                pr.setProducts(array);
                getOderlist(pr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getTotalUser(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Account/User");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Account> array = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {

                    array.add(ds.getValue(Account.class));
                }
                soluongnguoidung.setText(Integer.toString(array.size())+ " tài khoản");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getOderlist(ProfitControl pr){
        getOder().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Oder> array = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    array.add(ds.getValue(Oder.class));
                }
                pr.setOders(array);
                setter(pr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}