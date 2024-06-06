package com.project.android.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.android.Fagment.AccountFragment;
import com.project.android.Fagment.Add_Product__Fragment;
import com.project.android.Fagment.CartFragment;
import com.project.android.Fagment.HomeFragment;
import com.project.android.Fagment.Home_HomeF;
import com.project.android.Fagment.Ql_Order__Fragment;
import com.project.android.Fagment.Ql_product__Fragment;
import com.project.android.Fagment.ThongKe__Fragment;
import com.project.android.Fagment.editUserPage;
import com.project.android.Fagment.userPage;
import com.project.android.R;
import com.project.android.activity.UserActitity;
import com.project.android.model.Product;

import java.util.List;

public class HomeAdapter extends FragmentStateAdapter {
    public HomeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new HomeFragment();
            case 2:
                return new CartFragment();
            case 3:
                return new userPage();
            case 4:
                return new editUserPage();
            default:
                return  new Home_HomeF();
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
