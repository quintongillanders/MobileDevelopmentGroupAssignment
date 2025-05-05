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

public class ReportAdminLoginActivity extends AppCompatActivity {

    Button btnReportAdminLogin, btnBack;
    EditText repAdminLogin, repAdminPass;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_admin_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnReportAdminLogin = findViewById(R.id.ordReportLogin);
        btnBack = findViewById(R.id.btnBack);
        repAdminLogin = findViewById(R.id.email_reportadmin_login);
        repAdminPass = findViewById(R.id.pass_reportadmin_login);

        btnReportAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = repAdminLogin.getText().toString().trim();
                String password = repAdminPass.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(ReportAdminLoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            String userId = firebaseAuth.getCurrentUser().getUid();

                            firestore.collection("Users").document(userId).get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            String role = documentSnapshot.getString("role");
                                            if ("reportadmin".equals(role)) {
                                                Toast.makeText(ReportAdminLoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(ReportAdminLoginActivity.this, ReportAdminActivity.class));
                                                finish();
                                            } else {
                                                firebaseAuth.signOut(); // prevents access for wrong roles
                                                Toast.makeText(ReportAdminLoginActivity.this, "Sorry, you are not a report admin", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            firebaseAuth.signOut();
                                            Toast.makeText(ReportAdminLoginActivity.this, "User role not found", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        firebaseAuth.signOut();
                                        Toast.makeText(ReportAdminLoginActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(ReportAdminLoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show(); // if username or password is incorrect
                        });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportAdminLoginActivity.this, AdminSelectActivity.class);
                finish();
                Toast.makeText(ReportAdminLoginActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}