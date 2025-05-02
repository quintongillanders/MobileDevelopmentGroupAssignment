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

public class AdminSelectActivity extends AppCompatActivity {

    Button btnReportAdmin, btnProductAdmin, btnOrderAdmin, btnUserAdmin, btnBackToLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_select);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnReportAdmin = findViewById(R.id.btnReportAdmin);
        btnProductAdmin = findViewById(R.id.btnProductAdmin);
        btnOrderAdmin = findViewById(R.id.btnOrderAdmin);
        btnUserAdmin = findViewById(R.id.btnUserAdmin);
        btnBackToLogin = findViewById(R.id.btnBack);

        btnUserAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminSelectActivity.this, UserAdminLoginActivity.class);
                        startActivity(i);
                Toast.makeText(AdminSelectActivity.this, "User Admin clicked", Toast.LENGTH_SHORT).show();

            }
        });

        btnReportAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminSelectActivity.this, ReportAdminLoginActivity.class);
                startActivity(i);
                Toast.makeText(AdminSelectActivity.this, "Report Admin clicked", Toast.LENGTH_SHORT).show();

            }
        });

        btnOrderAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminSelectActivity.this, OrderAdminLoginActivity.class);
                startActivity(i);
                Toast.makeText(AdminSelectActivity.this, "Order Admin clicked", Toast.LENGTH_SHORT).show();

            }
        });

        btnProductAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminSelectActivity.this, ProductAdminLoginActivity.class);
                startActivity(i);
                Toast.makeText(AdminSelectActivity.this, "Product Admin clicked", Toast.LENGTH_SHORT).show();

            }
        });

        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminSelectActivity.this, MainActivity.class);
                startActivity(i);
                Toast.makeText(AdminSelectActivity.this, "Back to login clicked", Toast.LENGTH_SHORT).show();
            }
        });



    }
}