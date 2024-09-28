package com.example.bookshop_app.payload.response;

import com.example.bookshop_app.model.Province;

import java.util.List;

public class ProvinceResponse {

    private int error;
    private String error_text;
    private String data_name;
    private List<Province> data;

    public ProvinceResponse(int error, String error_text, String data_name, List<Province> data) {
        this.error = error;
        this.error_text = error_text;
        this.data_name = data_name;
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getError_text() {
        return error_text;
    }

    public void setError_text(String error_text) {
        this.error_text = error_text;
    }

    public String getData_name() {
        return data_name;
    }

    public void setData_name(String data_name) {
        this.data_name = data_name;
    }

    public List<Province> getData() {
        return data;
    }

    public void setData(List<Province> data) {
        this.data = data;
    }
}
