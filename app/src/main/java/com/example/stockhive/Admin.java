package com.example.stockhive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {

    ListView userList;
    ArrayList<String> userEmails = new ArrayList<>();
    ArrayList<String> userIds = new ArrayList<>();
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userList = findViewById(R.id.userList);
        dbRef = FirebaseDatabase.getInstance().getReference("Users");

        loadUsers();

        userList.setOnItemClickListener((parent, view, position, id) -> {
            String uid = userIds.get(position);
            toggleUserEnabled(uid);
        });

        userList.setOnItemLongClickListener((parent, view, position, id) -> {
            String email = userEmails.get(position);
            resetUserPassword(email);
            return true;
        });
    }

    private void loadUsers() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userEmails.clear();
                userIds.clear();

                for (DataSnapshot child : snapshot.getChildren()) {
                    String email = child.child("email").getValue(String.class);
                    userEmails.add(email);
                    userIds.add(child.getKey());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(Admin.this, android.R.layout.simple_list_item_1, userEmails);
                userList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Admin.this, "Error loading users", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleUserEnabled(String uid) {
        DatabaseReference userRef = dbRef.child(uid).child("enabled");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean current = snapshot.getValue(Boolean.class);
                if (current != null) {
                    boolean newState = !current;
                    userRef.setValue(newState);
                    Toast.makeText(Admin.this, "User enabled set to: " + newState, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Admin.this, "User state not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Admin.this, "Error updating user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetUserPassword(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Admin.this, "Reset email sent to " + email, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Admin.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void logoutadmin(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
