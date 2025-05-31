package com.example.stockhive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SupplierAddActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;

    EditText itemET, quantityET;
    RecyclerView itemsRV;
    SupplierAdapter adapter;
    ArrayList<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_supplier_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        itemList = new ArrayList<Item>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        itemET = findViewById(R.id.edt_item);
        quantityET = findViewById(R.id.edt_quantity);
        itemsRV = findViewById(R.id.rv_view);
        adapter = new SupplierAdapter(itemList);
        itemsRV.setAdapter(adapter);
        itemsRV.setLayoutManager(new LinearLayoutManager(this));


        reference.child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Item item = postSnapshot.getValue(Item.class);

                    if (item != null) {
                        item.setId(postSnapshot.getKey());
                        itemList.add(item);
                    }

                }
                adapter.setData(itemList);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void addItem (View v) {
        String itemName = itemET.getText().toString().trim();
        String quantityStr = quantityET.getText().toString().trim();

        if (itemName.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Please enter both item name and quantity", Toast.LENGTH_SHORT).show();
            return;
        }
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantity must be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference itemsRef = reference.child("items");
        String id = itemsRef.push().getKey();

        if (id == null) {
            Toast.makeText(this, "Failed to generate item ID", Toast.LENGTH_SHORT).show();
            return;
        }

        Item item = new Item(itemName, quantity);
        item.setId(id);

        itemsRef.child(id);

        itemsRef.child(id).setValue(item)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to add item: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }


    public void deleteItems(View v){
        reference.child("items").removeValue()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(this, "All Items Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Failed to Delete Items", Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void logoutsup(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
