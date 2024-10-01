package com.example.bookshop_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshop_app.R;
import com.example.bookshop_app.model.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder>{

    private OnItemClickListener onItemClickListener;
    private List<Address> addresses;

    public AddressAdapter(List<Address> addresses) {
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addresses.get(position);
        holder.txtAddressName.setText(address.getName());
        holder.txtAddressPhoneNumber.setText(address.getPhoneNumber());
        holder.txtDetailAddress.setText(address.getDetailAddress());
        holder.txtMapAddress.setText(address.toString());
        if(address.getIsDefault() == 1){
            holder.txtIsDefault.setVisibility(View.VISIBLE);
        } else {
            holder.txtIsDefault.setVisibility(View.GONE);
        }
        if(position == getItemCount()-1){
            holder.divideBar.setVisibility(View.GONE);
        }
        holder.itemAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.handleItemClicked(position);
            }
        });
    }

    class AddressViewHolder extends RecyclerView.ViewHolder{

        private CardView itemAddress;
        private TextView txtAddressName;
        private TextView txtAddressPhoneNumber;
        private TextView txtDetailAddress;
        private TextView txtMapAddress;
        private TextView txtIsDefault;
        private View divideBar;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            itemAddress = itemView.findViewById(R.id.itemAddress);
            txtAddressName = itemView.findViewById(R.id.txtAddressName);
            txtAddressPhoneNumber = itemView.findViewById(R.id.txtAddressPhoneNumber);
            txtDetailAddress = itemView.findViewById(R.id.txtDetailAddress);
            txtMapAddress = itemView.findViewById(R.id.txtMapAddress);
            txtIsDefault = itemView.findViewById(R.id.txtIsDefault);
            divideBar = itemView.findViewById(R.id.divideBar);
        }
    }

    public interface OnItemClickListener{
        void handleItemClicked(int index);
    }

}
