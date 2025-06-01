package com.example.stockhive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {


    TextView tvTotal;
    Button btnBack, btnClearCart, btnPurchase;
    ArrayList<CartItem> cartItems;

    private CartAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnBack = findViewById(R.id.cart_btn_back);
        btnClearCart = findViewById(R.id.cart_btn_clear);
        tvTotal = findViewById(R.id.cart_tv_total);
        btnPurchase = findViewById(R.id.cart_btn_purchase);

        cartItems = (ArrayList<CartItem>) getIntent().getSerializableExtra("cartItems");
        if (cartItems == null) cartItems = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.cart_rv_products);

        adapter = new CartAdapter(cartItems, new CartAdapter.OnRemoveClickListener() {
            @Override
            public void onRemoveClick(int position) {
                cartItems.remove(position);
                updateTotal();
                Toast.makeText(CartActivity.this, "Item removed", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateTotal();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CartActivity", "Returning cart with " + cartItems.size() + " items.");
                Intent resultIntent = new Intent();
                resultIntent.putExtra("cartItems", new ArrayList<>(cartItems));
                Toast.makeText(CartActivity.this, "Returning cart with " + cartItems.size() + " items.", Toast.LENGTH_SHORT).show();


                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });


        btnClearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItems.clear();
                updateTotal();
                Log.d("CartActivity", "Cart cleared. Current cart size: " + cartItems.size());

            }
        });

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItems.isEmpty()) {
                    Toast.makeText(CartActivity.this, "Your cart is already empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                new androidx.appcompat.app.AlertDialog.Builder(CartActivity.this)
                        .setTitle("Confirm Purchase")
                        .setMessage("Are you sure you want to complete your transaction?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            cartItems.clear();
                            updateTotal();
                            Log.d("CartActivity", "Purchase successful, cart has been cleared.");
                            Toast.makeText(CartActivity.this, "Purchase successful! Thank you!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }



    private void updateTotal() {

        double total = 0.0;
        for (CartItem item : cartItems) {
            String priceString = String.valueOf(item.getPrice());
            try {
                double price = Double.parseDouble(priceString.replace("$", ""));
                total += price;
            } catch (NumberFormatException e) {
                Log.e("CartActivity", "Error parsing price: " + priceString, e);
            }
        }
        tvTotal.setText("Total: $" + String.format("%.2f", total));

    }
}
