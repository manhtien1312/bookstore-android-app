package com.example.bookshop_app.api;

import com.example.bookshop_app.model.Address;
import com.example.bookshop_app.payload.response.MessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AddressApi {

    @POST("api/v1/address")
    Call<MessageResponse> addAddress(@Body Address address);
    @PUT("api/v1/address/update-address")
    Call<MessageResponse> updateAddress(@Body Address address);
    @DELETE("api/v1/address/{id}")
    Call<MessageResponse> deleteAddress(@Path("id") String addressId);

}
