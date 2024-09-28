package com.example.bookshop_app.ui.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookshop_app.R;
import com.example.bookshop_app.databinding.FragmentCartBinding;
import com.example.bookshop_app.utils.JwtManager;
import com.google.android.material.navigation.NavigationBarView;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private NavController navController;

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
}