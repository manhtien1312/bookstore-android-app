package com.example.bookshop_app.api;

import com.example.bookshop_app.payload.request.SignInRequest;
import com.example.bookshop_app.payload.request.SignUpRequest;
import com.example.bookshop_app.payload.response.MessageResponse;
import com.example.bookshop_app.payload.response.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("api/v1/auth/register")
    Call<MessageResponse> signUp(@Body SignUpRequest request);

    @POST("api/v1/auth/authenticate")
    Call<TokenResponse> signIn(@Body SignInRequest request);

}
