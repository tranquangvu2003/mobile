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
import com.project.android.Adapter.OderAdapter;
import com.project.android.R;
import com.project.android.activity.oderDetails;
import com.project.android.model.Oder;
import com.project.android.model.Product;

import java.util.ArrayList;
import java.util.List;


public class Ql_Order__Fragment extends Fragment {
    private RecyclerView recyclerView;
    private OderAdapter oderAdapter;
    private List<Oder> oderList;
    private static ISenDataListener iSenDataListener;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable ViewGroup viewGroup,@Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_ql_order__,viewGroup,false);
        recyclerView = view.findViewById(R.id.rcv_order);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        getListOder();
        oderList = new ArrayList<>();
        oderAdapter = new OderAdapter(oderList);

        oderAdapter = new OderAdapter(oderList, new OderAdapter.IClickListener() {
            @Override
            public void onClickDetails(Oder oder) {

                Intent intent = new Intent(getActivity(), oderDetails.class);
                intent.putExtra("id1",oder.getId());
                startActivity(intent);

            }

            @Override
            public void onClickDelete(Oder oder) {
                onclickDeleData(oder);
            }
        });
        recyclerView.setAdapter(oderAdapter);
        return view;
    }
    public void onClickDetails(Oder oder){
//        iSenDataListener.sendData(oder.getId());

    }
    public void onclickDeleData(Oder oder){
        new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.app_name))
                .setMessage("Đạo hữu có chắc không ?").setPositiveButton("Yah Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app");
                        DatabaseReference myRef = database.getReference("Oder/Oder");
                        myRef.child(String.valueOf(oder.getId())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getActivity(), "Đã xong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Ta cần nghĩ lại",null).show();
    }
    public void getListOder(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quanlyquancom-aac55-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Oder/Oder");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Oder oder = snapshot.getValue(Oder.class);
                if (oder != null){
                    oderList.add(oder);
                    oderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Oder oder = snapshot.getValue(Oder.class);
                if (oder != null){
                    for (int i = 0 ; i< oderList.size();i++){
                        if (oder.getId() == oderList.get(i).getId()){
                            oderList.remove(oderList.get(i));
                        }
                    }
                }
                oderAdapter.notifyDataSetChanged();

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