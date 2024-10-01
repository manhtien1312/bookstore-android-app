package com.example.bookshop_app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookshop_app.model.User;
import com.example.bookshop_app.payload.response.MessageResponse;
import com.example.bookshop_app.repository.UserRepository;

public class UserViewModel extends ViewModel {

    private UserRepository userRepository;
    private MutableLiveData<User> _user = new MutableLiveData<>();
    private MutableLiveData<MessageResponse> _messageResponse = new MutableLiveData<>();
    private MutableLiveData<String> _errorMessage = new MutableLiveData<>();

    public UserViewModel(){
        userRepository = new UserRepository();
    }

    public void getUserInformation(){
        userRepository.getUserInformation(new UserRepository.IUserResponse() {
            @Override
            public void onSuccess(User user, MessageResponse message) {
                _user.setValue(user);
            }

            @Override
            public void onError(String message) {
                _errorMessage.setValue(message);
            }
        });
    }

    public void updateUser(User user){
        userRepository.updateUser(user, new UserRepository.IUserResponse() {
            @Override
            public void onSuccess(User user, MessageResponse message) {
                _messageResponse.setValue(message);
            }

            @Override
            public void onError(String message) {
                _errorMessage.setValue(message);
            }
        });
    }

    public LiveData<User> getUserResponse(){
        return _user;
    }
    public LiveData<MessageResponse> getMessageResponse(){
        return _messageResponse;
    }
    public LiveData<String> getErrorResponse(){
        return _errorMessage;
    }
}
