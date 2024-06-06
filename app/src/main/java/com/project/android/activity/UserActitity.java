package com.project.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.project.android.Adapter.usertabadapter;
import com.project.android.R;

public class UserActitity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    String myString;
    private usertabadapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_actitity);

        Intent intent = getIntent();
        if(intent.hasExtra("iduser")) {
            Bundle b = getIntent().getExtras();
            if(!b.getString("idoder").equals(null)) {
                String index = b.getString("iduser");
                myString = index;
            }
        }
        else {

        }
        tabLayout = findViewById(R.id.tabLayoutAdmin);
        viewPager2 = findViewById(R.id.viewAdmin);

        myViewPagerAdapter = new usertabadapter(this);
        viewPager2.setAdapter(myViewPagerAdapter);


        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Thông tin người dùng");
                    break;
                case 2:
                    tab.setText("Chỉnh sửa thông tin");
                    break;
            }
        }).attach();


    }
    public String getMyData() {
        return myString;
    }
}