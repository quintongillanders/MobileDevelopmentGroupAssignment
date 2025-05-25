package com.example.stockhive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {


    TextView totaltext;
    Button BACKtocart, CLEARcart;
    ArrayList<CartItem> cartItems;


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


        BACKtocart = findViewById(R.id.backtocart);
        CLEARcart = findViewById(R.id.clearcart);
        totaltext = findViewById(R.id.totalText);

        cartItems = (ArrayList<CartItem>) getIntent().getSerializableExtra("cartItems");

        if (cartItems == null) cartItems = new ArrayList<>();
        updateTotal();

        BACKtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CartActivity", "Returning cart with " + cartItems.size() + " items.");
                Intent resultIntent = new Intent();
                resultIntent.putExtra("cartItems", new ArrayList<>(cartItems));


                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });






        CLEARcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItems.clear();
                updateTotal();
                Log.d("CartActivity", "Cart cleared. Current cart size: " + cartItems.size());

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
        totaltext.setText("Total: $" + String.format("%.2f", total));

    }
    }


