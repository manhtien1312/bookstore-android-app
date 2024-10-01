package com.example.bookshop_app.api;

import com.example.bookshop_app.model.User;
import com.example.bookshop_app.payload.response.MessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserApi {

    @GET("api/v1/user/information")
    Call<User> getUserInformation();
    @PUT("api/v1/user/update-information")
    Call<MessageResponse> updateUser(@Body User user);

}
