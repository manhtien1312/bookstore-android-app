package com.example.bookshop_app.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.bookshop_app.api.UserApi;
import com.example.bookshop_app.model.User;
import com.example.bookshop_app.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private final UserApi userApi;

    public UserRepository(Context context){
        userApi = RetrofitClient.retrofitWithToken(context).create(UserApi.class);
    }

    public void getUserInformation(final IUserResponse userResponse){
        Call<User> res = userApi.getUserInformation();
        res.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                userResponse.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                userResponse.onError(t.getMessage());
            }
        });
    }

    public interface IUserResponse {
        void onSuccess(User user);
        void onError(String message);
    }

}
