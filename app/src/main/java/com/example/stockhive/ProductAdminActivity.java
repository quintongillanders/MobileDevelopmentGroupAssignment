package com.example.stockhive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductAdminActivity extends AppCompatActivity {

    Button btnaddProduct, btnUpdateProduct, btnDeleteProduct, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnaddProduct = findViewById(R.id.btnAdd);
        btnUpdateProduct = findViewById(R.id.btnUpdate);
        btnDeleteProduct = findViewById(R.id.btnDelete);
        btnLogout = findViewById(R.id.btnLogout);

        btnaddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductAdminActivity.this, ProductAdminAddActivity.class);
                startActivity(i);
                Toast.makeText(ProductAdminActivity.this, "Add Product clicked", Toast.LENGTH_SHORT).show();

            }
        });

        btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductAdminActivity.this, ProductAdminUpdateActivity.class);
                startActivity(i);
                Toast.makeText(ProductAdminActivity.this, "Update Product clicked", Toast.LENGTH_SHORT).show();

            }
        });

        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductAdminActivity.this, ProductAdminDeleteActivity.class);
                startActivity(i);
                Toast.makeText(ProductAdminActivity.this, "Delete Product clicked", Toast.LENGTH_SHORT).show();

            }
        });




    }
}