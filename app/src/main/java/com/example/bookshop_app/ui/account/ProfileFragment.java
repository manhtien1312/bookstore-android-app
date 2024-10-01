package com.example.bookshop_app.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.bookshop_app.R;
import com.example.bookshop_app.adapter.AddressAdapter;
import com.example.bookshop_app.databinding.FragmentProfileBinding;
import com.example.bookshop_app.model.Address;
import com.example.bookshop_app.model.User;
import com.example.bookshop_app.payload.response.MessageResponse;
import com.example.bookshop_app.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private UserViewModel viewModel;
    private NavController navController;
    private List<Address> addresses;

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
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        displayUserInformation();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        navController = Navigation.findNavController(view);

        getParentFragmentManager().setFragmentResultListener("changeAddress", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if(result.getBoolean("isSuccess", false)){
                    displayUserInformation();
                }
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

        binding.btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.txtName.setEnabled(true);
                binding.txtName.requestFocus();
                binding.txtName.setSelection(binding.txtName.length());
            }
        });

//        binding.btnEditEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                binding.txtEmail.setEnabled(true);
//                binding.txtEmail.requestFocus();
//                binding.txtEmail.setSelection(binding.txtEmail.length());
//            }
//        });

        binding.btnEditPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.txtPhoneNumber.setEnabled(true);
                binding.txtPhoneNumber.requestFocus();
                binding.txtPhoneNumber.setSelection(binding.txtPhoneNumber.length());
            }
        });

        binding.btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragmentDirections.ActionProfileFragmentToAddressFragment action =
                        ProfileFragmentDirections.actionProfileFragmentToAddressFragment(null, addresses.size());
                navController.navigate(action);
            }
        });

        binding.btnSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.txtName.getText().toString().trim();
                String phoneNumber = binding.txtPhoneNumber.getText().toString().trim();
                RadioButton button = view.findViewById(binding.radioSelectGender.getCheckedRadioButtonId());
                String gender = button.getText().toString().trim();

                User user = new User(null, name, phoneNumber, gender, null, null, null);
                updateUserInformation(user);
            }
        });

    }

    private void displayUserInformation(){
        viewModel.getUserResponse().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.txtName.setText(user.getName());
                binding.txtEmail.setText(user.getEmail());
                binding.txtPhoneNumber.setText(user.getPhoneNumber());
                if(user.getGender().contentEquals(binding.btnGenderMale.getText())){
                    binding.radioSelectGender.check(R.id.btnGenderMale);
                }
                else {
                    binding.radioSelectGender.check(R.id.btnGenderFemale);
                }
                addresses = user.getAddress();

                AddressAdapter adapter = new AddressAdapter(addresses);
                binding.listAddress.setAdapter(adapter);
                binding.listAddress.setLayoutManager(new GridLayoutManager(requireContext(), 1));
                adapter.setOnItemClickListener(new AddressAdapter.OnItemClickListener() {
                    @Override
                    public void handleItemClicked(int index) {
                        ProfileFragmentDirections.ActionProfileFragmentToAddressFragment action =
                                ProfileFragmentDirections.actionProfileFragmentToAddressFragment(addresses.get(index), addresses.size());
                        navController.navigate(action);
                    }
                });
            }
        });
        viewModel.getUserInformation();
    }

    private void updateUserInformation(User user){
        viewModel.getMessageResponse().observe(getViewLifecycleOwner(), new Observer<MessageResponse>() {
            @Override
            public void onChanged(MessageResponse messageResponse) {
                binding.txtName.setEnabled(false);
                binding.txtPhoneNumber.setEnabled(false);
                binding.txtEmail.setEnabled(false);
                Toast.makeText(requireContext(), messageResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        viewModel.getErrorResponse().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errorStr) {
                Toast.makeText(requireContext(), errorStr, Toast.LENGTH_LONG).show();
            }
        });
        viewModel.updateUser(user);
    }
}