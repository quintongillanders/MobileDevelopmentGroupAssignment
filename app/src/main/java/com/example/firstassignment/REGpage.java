package com.example.firstassignment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class REGpage extends AppCompatActivity {

    EditText FullName, EMail, DOB, PAss, PHone, ADDress;
    Button btn_Register, btn_Logi;
    FirebaseAuth fAuth;

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

        FullName = findViewById(R.id.edtfullnameReg);
        EMail = findViewById(R.id.edtemailaddReg);
        DOB = findViewById(R.id.edtdobReg);
        PAss = findViewById(R.id.edtpassReg);
        PHone = findViewById(R.id.edtphnoReg);
        ADDress = findViewById(R.id.edtaddressReg);
        btn_Register = findViewById(R.id.btnregisterReg);
        btn_Logi = findViewById(R.id.btnloginReg);

        fAuth = FirebaseAuth.getInstance();

        btn_Logi.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Customer.class));
            finish();
        }

        btn_Register.setOnClickListener(v -> {
            String email = EMail.getText().toString().trim();
            String password = PAss.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                EMail.setError("Email is required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                PAss.setError("Password is required");
                return;
            }

            if (password.length() < 6) {
                PAss.setError("Password must be at least 6 characters");
                return;
            }

            fAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String uid = fAuth.getCurrentUser().getUid();
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");

                            HashMap<String, Object> userData = new HashMap<>();
                            userData.put("fullname", FullName.getText().toString());
                            userData.put("email", email);
                            userData.put("dob", DOB.getText().toString());
                            userData.put("phone", PHone.getText().toString());
                            userData.put("address", ADDress.getText().toString());
                            userData.put("role", getUserRole(email));
                            userData.put("enabled", true);

                            dbRef.child(uid).setValue(userData).addOnCompleteListener(saveTask -> {
                                if (saveTask.isSuccessful()) {
                                    Toast.makeText(REGpage.this, "User registered and saved!", Toast.LENGTH_SHORT).show();

                                    String role = getUserRole(email);
                                    Intent intent;
                                    if (role.equals("supplier")) {
                                        intent = new Intent(getApplicationContext(), Supplier.class);
                                    } else if (role.equals("admin")) {
                                        intent = new Intent(getApplicationContext(), Admin.class);
                                    } else {
                                        intent = new Intent(getApplicationContext(), Customer.class);
                                    }

                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(REGpage.this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
                                    Log.e("FirebaseSave", "Failed to write user data: " + saveTask.getException());
                                }
                            });

                        } else {
                            String err = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                            Toast.makeText(REGpage.this, "Error! " + err, Toast.LENGTH_SHORT).show();
                            Log.e("FirebaseAuth", "Registration failed: " + err);
                        }
                    });
        });
    }

    private String getUserRole(String email) {
        if (email.endsWith("@admin.com")) return "admin";
        else if (email.endsWith("@supplier.com")) return "supplier";
        else return "customer";
    }
}