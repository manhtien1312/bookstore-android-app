package com.example.bookshop_app.ui.cart;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookshop_app.R;
import com.example.bookshop_app.adapter.CartAdapter;
import com.example.bookshop_app.databinding.FragmentCartBinding;
import com.example.bookshop_app.model.Book;
import com.example.bookshop_app.model.Cart;
import com.example.bookshop_app.utils.JwtManager;
import com.example.bookshop_app.viewmodel.CartViewModel;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private CartViewModel viewModel;
    private NavController navController;
    private List<Cart> itemCart;
    private CartAdapter adapter;

    public CartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        JwtManager.getToken(requireContext());
        displayCart();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        binding.bottomNavigationView.setSelectedItemId(R.id.nav_cart);
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.nav_home){
                    navController.navigate(R.id.homeFragment);
                    return true;
                }
                if(itemId == R.id.nav_account){
                    if(JwtManager.getToken(requireContext()) != null){
                        navController.navigate(R.id.accountFragment);
                    } else {
                        navController.navigate(R.id.signInFragment);
                    }
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
                return false;
            }
        });
    }

    private void displayCart(){
        binding.loading.setVisibility(View.VISIBLE);
        viewModel.getErrorResponse().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errorStr) {
                Toast.makeText(requireContext(), errorStr, Toast.LENGTH_LONG).show();
            }
        });
        viewModel.getCartResponse().observe(getViewLifecycleOwner(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                if(carts == null){
                    binding.cartView.setVisibility(View.GONE);
                    binding.payCartView.setVisibility(View.GONE);
                    binding.emptyCardView.setVisibility(View.VISIBLE);
                    binding.loading.setVisibility(View.GONE);
                }
                else {
                    itemCart = carts;
                    adapter = new CartAdapter(requireContext(), itemCart);
                    binding.listCart.setAdapter(adapter);
                    binding.listCart.setLayoutManager(new GridLayoutManager(requireContext(), 1));

                    binding.cartView.setVisibility(View.VISIBLE);
                    binding.payCartView.setVisibility(View.VISIBLE);
                    binding.emptyCardView.setVisibility(View.GONE);
                    binding.loading.setVisibility(View.GONE);
                    adapter.setOnClickListener(new CartAdapter.OnClickListener() {
                        @Override
                        public void handleBookClicked(int index) {
                            Toast.makeText(requireContext(), "Redirected to BookDetail Fragment", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleDeleteClicked(int index) {
                            // gọi api xóa item cart và re-displayCart()
                        }

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void handleSelectBook(int index, boolean isSelected) {
                            int total = Integer.parseInt(binding.txtTotalPrice.getText().toString());
                            Cart item = itemCart.get(index);
                            if(isSelected){
                                total += item.getBook().getPrice() * item.getQuantity();
                            }
                            else {
                                total -= item.getBook().getPrice() * item.getQuantity();
                            }
                            binding.txtTotalPrice.setText(Integer.toString(total));
                        }
                    });
                }
            }
        });
        viewModel.getAllItem();
    }
}