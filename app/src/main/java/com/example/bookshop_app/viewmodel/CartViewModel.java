package com.example.bookshop_app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookshop_app.model.Cart;
import com.example.bookshop_app.payload.response.MessageResponse;
import com.example.bookshop_app.repository.CartRepository;

import java.util.List;

public class CartViewModel extends ViewModel {

    private final CartRepository cartRepository = new CartRepository();
    private MutableLiveData<List<Cart>> _cart = new MutableLiveData<>();
    private MutableLiveData<MessageResponse> _message = new MutableLiveData<>();
    private MutableLiveData<String> _errorMessage = new MutableLiveData<>();

    public CartViewModel() {}

    public void getAllItem(){
        cartRepository.getAllItems(new CartRepository.ICartResponse() {
            @Override
            public void onSuccess(List<Cart> cart, MessageResponse messageResponse) {
                _cart.setValue(cart);
            }

            @Override
            public void onError(String message) {
                _errorMessage.setValue(message);
            }
        });
    }

    public void addItem(Cart cart){
        cartRepository.addItem(cart, new CartRepository.ICartResponse() {
            @Override
            public void onSuccess(List<Cart> cart, MessageResponse messageResponse) {
                _message.setValue(messageResponse);
            }

            @Override
            public void onError(String message) {
                _errorMessage.setValue(message);
            }
        });
    }

    public LiveData<List<Cart>> getCartResponse(){
        return _cart;
    }
    public LiveData<MessageResponse> getSuccessMessage(){
        return _message;
    }
    public LiveData<String> getErrorResponse(){
        return _errorMessage;
    }

}
