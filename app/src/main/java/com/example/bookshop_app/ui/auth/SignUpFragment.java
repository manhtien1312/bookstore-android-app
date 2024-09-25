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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.bookshop_app.R;
import com.example.bookshop_app.databinding.FragmentSignUpBinding;
import com.example.bookshop_app.payload.request.SignUpRequest;
import com.example.bookshop_app.viewmodel.AuthViewModel;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private AuthViewModel viewModel;
    private NavController navController;

    public SignUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        String[] genderArr = getResources().getStringArray(R.array.gender);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown, genderArr);
        binding.autoCompleteTextView.setAdapter(arrayAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

        // Chọn giới tính
        binding.autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = parent.getItemAtPosition(position).toString();
                binding.txtGender.setText(itemValue);
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

        // Đăng ký
        viewModel.getSignUpResponse().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String res) {
                binding.loading.setVisibility(View.INVISIBLE);
                if(res.contains("Failure")){
                    String messages = res.substring(8);
                    Toast.makeText(requireContext(), messages, Toast.LENGTH_LONG).show();
                }
                else if(res.equals("Server Error")){
                    Toast.makeText(requireContext(), "Lỗi Server. Vui lòng thử lại sau!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(requireContext(), res, Toast.LENGTH_LONG).show();
                    navController.popBackStack();
                }
            }
        });
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loading.setVisibility(View.VISIBLE);
                String name = binding.txtName.getText().toString().trim();
                String gender = binding.txtGender.getText().toString().trim();
                String phoneNumber = binding.txtPhoneNumber.getText().toString().trim();
                String email = binding.txtEmail.getText().toString().trim();
                String password = binding.txtPassword.getText().toString().trim();
                SignUpRequest request = new SignUpRequest(name, gender, phoneNumber, email, password);
                viewModel.signUp(request);
            }
        });

        // Chuyển về trang đăng nhập
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signUpFragment_to_signInFragment);
            }
        });

    }
}