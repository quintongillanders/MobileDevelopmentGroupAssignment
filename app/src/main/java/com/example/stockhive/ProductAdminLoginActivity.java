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

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductAdminLoginActivity extends AppCompatActivity {

    Button btnLoginPAdmin, btnBack;
    EditText edtEmailPAdminLogin, edtEmailPAdminPass;

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_admin_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnLoginPAdmin = findViewById(R.id.btnProductAdminLogin);
        btnBack = findViewById(R.id.btnBack);
        edtEmailPAdminLogin = findViewById(R.id.email_Padmin_login);
        edtEmailPAdminPass = findViewById(R.id.pass_admin_login);

        btnLoginPAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmailPAdminLogin.getText().toString().trim();
                String password = edtEmailPAdminPass.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(ProductAdminLoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            String userId = firebaseAuth.getCurrentUser().getUid();

                            firestore.collection("Users").document(userId).get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            String role = documentSnapshot.getString("role");
                                            if ("productadmin".equals(role)) {
                                                Toast.makeText(ProductAdminLoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(ProductAdminLoginActivity.this, ProductAdminActivity.class));
                                                finish();
                                            } else {
                                                firebaseAuth.signOut(); // prevents access for wrong roles
                                                Toast.makeText(ProductAdminLoginActivity.this, "Sorry, you are not a product admin", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            firebaseAuth.signOut();
                                            Toast.makeText(ProductAdminLoginActivity.this, "User role not found", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        firebaseAuth.signOut();
                                        Toast.makeText(ProductAdminLoginActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(ProductAdminLoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show(); // if username or password is incorrect
                        });
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductAdminLoginActivity.this, AdminSelectActivity.class);
                finish();
                Toast.makeText(ProductAdminLoginActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


