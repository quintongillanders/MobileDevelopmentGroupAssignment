package com.example.stockhive;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button REGISTER, LoginButton;
    EditText EMail, PAss;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EMail = findViewById(R.id.email_login);
        PAss = findViewById(R.id.pass_login);
        LoginButton = findViewById(R.id.btnLogin);
        REGISTER = findViewById(R.id.btnRegister);

        fAuth = FirebaseAuth.getInstance();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = fAuth.getCurrentUser();
                            if (user != null) {
                                String uid = user.getUid();
                                checkUserEnabledStatus(uid, email);
                            }
                        } else {
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                            Toast.makeText(MainActivity.this, "Error! " + errorMessage, Toast.LENGTH_SHORT).show();
                            Log.d("LoginError", "Login failed: " + errorMessage);
                        }
                    }
                });
            }
        });

        REGISTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, REGpage.class);
                startActivity(i);
            }
        });
    }

    private void checkUserEnabledStatus(String uid, String email) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        userRef.child("enabled").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean enabled = snapshot.getValue(Boolean.class);

                if (enabled != null && enabled) {

                    if (email.endsWith("@supplier.com")) {
                        startActivity(new Intent(MainActivity.this, Supplier.class));
                    } else if (email.endsWith("@admin.com")) {
                        startActivity(new Intent(MainActivity.this, Admin.class));
                    } else {
                        startActivity(new Intent(MainActivity.this, Customer.class));
                    }
                    finish();
                } else {
                    fAuth.signOut();
                    Toast.makeText(MainActivity.this, "Your account is disabled. Contact admin.", Toast.LENGTH_LONG).show();
                    Log.d("UserStatus", "User disabled: " + email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Database error. Try again later.", Toast.LENGTH_SHORT).show();
                Log.d("DatabaseError", "Error while reading user status: " + error.getMessage());
            }
        });
    }
}
