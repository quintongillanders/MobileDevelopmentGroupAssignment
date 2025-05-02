package com.example.stockhive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class REGpage extends AppCompatActivity {

    Button btnloginReg, btnregisterReg;
    EditText edtfullnameReg, edtemailaddReg, edtdobreg, edtpassReg, edtphnoreg, edtaddressReg;
    TextView txtdisplayRegInfo;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_regpage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtfullnameReg = findViewById(R.id.edtfullnameReg);
        edtemailaddReg = findViewById(R.id.edtemailaddReg);
        edtdobreg = findViewById(R.id.edtdobReg);
        edtpassReg = findViewById(R.id.edtpassReg);
        edtphnoreg = findViewById(R.id.edtphnoReg);
        edtaddressReg = findViewById(R.id.edtaddressReg);

        btnloginReg = findViewById(R.id.btnloginReg);
        btnregisterReg = findViewById(R.id.btnregisterReg);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnregisterReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = edtfullnameReg.getText().toString().trim();
                String email = edtemailaddReg.getText().toString().trim();
                String dob = edtdobreg.getText().toString().trim();
                String password = edtpassReg.getText().toString().trim();
                String phone = edtphnoreg.getText().toString().trim();
                String address = edtaddressReg.getText().toString().trim();

                if (fullName.isEmpty() || email.isEmpty() || dob.isEmpty() || password.isEmpty() || phone.isEmpty() ||
                        address.isEmpty()) {
                    Toast.makeText(REGpage.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            String uid = firebaseAuth.getUid();
                            Map<String, Object> consumer = new HashMap<>();
                            consumer.put("fullName", fullName);
                            consumer.put("email", email);
                            consumer.put("dob", dob);
                            consumer.put("phone", phone);
                            consumer.put("address", address);
                            consumer.put("role", "consumer");

                            db.collection("Users")
                                    .document(uid)
                                    .set(consumer)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(REGpage.this, "Registration Success!", Toast.LENGTH_SHORT).show(); // Successfully registered new user
                                        txtdisplayRegInfo.setText("Welcome, " + fullName + "!"); //  welcome the new user
                                        clearFields();
                                        startActivity(new Intent(REGpage.this, MainActivity.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(REGpage.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(REGpage.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        btnloginReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(REGpage.this, "Back to login clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
    edtfullnameReg.setText("");
    edtemailaddReg.setText("");
    edtdobreg.setText("");
    edtphnoreg.setText("");
    edtaddressReg.setText("");

    }
}












