package com.example.bookshop_app.repository;

import androidx.annotation.NonNull;

import com.example.bookshop_app.api.CartApi;
import com.example.bookshop_app.model.Cart;
import com.example.bookshop_app.payload.response.MessageResponse;
import com.example.bookshop_app.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRepository {

    private final CartApi cartApi;

    public CartRepository() {
        this.cartApi = RetrofitClient.retrofitWithToken().create(CartApi.class);
    }

    public void getAllItems(final ICartResponse cartResponse){
        Call<List<Cart>> res = cartApi.getAllItems();
        res.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(@NonNull Call<List<Cart>> call, @NonNull Response<List<Cart>> response) {
                cartResponse.onSuccess(response.body(), null);
            }

            @Override
            public void onFailure(@NonNull Call<List<Cart>> call, @NonNull Throwable t) {
                cartResponse.onError(t.getMessage());
            }
        });
    };

    public void addItem(Cart cart, final ICartResponse cartResponse){
        Call<MessageResponse> res = cartApi.addItem(cart);
        res.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                cartResponse.onSuccess(null, response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                cartResponse.onError(t.getMessage());
            }
        });
    }

    public interface ICartResponse{
        void onSuccess(List<Cart> cart, MessageResponse messageResponse);
        void onError(String message);
    }
}
