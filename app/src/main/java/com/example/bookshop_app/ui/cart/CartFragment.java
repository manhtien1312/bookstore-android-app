package com.example.bookshop_app.ui.cart;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.bookshop_app.utils.JwtManager;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private NavController navController;
    private List<Book> booksInCart;

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
        booksInCart = new ArrayList<>();
        booksInCart.add(new Book("1", "Kính Vạn Hoa Chết Chóc - Tập 2 (Tái Bản 2023)", null, null, 120000, 0, null, null));
        booksInCart.add(new Book("1", "Kính Vạn Hoa Chết Chóc - Tập 2 (Tái Bản 2023)", null, null, 120000, 0, null, null));
        booksInCart.add(new Book("1", "Kính Vạn Hoa Chết Chóc - Tập 2 (Tái Bản 2023)", null, null, 120000, 0, null, null));
        booksInCart.add(new Book("1", "Kính Vạn Hoa Chết Chóc - Tập 2 (Tái Bản 2023)", null, null, 120000, 0, null, null));
        booksInCart.add(new Book("1", "Kính Vạn Hoa Chết Chóc - Tập 2 (Tái Bản 2023)", null, null, 120000, 0, null, null));
        booksInCart.add(new Book("1", "Kính Vạn Hoa Chết Chóc - Tập 2 (Tái Bản 2023)", null, null, 120000, 0, null, null));
        booksInCart.add(new Book("1", "Kính Vạn Hoa Chết Chóc - Tập 2 (Tái Bản 2023)", null, null, 120000, 0, null, null));
        booksInCart.add(new Book("1", "Kính Vạn Hoa Chết Chóc - Tập 2 (Tái Bản 2023)", null, null, 120000, 0, null, null));
        booksInCart.add(new Book("1", "Kính Vạn Hoa Chết Chóc - Tập 2 (Tái Bản 2023)", null, null, 120000, 0, null, null));
        booksInCart.add(new Book("1", "Kính Vạn Hoa Chết Chóc - Tập 2 (Tái Bản 2023)", null, null, 120000, 0, null, null));
        booksInCart.add(new Book("1", "Kính Vạn Hoa Chết Chóc - Tập 2 (Tái Bản 2023)", null, null, 120000, 0, null, null));
        booksInCart.add(new Book("1", "Kính Vạn Hoa Chết Chóc - Tập 2 (Tái Bản 2023)", null, null, 120000, 0, null, null));

        CartAdapter adapter = new CartAdapter(booksInCart);
        binding.listCart.setAdapter(adapter);
        binding.listCart.setLayoutManager(new GridLayoutManager(requireContext(), 1));

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
                if(isSelected){
                    total += booksInCart.get(index).getPrice();
                }
                else {
                    total -= booksInCart.get(index).getPrice();
                }
                binding.txtTotalPrice.setText(Integer.toString(total));
            }
        });
    }
}