package com.example.bookshop_app.ui.auth;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookshop_app.R;
import com.example.bookshop_app.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;

    public SignUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

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

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signUpFragment_to_signInFragment);
            }
        });

    }
}