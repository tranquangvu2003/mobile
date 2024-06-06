package com.project.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.project.android.Adapter.HomeAdapter;
import com.project.android.Adapter.MyViewPagerAdapter;
import com.project.android.Adapter.ProhomeAdapter;
import com.project.android.Fagment.AccountFragment;
import com.project.android.Fagment.CartFragment;
import com.project.android.Fagment.Home_HomeF;
import com.project.android.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private HomeAdapter myViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);

        viewPager2 = findViewById(R.id.viewTrangChu);

        myViewPagerAdapter = new HomeAdapter(this);
        viewPager2.setAdapter(myViewPagerAdapter);


        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Trang Chủ");
                    break;
                case 1:
                    tab.setText("Gian hàng");
                    break;
                case 2:
                    tab.setText("Giỏ hàng");
                    break;
                case 3:
                    tab.setText("Tài khoản");
                    break;
                case 4:
                    tab.setText("Edit");
                    break;
            }
        }).attach();

    }
}