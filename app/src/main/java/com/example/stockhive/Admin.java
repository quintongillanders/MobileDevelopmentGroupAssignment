package com.example.stockhive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {

    private ListView userList;
    private ArrayList<String> userEmails = new ArrayList<>();
    private ArrayList<String> userIds = new ArrayList<>();
    private UserAdapter adapter;

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
        adapter = new UserAdapter(this, userEmails, userIds);
        userList.setAdapter(adapter);

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
        FirebaseFirestore.getInstance().collection("Users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    userEmails.clear();
                    userIds.clear();

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String email = doc.getString("email");
                        String uid = doc.getId();
                        if (email != null) {
                            userEmails.add(email);
                            userIds.add(uid);
                        }
                    }

                    adapter.notifyDataSetChanged();
                    Toast.makeText(Admin.this, "Loaded users", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(Admin.this, "Error loading users", Toast.LENGTH_SHORT).show());
    }

    private void toggleUserEnabled(String uid) {
        DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(uid);

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Boolean enabled = documentSnapshot.getBoolean("enabled");
                boolean newState = enabled == null || !enabled;

                userRef.update("enabled", newState)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(Admin.this, "User enabled set to: " + newState, Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(Admin.this, "Failed to update user", Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(Admin.this, "User not found", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e ->
                Toast.makeText(Admin.this, "Error reading user", Toast.LENGTH_SHORT).show());
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





