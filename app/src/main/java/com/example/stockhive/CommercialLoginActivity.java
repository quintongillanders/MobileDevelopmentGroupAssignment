package com.example.stockhive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class CommercialLoginActivity extends AppCompatActivity {
    Button btnLogin, btnBack;
    EditText edtComLogin, edtComPass;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_commercial_login);

        firebaseAuth = firebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnLogin = findViewById(R.id.btnCommercialLogin);
        btnBack = findViewById(R.id.btnBack);
        edtComLogin = findViewById(R.id.email_commercial_login);
        edtComPass = findViewById(R.id.pass_commercial_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtComLogin.getText().toString().trim();
                String password = edtComPass.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(CommercialLoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            String userId = firebaseAuth.getCurrentUser().getUid();

                            firestore.collection("Users").document(userId).get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            String role = documentSnapshot.getString("role");
                                            if ("commercial".equals(role)) {
                                                Toast.makeText(CommercialLoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(CommercialLoginActivity.this, CommercialUserActivity.class));
                                                finish();
                                            } else {
                                                firebaseAuth.signOut(); // prevents access for wrong roles
                                                Toast.makeText(CommercialLoginActivity.this, "Sorry, you are not a commerical user", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            firebaseAuth.signOut();
                                            Toast.makeText(CommercialLoginActivity.this, "User role not found", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        firebaseAuth.signOut();
                                        Toast.makeText(CommercialLoginActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(CommercialLoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show(); // if username or password is incorrect
                        });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CommercialLoginActivity.this, MainActivity.class);
                finish();
                Toast.makeText(CommercialLoginActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}







