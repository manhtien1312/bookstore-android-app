package com.example.bookshop_app.ui.account;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookshop_app.R;
import com.example.bookshop_app.databinding.FragmentAccountBinding;
import com.example.bookshop_app.model.User;
import com.example.bookshop_app.utils.JwtManager;
import com.example.bookshop_app.viewmodel.UserViewModel;
import com.google.android.material.navigation.NavigationBarView;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private UserViewModel viewModel;
    private NavController navController;

    public AccountFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        displayUserInformation();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        navController = Navigation.findNavController(view);

        binding.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_accountFragment_to_settingFragment);
            }
        });

        binding.btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_accountFragment_to_profileFragment);
            }
        });

        binding.bottomNavigationView.setSelectedItemId(R.id.nav_account);
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.nav_home){
                    navController.navigate(R.id.homeFragment);
                }
                if(itemId == R.id.nav_recommend){
                    navController.navigate(R.id.recommendFragment);
                    return true;
                }
                if(itemId == R.id.nav_notification){
                    navController.navigate(R.id.notificationFragment);
                    return true;
                }
                if(itemId == R.id.nav_cart){
                    navController.navigate(R.id.cartFragment);
                    return true;
                }
                return false;
            }
        });

    }

    private void displayUserInformation(){
        binding.loading.setVisibility(View.VISIBLE);
        viewModel.getUserResponse().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user == null){
                    JwtManager.removeToken(requireContext());
                    navController.navigate(R.id.signInFragment);
                    binding.loading.setVisibility(View.INVISIBLE);
                } else {
                    binding.imgUserAvatar.setImageBitmap(decodeImageBase64(user.getAvatarImage()));
                    binding.txtUserName.setText(user.getName());
                    binding.loading.setVisibility(View.INVISIBLE);
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