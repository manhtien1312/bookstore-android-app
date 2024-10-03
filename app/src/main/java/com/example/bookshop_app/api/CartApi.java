package com.example.bookshop_app.api;

import com.example.bookshop_app.model.Cart;
import com.example.bookshop_app.payload.response.MessageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CartApi {

    @GET("api/v1/cart")
    Call<List<Cart>> getAllItems();

    @POST("api/v1/cart")
    Call<MessageResponse> addItem(@Body Cart cart);

}
