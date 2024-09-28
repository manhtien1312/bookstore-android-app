package com.example.bookshop_app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookshop_app.model.Address;
import com.example.bookshop_app.payload.response.MessageResponse;
import com.example.bookshop_app.payload.response.ProvinceResponse;
import com.example.bookshop_app.repository.AddressRepository;
import com.example.bookshop_app.repository.ProvinceRepository;

public class AddressViewModel extends ViewModel {

    private final ProvinceRepository provinceRepository = new ProvinceRepository();
    private MutableLiveData<ProvinceResponse> _provinceResponse = new MutableLiveData<>();
    private MutableLiveData<ProvinceResponse> _districtResponse = new MutableLiveData<>();
    private MutableLiveData<ProvinceResponse> _wardResponse = new MutableLiveData<>();
    private final AddressRepository addressRepository = new AddressRepository();
    private MutableLiveData<MessageResponse> _addressResponse = new MutableLiveData<>();
    private MutableLiveData<String> _errorMessage = new MutableLiveData<>();

    public AddressViewModel(){}

    public void getProvince(){
        provinceRepository.getProvince(new ProvinceRepository.IProvinceResponse() {
            @Override
            public void onSuccess(ProvinceResponse response) {
                _provinceResponse.setValue(response);
            }

            @Override
            public void onError(String message) {
                _errorMessage.setValue(message);
            }
        });
    }

    public void getDistrict(String provinceId){
        provinceRepository.getDistrict(provinceId, new ProvinceRepository.IProvinceResponse() {
            @Override
            public void onSuccess(ProvinceResponse response) {
                _districtResponse.setValue(response);
            }

            @Override
            public void onError(String message) {
                _errorMessage.setValue(message);
            }
        });
    }

    public void getWard(String districtId){
        provinceRepository.getWard(districtId, new ProvinceRepository.IProvinceResponse() {
            @Override
            public void onSuccess(ProvinceResponse response) {
                _wardResponse.setValue(response);
            }

            @Override
            public void onError(String message) {
                _errorMessage.setValue(message);
            }
        });
    }

    public void addAddress(Address address){
        addressRepository.addAddress(address, new AddressRepository.IAddressResponse() {
            @Override
            public void onSuccess(MessageResponse response) {
                _addressResponse.setValue(response);
            }

            @Override
            public void onError(String message) {
                _errorMessage.setValue(message);
            }
        });
    }

    public void updateAddress(Address address){
        addressRepository.updateAddress(address, new AddressRepository.IAddressResponse() {
            @Override
            public void onSuccess(MessageResponse response) {
                _addressResponse.setValue(response);
            }

            @Override
            public void onError(String message) {
                _errorMessage.setValue(message);
            }
        });
    }

    public LiveData<ProvinceResponse> getProvinceResponse(){
        return _provinceResponse;
    }
    public LiveData<ProvinceResponse> getDistrictResponse(){
        return _districtResponse;
    }
    public LiveData<ProvinceResponse> getWardResponse(){
        return _wardResponse;
    }
    public LiveData<MessageResponse> getAddressResponse(){
        return _addressResponse;
    }
    public LiveData<String> getErrorResponse(){
        return _errorMessage;
    }
}
