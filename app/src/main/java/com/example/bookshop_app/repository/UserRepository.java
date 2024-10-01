package com.example.bookshop_app.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.bookshop_app.api.UserApi;
import com.example.bookshop_app.model.User;
import com.example.bookshop_app.payload.response.MessageResponse;
import com.example.bookshop_app.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private final UserApi userApi;

    public UserRepository(){
        userApi = RetrofitClient.retrofitWithToken().create(UserApi.class);
    }

    public void getUserInformation(final IUserResponse userResponse){
        Call<User> res = userApi.getUserInformation();
        res.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                userResponse.onSuccess(response.body(), null);
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                userResponse.onError(t.getMessage());
            }
        });
    }

    public void updateUser(User user, final IUserResponse userResponse){
        Call<MessageResponse> res = userApi.updateUser(user);
        res.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                userResponse.onSuccess(null, response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                userResponse.onError(t.getMessage());
            }
        });
    }

    public interface IUserResponse {
        void onSuccess(User user, MessageResponse message);
        void onError(String message);
    }

}
