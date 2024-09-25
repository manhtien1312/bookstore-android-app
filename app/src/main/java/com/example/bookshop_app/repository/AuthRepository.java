package com.example.bookshop_app.repository;

import androidx.annotation.NonNull;

import com.example.bookshop_app.api.AuthApi;
import com.example.bookshop_app.payload.request.SignInRequest;
import com.example.bookshop_app.payload.request.SignUpRequest;
import com.example.bookshop_app.payload.response.MessageResponse;
import com.example.bookshop_app.payload.response.TokenResponse;
import com.example.bookshop_app.utils.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    private final AuthApi authApi = RetrofitClient.retrofit().create(AuthApi.class);

    public AuthRepository(){}

    public void signIn(SignInRequest request, final IAuthResponse authResponse){
        Call<TokenResponse> res = authApi.signIn(request);
        res.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(@NonNull Call<TokenResponse> call, @NonNull Response<TokenResponse> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    authResponse.onSuccess(response.body().getToken(), null);
                } else {
                    authResponse.onError("Failure");
                }
            }

            @Override
            public void onFailure(@NonNull Call<TokenResponse> call, @NonNull Throwable t) {
                authResponse.onError("Server Error");
            }
        });
    }

    public void signUp(SignUpRequest request, final IAuthResponse authResponse){
        Call<MessageResponse> res = authApi.signUp(request);
        res.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    authResponse.onSuccess(null, response.body().getMessage());
                } else if(response.code() == 400){
                    assert response.errorBody() != null;
                    try {
                        MessageResponse messageResponse = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        authResponse.onError("Failure " + messageResponse.getMessage());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                authResponse.onError("Server Error");
            }
        });
    }

    public interface IAuthResponse {
        void onSuccess(String token, String message);
        void onError(String message);
    }


}
