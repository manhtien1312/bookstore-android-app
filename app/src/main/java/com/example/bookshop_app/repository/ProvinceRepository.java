package com.example.bookshop_app.repository;

import androidx.annotation.NonNull;

import com.example.bookshop_app.api.ProvinceApi;
import com.example.bookshop_app.model.User;
import com.example.bookshop_app.payload.response.ProvinceResponse;
import com.example.bookshop_app.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvinceRepository {

    private final ProvinceApi provinceApi;

    public ProvinceRepository() {
        provinceApi = RetrofitClient.retrofitProvince().create(ProvinceApi.class);
    }

    public void getProvince(final IProvinceResponse provinceResponse){
        Call<ProvinceResponse> res = provinceApi.getProvince();
        res.enqueue(new Callback<ProvinceResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProvinceResponse> call, @NonNull Response<ProvinceResponse> response) {
                provinceResponse.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ProvinceResponse> call, @NonNull Throwable t) {
                provinceResponse.onError(t.getMessage());
            }
        });
    }

    public void getDistrict(String provinceId, final IProvinceResponse provinceResponse){
        Call<ProvinceResponse> res = provinceApi.getDistrict(provinceId);
        res.enqueue(new Callback<ProvinceResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProvinceResponse> call, @NonNull Response<ProvinceResponse> response) {
                provinceResponse.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ProvinceResponse> call, @NonNull Throwable t) {
                provinceResponse.onError(t.getMessage());
            }
        });
    }

    public void getWard(String districtId, final IProvinceResponse provinceResponse){
        Call<ProvinceResponse> res = provinceApi.getWard(districtId);
        res.enqueue(new Callback<ProvinceResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProvinceResponse> call, @NonNull Response<ProvinceResponse> response) {
                provinceResponse.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ProvinceResponse> call, @NonNull Throwable t) {
                provinceResponse.onError(t.getMessage());
            }
        });
    }

    public interface IProvinceResponse{
        void onSuccess(ProvinceResponse response);
        void onError(String message);
    }
}
