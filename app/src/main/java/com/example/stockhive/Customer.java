package com.example.stockhive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockhive.ADApt1;
import com.example.stockhive.CartActivity;
import com.example.stockhive.CartItem;
import com.example.stockhive.MainActivity;
import com.example.stockhive.R;
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


        Button viewCartButton = findViewById(R.id.viewCart);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer.this, CartActivity.class);
                intent.putExtra("cartItems", cartItems);
                startActivityForResult(intent, REQUEST_CODE_CART);
            }
        });



        // todo: categorize each product into separate vendors like devices, accessories, and extras.
        // todo: finalize vendor names

        String productname[] = {"Smartphone X10", "Laptop Pro 15", "Wireless Earbuds", "4K Monitor", "Gaming Mouse",
                "Mechanical Keyboard", "Fitness Tracker", "Bluetooth Speaker", "Tablet Mini 8", "Smartwatch Z",
                "Power Bank 20K", "Portable Projector", "Noise Cancelling Headphones", "Smart Light Bulb", "VR Headset",
                "USB-C Hub", "Drone HD Pro", "Streaming Webcam", "Digital Photo Frame", "E-Reader", "Mini Desktop PC",
                "Graphics Tablet", "Smart Thermostat", "Portable SSD 1TB", "WiFi Range Extender", "Smart Door Lock",
                "Robot Vacuum", "Smart Scale", "Action Camera", "Laptop Stand", "Noise Sensor", "Laser Printer",
                "Smart Plug", "Electric Scooter", "Air Purifier", "Car Dash Cam", "LED Strip Lights", "Mechanical Timer",
                "Wireless Charger", "HDMI Switch", "Digital Clock", "Laptop Cooling Pad", "Cordless Hair Trimmer",
                "Smart Coffee Maker", "Smartphone Gimbal", "Outdoor Security Camera", "Rechargeable Flashlight",
                "Bluetooth Tracker", "Digital Microscope", "Phone Stabilizer"};


        String description[] = {"X10-2024", "LP15-2024", "WE-22", "4KM-27", "GM-500", "MK-900", "FT-X", "BS-10", "TM8-2024", "SWZ-7",
                "PB-20000", "PJ-MINI", "NC-H900", "SLB-WIFI", "VRX-3", "HUB-C7", "DR-HDPRO", "WC-1080", "DPF-10", "ER-6",
                "MPC-X1", "GT-A3", "ST-T100", "SSD-1T", "WRE-X", "SDL-PRO", "RV-360", "SS-10", "AC-PRO", "LS-FOLD",
                "NS-X1", "LP-100", "SP-WIFI", "ES-RUN", "AP-CLEAR", "DC-HD", "LED-STRIP", "MT-01", "WC-FLAT", "HS-3P",
                "DC-LED", "CP-ICE", "HT-CORD", "CM-WIFI", "GMB-PRO", "OSC-HD", "RF-900", "BT-TRACK", "DM-100", "PS-360"};



        double[] price = {799, 1299, 99, 349, 59, 89, 69, 79, 229, 199, 49, 299, 149, 19, 399, 39, 499, 89, 79, 129, 349,
                149, 179, 109, 59, 189, 249, 59, 179, 29, 49, 199, 25, 599, 149, 99, 39, 15, 35, 29, 25, 34, 59, 149, 119,
                129, 29, 25, 89, 79};


        int[] imageData = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.e,
                R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i,
                R.drawable.j, R.drawable.k, R.drawable.l, R.drawable.m,
                R.drawable.n, R.drawable.o, R.drawable.p, R.drawable.q,
                R.drawable.r, R.drawable.s, R.drawable.t, R.drawable.u,
                R.drawable.v, R.drawable.w, R.drawable.x, R.drawable.y,
                R.drawable.z,


                R.drawable.aa, R.drawable.bb, R.drawable.cc,
                R.drawable.dd, R.drawable.ee, R.drawable.ff, R.drawable.gg,
                R.drawable.hh, R.drawable.ii, R.drawable.jj, R.drawable.kk,
                R.drawable.ll, R.drawable.mm, R.drawable.nn, R.drawable.oo,
                R.drawable.pp, R.drawable.qq, R.drawable.rr, R.drawable.ss,
                R.drawable.tt, R.drawable.uu, R.drawable.vv, R.drawable.ww,
                R.drawable.xx, R.drawable.yy};

        recyclerView = findViewById(R.id.recyclerview);
        ADApt1 adapter = new ADApt1(imageData, productname, description, price, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        SearchView searchView = findViewById(R.id.searchView);
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
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        updateCartIcon();
    }


    private void updateCartIcon() {
        Button viewCartButton = findViewById(R.id.viewCart);
        viewCartButton.setText("View Cart (" + cartItems.size() + ")");
    }

    public void logoutcust(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CART && resultCode == RESULT_OK && data != null) {
            cartItems = (ArrayList<CartItem>) data.getSerializableExtra("cartItems");
            updateCartIcon();
            showCartSummary();
        }
    }
}













