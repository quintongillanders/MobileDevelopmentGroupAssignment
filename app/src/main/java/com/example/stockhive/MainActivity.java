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


public class MainActivity extends AppCompatActivity {
    Button btn_REGIS, btn_LOGIN, btnLoginAdmin;
    EditText edtEmail, edtPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_REGIS = findViewById(R.id.btnRegister);
        btn_LOGIN = findViewById(R.id.btnLogin);
        btnLoginAdmin = findViewById(R.id.btnAdmin);
        edtEmail = findViewById(R.id.email_login);
        edtPassword = findViewById(R.id.pass_login);

        btn_LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show(); // if one or more fields are blank
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                                    Toast.makeText(MainActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, CustomerActivity.class));
                                    finish();
                                })
                        .addOnFailureListener(e -> {
                            Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show(); // if username or password is incorrect
                                });
            }
        });

        btn_REGIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, REGpage.class);
                startActivity(i);
            }
        });

        btnLoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AdminSelectActivity.class);
                startActivity(i);
                Toast.makeText(MainActivity.this, "Login as admin clicked", Toast.LENGTH_SHORT).show();
            }
        });


    }
}