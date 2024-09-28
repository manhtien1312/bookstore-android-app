package com.example.bookshop_app.ui.auth;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookshop_app.R;
import com.example.bookshop_app.databinding.FragmentSignInBinding;
import com.example.bookshop_app.payload.request.SignInRequest;
import com.example.bookshop_app.utils.JwtManager;
import com.example.bookshop_app.viewmodel.AuthViewModel;
import com.google.android.material.navigation.NavigationBarView;

public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private AuthViewModel viewModel;
    private NavController navController;

    public SignInFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // Hiển thị password
        binding.btnViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnViewPassword.setVisibility(View.INVISIBLE);
                binding.btnHidePassword.setVisibility(View.VISIBLE);

                Typeface currentFont = binding.txtPassword.getTypeface();
                binding.txtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.txtPassword.setTypeface(currentFont);
                binding.txtPassword.setSelection(binding.txtPassword.length());
            }
        });

        // Ẩn password
        binding.btnHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnViewPassword.setVisibility(View.VISIBLE);
                binding.btnHidePassword.setVisibility(View.INVISIBLE);

                Typeface currentFont = binding.txtPassword.getTypeface();
                binding.txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.txtPassword.setTypeface(currentFont);
                binding.txtPassword.setSelection(binding.txtPassword.length());
            }
        });

        // Chuyển sang trang đăng ký
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });

        // Đăng nhập
        viewModel.getSignInResponse().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String res) {
                binding.loading.setVisibility(View.INVISIBLE);
                if(res.equals("Failure")){
                    Toast.makeText(requireContext(), "Thông tin đăng nhập không chính xác!", Toast.LENGTH_LONG).show();
                }
                else if(res.equals("Server Error")) {
                    Toast.makeText(requireContext(), "Lỗi Server. Vui lòng thử lại sau!", Toast.LENGTH_LONG).show();
                }
                else {
                    JwtManager.saveToken(requireContext(), res);
                    navController.navigate(R.id.accountFragment);
                }
            }
        });
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loading.setVisibility(View.VISIBLE);
                String email = binding.txtEmail.getText().toString();
                String password = binding.txtPassword.getText().toString();
                SignInRequest request = new SignInRequest(email, password);
                viewModel.signIn(request);
            }
        });

        binding.bottomNavigationView.setSelectedItemId(R.id.nav_account);
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.nav_home){
                    navController.navigate(R.id.homeFragment);
                    return true;
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
}