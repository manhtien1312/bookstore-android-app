package com.example.bookshop_app.api;

import com.example.bookshop_app.model.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserApi {

    @GET("/api/v1/user/information")
    Call<User> getUserInformation();

}
