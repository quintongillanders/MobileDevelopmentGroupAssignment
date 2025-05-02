package com.example.stockhive;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductAdminAddActivity extends AppCompatActivity {
    private EditText bikeColour, bikeMileage, bikeModel;
    private Button addProduct, backButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_admin_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bikeColour = findViewById(R.id.Colour);
        bikeMileage = findViewById(R.id.Mileage);
        bikeModel = findViewById(R.id.model);
        addProduct = findViewById(R.id.btnAddProduct);
        backButton = findViewById(R.id.btnBack);

        db = FirebaseFirestore.getInstance();

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String colour = bikeColour.getText().toString().trim();
                String mileage = bikeMileage.getText().toString().trim();
                String model = bikeModel.getText().toString().trim();

                if (colour.isEmpty() || mileage.isEmpty() || model.isEmpty()) {
                    Toast.makeText(ProductAdminAddActivity.this, "Please fill in all fields and try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> bike = new HashMap<>();
                bike.put("colour", colour);
                bike.put("mileage", mileage);
                bike.put("model", model);

                db.collection("Bikes")
                        .add(bike)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(ProductAdminAddActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                            bikeColour.setText("");
                            bikeMileage.setText("");
                            bikeModel.setText("");
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(ProductAdminAddActivity.this, "Failed to add" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(ProductAdminAddActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();

            }
        });
    }
}




