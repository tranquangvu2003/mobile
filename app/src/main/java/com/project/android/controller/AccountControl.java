package com.project.android.controller;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.android.activity.LoginActivity;
import com.project.android.model.Account;
import com.project.android.model.Product;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class AccountControl {
    public AccountControl() {
//        getUser();
//        getAdmin();
    }

    ArrayList<Account> adminlist;
    ArrayList<Account> Userlist;
    Account _account;

    public ArrayList<Account> getAdminlist() {
        return adminlist;
    }

    public void setAdminlist(ArrayList<Account> adminlist) {
        this.adminlist = adminlist;
    }

    public ArrayList<Account> getUserlist() {
        return Userlist;
    }

    public void setUserlist(ArrayList<Account> userlist) {
        Userlist = userlist;
    }

    public void SaveProduct(Account account) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Account");
        myRef.child("User").child(account.getUserName()).setValue(account);
    }

    public void getUser() {
        ArrayList<Account> list = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Account");
        myRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account rs = snapshot.getValue(Account.class);
                list.add(rs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        this.Userlist = list;
    }

        public void getAdmin() {
        ArrayList<Account> list = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Account");
        myRef.child("Admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account rs = snapshot.getValue(Account.class);
                list.add(rs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        this.adminlist = list;
    }

    public void saveallChange() {
        for (Account e : this.Userlist
        ) {
            this.SaveProduct(e);
        }

    }

    public Account get_account() {
        return _account;
    }

    public void getDirectAdminUser(String id) {
        DatabaseReference database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Account");
        database.child("User").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                _account.setId(snapshot.child("id").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public String getUserLogin(String usename){
        String rs ="";
        AccountControl pro = new AccountControl();
        for(Account user : pro.getUserlist() ){
            if(user.getUserName().equals(usename)){
                rs=user.getUserName();
            }
        }
        return rs;
    }
}
