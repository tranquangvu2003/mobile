package com.project.android.Fagment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.android.R;

public class CartFragment extends Fragment {
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.activity_shopping_cart,viewGroup,false);
        oncreateFragment(view);
        return view;
    }
    private void oncreateFragment(View view) {

    }
}
