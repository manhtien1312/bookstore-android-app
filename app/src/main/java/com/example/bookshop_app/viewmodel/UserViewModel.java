package com.example.bookshop_app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookshop_app.model.User;
import com.example.bookshop_app.repository.UserRepository;

public class UserViewModel extends ViewModel {

    private UserRepository userRepository;
    private MutableLiveData<User> _user = new MutableLiveData<>();

    public UserViewModel(){
        userRepository = new UserRepository();
    }

    public void getUserInformation(){
        userRepository.getUserInformation(new UserRepository.IUserResponse() {
            @Override
            public void onSuccess(User user) {
                _user.setValue(user);
            }

            @Override
            public void onError(String message) {
                _user.setValue(null);
            }
        });
    }

    public LiveData<User> getUserResponse(){
        return _user;
    }

}
