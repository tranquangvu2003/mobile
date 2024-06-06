package com.project.android.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.project.android.Fagment.AccountFragment;
import com.project.android.Fagment.Add_Product__Fragment;
import com.project.android.Fagment.HomeFragment;
import com.project.android.Fagment.Home_HomeF;
import com.project.android.Fagment.Ql_Order__Fragment;
import com.project.android.Fagment.Ql_product__Fragment;
import com.project.android.Fagment.ThongKe__Fragment;

public class MyViewHome extends FragmentStateAdapter {
    public MyViewHome(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new Home_HomeF();
            case 2:
                return new AccountFragment();
            case 3:
                return new Home_HomeF();
            default:
                return  new Home_HomeF();
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
