package com.example.stockhive;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Customer extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<CartItem> cartItems = new ArrayList<>();

    private static final int REQUEST_CODE_CART = 100;


    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button viewCartButton = findViewById(R.id.customer_btn_cart);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer.this, CartActivity.class);
                intent.putExtra("cartItems", cartItems);
                getReturnCartItemsLauncher.launch(intent);
            }
        });


        Image image = new Image(R.drawable.a);
        Product product = new Product("Laptop", "java", 500.0, image);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);

        image = new Image(R.drawable.b);
        product = new Product("iPhone 16", "xml", 1800.0, image);
        products.add(product);

        image = new Image(R.drawable.c);
        product = new Product("iPhone 16", "xml", 1800.0, image);
        products.add(product);

        image = new Image(R.drawable.d);
        product = new Product("Nintendo Switch 2", "xml", 1500.0, image);
        products.add(product);

        image = new Image(R.drawable.e);
        product = new Product("Nintendo Switch OLED", "java", 1600.0, image);
        products.add(product);


        image = new Image(R.drawable.f);
        product = new Product("Nintendo Switch Lite", "xml", 1700.0, image);
        products.add(product);

        image = new Image(R.drawable.g);
        product = new Product("Samsung Smart TV", "xml", 2500.0, image);
        products.add(product);

        image = new Image(R.drawable.h);
        product = new Product("iPhone 16 Pro", "java", 2500.0, image);
        products.add(product);


        image = new Image(R.drawable.i);
        product = new Product("TV Remote", "xml", 100.0, image);
        products.add(product);


        recyclerView = findViewById(R.id.customer_rv_products);
        ProductAdapter adapter = new ProductAdapter(products, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        SearchView searchView = findViewById(R.id.customer_sv_products);
        searchView.setQueryHint("Search products...");
        searchView.setIconified(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }

                searchRunnable = () -> adapter.filter(newText);
                handler.postDelayed(searchRunnable, 300);

                return true;
            }
        });
    }


    public void showCartSummary() {
        double totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }

        String summary = "Items in Cart: " + cartItems.size() + "\n" + "Total Price: $" + totalPrice;
        Toast.makeText(this, summary, Toast.LENGTH_LONG).show();
    }

    public void addToCart(CartItem item) {
        cartItems.add(item);
        Toast.makeText(this, item.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        updateCartIcon();
    }


    private void updateCartIcon() {
        Button viewCartButton = findViewById(R.id.customer_btn_cart);
        viewCartButton.setText("View Cart (" + cartItems.size() + ")");
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    ActivityResultLauncher<Intent> getReturnCartItemsLauncher = registerForActivityResult(
            new StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    cartItems = (ArrayList<CartItem>) intent.getSerializableExtra("cartItems");
                    updateCartIcon();
                    showCartSummary();
                }
            }
    );

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Toast.makeText(this, "onActivityResult", Toast.LENGTH_SHORT).show();
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//        if (requestCode == REQUEST_CODE_CART && resultCode == RESULT_OK && data != null) {
//            cartItems = (ArrayList<CartItem>) data.getSerializableExtra("cartItems");
//            updateCartIcon();
//            showCartSummary();
//        }
//    }
}
