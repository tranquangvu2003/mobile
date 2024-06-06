package com.project.android.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.project.android.Fagment.Add_Product__Fragment;
import com.project.android.Fagment.Ql_Order__Fragment;
import com.project.android.Fagment.Ql_product__Fragment;
import com.project.android.Fagment.ThongKe__Fragment;
import com.project.android.Fagment.editUserPage;
import com.project.android.Fagment.userPage;

public class usertabadapter extends FragmentStateAdapter {
    public usertabadapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new editUserPage();
            default:
                return  new userPage();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

