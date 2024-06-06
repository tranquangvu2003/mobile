package com.project.android.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.android.R;
import com.project.android.model.Oder;

import java.util.List;

public class OderAdapter extends RecyclerView.Adapter<OderAdapter.OderViewHolder>{
    private List<Oder> oderList;
    private  IClickListener iClickListener;

    public OderAdapter(List<Oder> oderList, IClickListener iClickListener) {
        this.oderList = oderList;
        this.iClickListener = iClickListener;
        notifyDataSetChanged();
    }

    public interface IClickListener{
         void onClickDetails(Oder oder);
        void onClickDelete(Oder oder);
    }

    public OderAdapter(List<Oder> oderList) {
        this.oderList = oderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new OderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OderViewHolder holder, int position) {
            Oder oder = oderList.get(position);
            if (oder == null){
                return;
            }
            holder.textName.setText("Oder"+oder.getId());
            holder.textDate.setText(oder.getDayCrate());
            holder.textPrice.setText(Integer.toString(oder.getTotal()) +"VNĐ");
            holder.btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickListener.onClickDelete(oder);
                }
            });

            holder.btnDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickListener.onClickDetails(oder);
                }
            });

    }

    @Override
    public int getItemCount() {
         if(oderList != null){
             return oderList.size();
        }
         return 0;
    }

    public class OderViewHolder extends RecyclerView.ViewHolder{
        private TextView textName, textDate, textPrice;
        private Button btndelete, btnDetails;


        public OderViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.name_order);
            textDate = itemView.findViewById(R.id.date_order);
            textPrice = itemView.findViewById(R.id.price_order);
            btndelete = itemView.findViewById(R.id.delete_order);
            btnDetails = itemView.findViewById(R.id.details_order);
        }
    }
}
