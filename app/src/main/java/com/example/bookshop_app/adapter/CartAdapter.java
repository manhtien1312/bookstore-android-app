package com.example.bookshop_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookshop_app.R;
import com.example.bookshop_app.model.Book;
import com.example.bookshop_app.model.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<Cart> cart;
    private OnClickListener onClickListener;

    public CartAdapter(Context context, List<Cart> cart) {
        this.cart = cart;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cartItem = cart.get(position);
        Book book = cartItem.getBook();

        Glide.with(context).load(book.getImageUrl()).into(holder.imgBookCover);

        holder.txtBookTitle.setText(book.getTitle());
        holder.txtBookPrice.setText(Integer.toString(book.getPrice()));
        holder.txtQuantity.setText(Integer.toString(cartItem.getQuantity()));
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.txtQuantity.getText().toString());
                quantity -= 1;
                holder.txtQuantity.setText(Integer.toString(quantity));
            }
        });
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.txtQuantity.getText().toString());
                quantity += 1;
                holder.txtQuantity.setText(Integer.toString(quantity));
            }
        });
        holder.imgBookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.handleBookClicked(position);
            }
        });
        holder.txtBookTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.handleBookClicked(position);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.handleDeleteClicked(position);
            }
        });
        holder.btnSelectBook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onClickListener.handleSelectBook(position, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    class CartViewHolder extends RecyclerView.ViewHolder{

        private CardView itemCart;
        private CheckBox btnSelectBook;
        private ImageView imgBookCover;
        private TextView txtBookTitle;
        private TextView txtBookPrice;
        private CardView btnMinus;
        private CardView btnPlus;
        private TextView txtQuantity;
        private CardView btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCart = itemView.findViewById(R.id.itemCart);
            btnSelectBook = itemView.findViewById(R.id.btnSelectBook);
            imgBookCover = itemView.findViewById(R.id.imgCover);
            txtBookTitle = itemView.findViewById(R.id.txtBookTitle);
            txtBookPrice = itemView.findViewById(R.id.txtBookPrice);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface OnClickListener{
        void handleBookClicked(int index);
        void handleDeleteClicked(int index);
        void handleSelectBook(int index, boolean isSelected);
    }
}
