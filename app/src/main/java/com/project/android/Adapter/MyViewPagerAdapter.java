package com.project.android.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.project.android.Fagment.Add_Product__Fragment;
import com.project.android.Fagment.Ql_Order__Fragment;
import com.project.android.Fagment.Ql_product__Fragment;
import com.project.android.Fagment.ThongKe__Fragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new Add_Product__Fragment();
            case 2:
                return new Ql_product__Fragment();
            case 3:
                return new Ql_Order__Fragment();
            default:
                return  new ThongKe__Fragment();
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
