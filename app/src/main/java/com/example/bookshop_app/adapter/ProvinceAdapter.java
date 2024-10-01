package com.example.bookshop_app.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshop_app.R;
import com.example.bookshop_app.model.Province;

import java.util.List;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<Province> provinces;

    public ProvinceAdapter(List<Province> provinces) {
        this.provinces = provinces;
    }

    @NonNull
    @Override
    public ProvinceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_province, parent, false);
        return new ProvinceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinceViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Province province = provinces.get(position);
        holder.txtProvince.setText(province.getFull_name());
        holder.itemProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.handleItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return provinces.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ProvinceViewHolder extends RecyclerView.ViewHolder{

        private CardView itemProvince;
        private TextView txtProvince;

        public ProvinceViewHolder(@NonNull View itemView) {
            super(itemView);
            itemProvince = itemView.findViewById(R.id.itemProvince);
            txtProvince = itemView.findViewById(R.id.txtProvince);
        }
    }

    public interface OnItemClickListener{
        void handleItemClicked(int index);
    }
}
