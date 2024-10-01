package com.example.bookshop_app.ui.account;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookshop_app.adapter.ProvinceAdapter;
import com.example.bookshop_app.databinding.FragmentAddressBinding;
import com.example.bookshop_app.model.Address;
import com.example.bookshop_app.model.Province;
import com.example.bookshop_app.payload.response.MessageResponse;
import com.example.bookshop_app.payload.response.ProvinceResponse;
import com.example.bookshop_app.viewmodel.AddressViewModel;

import java.util.List;

public class AddressFragment extends Fragment {

    private FragmentAddressBinding binding;
    private NavController navController;
    private AddressViewModel viewModel;
    private Address address;
    private List<Province> provinces;
    private List<Province> districts;
    private List<Province> wards;
    private ProvinceAdapter adapter;
    private String provinceId;
    private String districtId;

    public AddressFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddressBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        displayAddress();
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

        binding.city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getProvinceResponse().observe(getViewLifecycleOwner(), new Observer<ProvinceResponse>() {
                    @Override
                    public void onChanged(ProvinceResponse response) {
                        provinces = response.getData();
                        adapter = new ProvinceAdapter(provinces);
                        binding.listProvince.setAdapter(adapter);
                        binding.listProvince.setLayoutManager(new GridLayoutManager(requireContext(), 1));
                        adapter.setOnItemClickListener(new ProvinceAdapter.OnItemClickListener() {
                            @Override
                            public void handleItemClicked(int index) {
                                binding.txtCity.setText(provinces.get(index).getFull_name());
                                provinceId = provinces.get(index).getId();
                                binding.popupSelectProvince.setVisibility(View.GONE);
                            }
                        });
                    }
                });
                viewModel.getProvince();
                binding.popupSelectProvince.setVisibility(View.VISIBLE);
            }
        });

        binding.district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getDistrictResponse().observe(getViewLifecycleOwner(), new Observer<ProvinceResponse>() {
                    @Override
                    public void onChanged(ProvinceResponse response) {
                        districts = response.getData();
                        adapter = new ProvinceAdapter(districts);
                        binding.listProvince.setAdapter(adapter);
                        binding.listProvince.setLayoutManager(new GridLayoutManager(requireContext(), 1));
                        adapter.setOnItemClickListener(new ProvinceAdapter.OnItemClickListener() {
                            @Override
                            public void handleItemClicked(int index) {
                                binding.txtDistrict.setText(districts.get(index).getFull_name());
                                districtId = districts.get(index).getId();
                                binding.popupSelectProvince.setVisibility(View.GONE);
                            }
                        });
                    }
                });
                viewModel.getDistrict(provinceId);
                binding.txtPopupHeader.setText("Chọn quận/huyện");
                binding.txtSearch.setHint("Chọn quận/huyện");
                binding.popupSelectProvince.setVisibility(View.VISIBLE);
            }
        });

        binding.ward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getWardResponse().observe(getViewLifecycleOwner(), new Observer<ProvinceResponse>() {
                    @Override
                    public void onChanged(ProvinceResponse response) {
                        wards = response.getData();
                        adapter = new ProvinceAdapter(wards);
                        binding.listProvince.setAdapter(adapter);
                        binding.listProvince.setLayoutManager(new GridLayoutManager(requireContext(), 1));
                        adapter.setOnItemClickListener(new ProvinceAdapter.OnItemClickListener() {
                            @Override
                            public void handleItemClicked(int index) {
                                binding.txtWard.setText(wards.get(index).getFull_name());
                                binding.popupSelectProvince.setVisibility(View.GONE);
                            }
                        });
                    }
                });
                viewModel.getWard(districtId);
                binding.txtPopupHeader.setText("Chọn phường/xã");
                binding.txtSearch.setHint("Chọn phường/xã");
                binding.popupSelectProvince.setVisibility(View.VISIBLE);
            }
        });

        binding.btnCancelSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.popupSelectProvince.setVisibility(View.GONE);
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(address.getIsDefault() == 1){
                    Toast.makeText(requireContext(), "Bạn khộng thể xóa địa chỉ mặc định. Vui lòng thêm hoặc thay đổi địa chỉ mặc định khác!", Toast.LENGTH_LONG).show();
                }
                else {
                    binding.popupDelete.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.btnCancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.popupDelete.setVisibility(View.GONE);
            }
        });

        binding.btnConfirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAddress(address.getId());
            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressName = binding.txtAddressName.getText().toString().trim();
                String addressPhoneNumber = binding.txtAddressPhoneNumber.getText().toString().trim();
                String city = binding.txtCity.getText().toString().trim();
                String district = binding.txtDistrict.getText().toString().trim();
                String ward = binding.txtWard.getText().toString().trim();
                String detailAddress = binding.txtDetailAddress.getText().toString().trim();
                int isDefault = binding.btnIsDefault.isChecked() ? 1 : 0;
                if(address == null){
                    address = new Address(null, addressName, addressPhoneNumber, city, district, ward, detailAddress, isDefault);
                    addAddress(address);
                } else {
                    address.setName(addressName);
                    address.setPhoneNumber(addressPhoneNumber);
                    address.setCity(city);
                    address.setDistrict(district);
                    address.setWard(ward);
                    address.setDetailAddress(detailAddress);
                    address.setIsDefault(isDefault);
                    updateAddress(address);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void displayAddress(){
        AddressFragmentArgs args = AddressFragmentArgs.fromBundle(getArguments());
        address = args.getAddress();
        int numberOfAddress = args.getNumberOfAddress();

        if(address == null){
            binding.txtHeader.setText("Thêm địa chỉ mới");
            binding.btnDelete.setVisibility(View.GONE);
        } else {
            binding.txtAddressName.setText(address.getName());
            binding.txtAddressPhoneNumber.setText(address.getPhoneNumber());
            binding.txtCity.setText(address.getCity());
            binding.txtDistrict.setText(address.getDistrict());
            binding.txtWard.setText(address.getWard());
            binding.txtDetailAddress.setText(address.getDetailAddress());
            binding.btnIsDefault.setChecked(address.getIsDefault() == 1);
        }
        if(numberOfAddress == 0){
            binding.btnIsDefault.setChecked(true);
            binding.btnIsDefault.setEnabled(false);
        }

    }

    private void addAddress(Address address){
        displayResponse();
        viewModel.addAddress(address);
    }

    private void updateAddress(Address address){
        displayResponse();
        viewModel.updateAddress(address);
    }

    private void deleteAddress(String addressId){
        displayResponse();
        viewModel.deleteAddress(addressId);
    }

    private void displayResponse(){
        viewModel.getAddressResponse().observe(getViewLifecycleOwner(), new Observer<MessageResponse>() {
            @Override
            public void onChanged(MessageResponse messageResponse) {
                Toast.makeText(requireContext(), messageResponse.getMessage(), Toast.LENGTH_LONG).show();
                Bundle result = new Bundle();
                result.putBoolean("isSuccess", true);
                getParentFragmentManager().setFragmentResult("changeAddress", result);
                getParentFragmentManager().popBackStack();
            }
        });
        viewModel.getErrorResponse().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errorStr) {
                Toast.makeText(requireContext(), errorStr, Toast.LENGTH_LONG).show();
            }
        });
    }

}