package com.example.bookshop_app.api;

import com.example.bookshop_app.payload.response.ProvinceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProvinceApi {

    @GET("1/0.htm")
    Call<ProvinceResponse> getProvince();

    @GET("2/{id}.htm")
    Call<ProvinceResponse> getDistrict(@Path("id") String provinceId);

    @GET("3/{id}.htm")
    Call<ProvinceResponse> getWard(@Path("id") String districtId);

}
