package com.example.stockhive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtEmail, edtPass;
    private Button  btnRegister, btnBack;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.email_login);
        edtPass = findViewById(R.id.pass_login);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btn_back);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               String email = edtEmail.getText().toString().trim();
                                               String password = edtPass.getText().toString().trim();

                                               if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                                                   Toast.makeText(RegisterActivity.this, "Please enter both username and password to register", Toast.LENGTH_SHORT).show();
                                                   return;
                                               }

                                               auth.createUserWithEmailAndPassword(email, password)
                                                       .addOnCompleteListener(task -> {
                                                           if (task.isSuccessful()) {
                                                               FirebaseUser user = auth.getCurrentUser();
                                                               Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();

                                                               if (email.endsWith("@admin.com")) {
                                                                   startActivity(new Intent(RegisterActivity.this, AdminActivity.class));
                                                               } else if (email.endsWith("@supplier.com")) {
                                                                   startActivity(new Intent(RegisterActivity.this, SupplierActivity.class));
                                                               } else {
                                                                   startActivity(new Intent(RegisterActivity.this, CustomerActivity.class));
                                                               }

                                                               finish();
                                                           } else {
                                                               Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                           }

                                                       });
                                           }
                                       });


                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        Toast.makeText(RegisterActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        }








