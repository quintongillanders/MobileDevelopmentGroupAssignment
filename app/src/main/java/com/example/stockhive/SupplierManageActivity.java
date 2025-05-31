package com.example.stockhive;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class SupplierManageActivity extends AppCompatActivity {


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    Button btnUpdate, btnDelete, btnLogout;
    EditText txtName, txtQuantity;
    RecyclerView prodList;
    ArrayList<Item> itemList;
    SupplierAdapter adapter;

    private String selectedItemId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_supplier_manage);

        btnUpdate = findViewById(R.id.updateBtn);
        btnLogout = findViewById(R.id.logoutBtn);
        txtName = findViewById(R.id.nameTxt);
        txtQuantity = findViewById(R.id.quantTxt);
        prodList = findViewById(R.id.rv_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        prodList.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        adapter = new SupplierAdapter(itemList);
        prodList.setAdapter(adapter);

        txtName.setText("");
        txtQuantity.setText("");
        selectedItemId = null;

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("items");

        loadItems();

        adapter.setOnItemClickListener(item -> {
            txtName.setText(item.getItemname());
            txtQuantity.setText(String.valueOf(item.getItemquantity()));
            selectedItemId = item.getId();
        });

        btnUpdate.setOnClickListener(v -> updateItem());
    }

    private void loadItems() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                itemList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Item item = postSnapshot.getValue(Item.class);
                    if (item != null) {
                        item.setId(postSnapshot.getKey());
                        itemList.add(item);
                    }
                }
                adapter.setData(itemList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void updateItem() {
        String name = txtName.getText().toString().trim();
        String quantityStr = txtQuantity.getText().toString().trim();

        if (selectedItemId == null || name.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Please select an item!", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter quantity as a number!", Toast.LENGTH_SHORT).show();
            return;
        }

        Item updatedItem = new Item(name, quantity);
        updatedItem.setId(selectedItemId);

        reference.child(selectedItemId).setValue(updatedItem)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Update successful!", Toast.LENGTH_SHORT).show());
    }
}


