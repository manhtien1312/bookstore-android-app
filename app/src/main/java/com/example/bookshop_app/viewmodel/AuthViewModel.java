package com.example.bookshop_app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookshop_app.payload.request.SignInRequest;
import com.example.bookshop_app.payload.request.SignUpRequest;
import com.example.bookshop_app.repository.AuthRepository;

public class AuthViewModel extends ViewModel {

    private AuthRepository authRepository;
    private MutableLiveData<String> signInResponse = new MutableLiveData<>();
    private MutableLiveData<String> signUpResponse = new MutableLiveData<>();

    public AuthViewModel(){
        authRepository = new AuthRepository();
    }

    public void signIn(SignInRequest request){
        authRepository.signIn(request, new AuthRepository.IAuthResponse()
        {
            @Override
            public void onSuccess(String token, String message) {
                signInResponse.setValue(token);
            }

            @Override
            public void onError(String message) {
                signInResponse.setValue(message);
            }
        });
    }

    public void signUp(SignUpRequest request){
        authRepository.signUp(request, new AuthRepository.IAuthResponse() {
            @Override
            public void onSuccess(String token, String message) {
                signUpResponse.setValue(message);
            }

            @Override
            public void onError(String message) {
                 signUpResponse.setValue(message);
            }
        });
    }

    public LiveData<String> getSignInResponse(){
        return signInResponse;
    }

    public LiveData<String> getSignUpResponse(){
        return signUpResponse;
    }

}
