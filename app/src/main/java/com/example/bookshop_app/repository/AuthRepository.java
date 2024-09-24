package com.example.bookshop_app.repository;

import com.example.bookshop_app.api.AuthApi;
import com.example.bookshop_app.payload.request.SignInRequest;
import com.example.bookshop_app.payload.response.TokenResponse;
import com.example.bookshop_app.utils.RetrofitClient;

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
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    authResponse.onSuccess(response.body().getToken());
                } else {
                    authResponse.onError("Failure");
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                authResponse.onError("Server Error");
            }
        });
    }

    public interface IAuthResponse {
        void onSuccess(String token);
        void onError(String message);
    }


}
