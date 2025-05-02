package com.example.stockhive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductAdminDeleteActivity extends AppCompatActivity {

    private Spinner bikeList;
    private List<String> bikes = new ArrayList<>();
    private Map<String, String> bikeIdMap = new HashMap<>();
    private EditText edtColour, edtMileage, edtModel;
    private Button btnDelete, btnLogout;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_admin_delete);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        bikeList = findViewById(R.id.listBikes);
        edtColour = findViewById(R.id.Colour);
        edtMileage = findViewById(R.id.Mileage);
        edtModel = findViewById(R.id.model);
        btnDelete = findViewById(R.id.btnDeleteProduct);
        btnLogout = findViewById(R.id.btnLogout);

        db = FirebaseFirestore.getInstance();

        db.collection("bikes").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (var doc : queryDocumentSnapshots) {
                String id = doc.getId();
                String model = doc.getString("model");

                if (model != null) {
                    bikes.add(model);
                    bikeIdMap.put(model, id);
                }

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bikes);
            bikeList.setAdapter(adapter);
        });

        bikeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedBike = bikes.get(position);
                String docID = bikeIdMap.get(selectedBike);

                if (docID != null) {
                    db.collection("bikes").document(docID).get().addOnSuccessListener(documentSnapshot -> {
                        edtColour.setText(documentSnapshot.getString("colour"));
                        edtMileage.setText(documentSnapshot.getString("mileage"));
                        edtModel.setText(documentSnapshot.getString("model"));
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object selectedItem = bikeList.getSelectedItem();
                if (selectedItem != null) {
                    String selectedBike = bikeList.getSelectedItem().toString();
                    String docID = bikeIdMap.get(selectedBike);

                    if (docID != null) {
                        db.collection("bikes").document(docID).delete()
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(ProductAdminDeleteActivity.this, "Bike Deleted successfully", Toast.LENGTH_SHORT).show();
                                    edtColour.setText("");
                                    edtMileage.setText("");
                                    edtModel.setText("");
                                    bikes.remove(selectedBike);
                                    bikeIdMap.remove(selectedBike);
                                    ((ArrayAdapter) bikeList.getAdapter()).notifyDataSetChanged();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(ProductAdminDeleteActivity.this, "Failed to delete bike, please try again", Toast.LENGTH_SHORT).show();
                                });
                    }
                } else {
                    Toast.makeText(ProductAdminDeleteActivity.this, "Please select a bike first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(ProductAdminDeleteActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
    }
    }







