package com.project.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.project.android.Adapter.MyViewHome;
import com.project.android.Adapter.MyViewPagerAdapter;
import com.project.android.R;


public class AdminActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private MyViewPagerAdapter myViewPagerAdapter;
    String myString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        tabLayout = findViewById(R.id.tabLayoutAdmin);
        viewPager2 = findViewById(R.id.viewAdmin);

        myViewPagerAdapter = new MyViewPagerAdapter(this);
        viewPager2.setAdapter(myViewPagerAdapter);


        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Thống Kê");
                    break;
                case 1:
                    tab.setText("Thêm sản phẩm");
                    break;
                case 2:
                    tab.setText("Quản lý sản phẩm");
                    break;
                case 3:
                    tab.setText("Quản lý đơn");
                    break;
            }
        }).attach();

    }


}