package com.example.bookshop_app.repository;

import androidx.annotation.NonNull;

import com.example.bookshop_app.api.AddressApi;
import com.example.bookshop_app.model.Address;
import com.example.bookshop_app.payload.response.MessageResponse;
import com.example.bookshop_app.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressRepository {

    private final AddressApi addressApi;

    public AddressRepository() {
        this.addressApi = RetrofitClient.retrofitWithToken().create(AddressApi.class);
    }

    public void addAddress(Address address, final IAddressResponse addressResponse){
        Call<MessageResponse> res = addressApi.addAddress(address);
        res.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                addressResponse.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                addressResponse.onError(t.getMessage());
            }
        });
    }

    public void updateAddress(Address address, final IAddressResponse addressResponse){
        Call<MessageResponse> res = addressApi.updateAddress(address);
        res.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                addressResponse.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                addressResponse.onError(t.getMessage());
            }
        });
    }

    public void deleteAddress(String addressId, final IAddressResponse addressResponse){
        Call<MessageResponse> res = addressApi.deleteAddress(addressId);
        res.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                addressResponse.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                addressResponse.onError(t.getMessage());
            }
        });
    }

    public interface IAddressResponse{
        void onSuccess(MessageResponse response);
        void onError(String message);
    }
}
