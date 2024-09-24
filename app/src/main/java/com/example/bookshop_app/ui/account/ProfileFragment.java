package com.example.bookshop_app.ui.account;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.bookshop_app.R;
import com.example.bookshop_app.databinding.FragmentProfileBinding;
import com.example.bookshop_app.model.User;
import com.example.bookshop_app.utils.JwtManager;
import com.example.bookshop_app.viewmodel.UserViewModel;

import java.nio.charset.StandardCharsets;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private UserViewModel viewModel;
    private NavController navController;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new UserViewModel(requireContext());
        displayUserInformation();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        navController = Navigation.findNavController(view);
        binding.helpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JwtManager.removeToken(requireContext());
                navController.navigate(R.id.signInFragment);
            }
        });

    }

    private void displayUserInformation(){
        viewModel.getUserResponse().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user == null){
                    navController.navigate(R.id.signInFragment);
                } else {
                    binding.imgUserAvatar.setImageBitmap(decodeImageBase64(user.getAvatarImage()));
                    binding.txtUserName.setText(user.getName());
                }
            }
        });
        viewModel.getUserInformation();
    }

    private Bitmap decodeImageBase64(String imageStr){
        byte[] decodedImg = Base64.decode(imageStr, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedImg, 0, decodedImg.length);
    }
}