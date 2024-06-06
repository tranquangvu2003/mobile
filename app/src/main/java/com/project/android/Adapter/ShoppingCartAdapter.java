package com.project.android.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.android.Interface.IIMageClicklistener;
import com.project.android.R;
import com.project.android.model.EventBus.TinhtongEvent;
import com.project.android.model.ShoppingCart;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHoler> {
    Context context;
    List<ShoppingCart> listshoppingCart;

    public ShoppingCartAdapter(Context context, List<ShoppingCart> listshoppingCart) {
        this.context = context;
        this.listshoppingCart = listshoppingCart;
    }

    @NonNull
    @Override
    public ShoppingCartAdapter.MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemshoppingcart, parent, false);

        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartAdapter.MyViewHoler holder, int position) {
        ShoppingCart gioHang = listshoppingCart.get(position);
        holder.item_giohang_tensanpham.setText(gioHang.getTensp());
        holder.item_giohang_soluong.setText(gioHang.getSoluong()+ " ");
        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText("Giá: "+ decimalFormat.format(gioHang.getGiasp())+ "Đ");
        long giasp= gioHang.getGiasp()*gioHang.getSoluong();
        holder.item_giohang_gia2.setText(decimalFormat.format(giasp));
        holder.setListener(new IIMageClicklistener() {
            @Override
            public void onImageClick(View view, int post, int giatri) {
                Log.d("TAG", "onImageClick: " + post + "..." + giatri);
                if(giatri==1){
                    if(listshoppingCart.get(post).getSoluong()>1){
                        int soluongmoi=listshoppingCart.get(post).getSoluong() -1;
                        listshoppingCart.get(post).setSoluong(soluongmoi);
                    }
                }else if(giatri==2){
                    if(listshoppingCart.get(post).getSoluong()<11){
                        int soluongmoi=listshoppingCart.get(post).getSoluong() +1;
                        listshoppingCart.get(post).setSoluong(soluongmoi);

                    }
                }
                holder.item_giohang_soluong.setText(listshoppingCart.get(post).getSoluong()+ " ");
                long giasp= listshoppingCart.get(post).getGiasp()*listshoppingCart.get(post).getSoluong();
                holder.item_giohang_gia2.setText(decimalFormat.format(giasp));
                EventBus.getDefault().postSticky(new TinhtongEvent());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listshoppingCart.size();
    }
    public class MyViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image, item_giohang_tru, item_giohang_cong;
        TextView item_giohang_tensanpham, item_giohang_gia, item_giohang_soluong, item_giohang_gia2;
        IIMageClicklistener listener;



        public MyViewHoler(@NonNull View itemView){
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensanpham = itemView.findViewById(R.id.item_giohang_tensanpham);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_gia2 = itemView.findViewById(R.id.item_giohang_gia2);
            item_giohang_tru = itemView.findViewById(R.id.item_giohang_tru);
            item_giohang_cong = itemView.findViewById(R.id.item_giohang_cong);


            item_giohang_cong.setOnClickListener(this);
            item_giohang_tru.setOnClickListener(this);
        }

        public void setListener(IIMageClicklistener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if(v== item_giohang_tru){
                listener.onImageClick(v, getAdapterPosition(), 1);


            }else if(v== item_giohang_cong){
                listener.onImageClick(v, getAdapterPosition(), 2);

            }
        }
    }
}
