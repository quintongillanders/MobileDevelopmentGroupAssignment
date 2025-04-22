package com.example.stockhive;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

public class CustomerActivity extends AppCompatActivity {

    private Context context;

    RecyclerView recyclerView;
    Button btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cust), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        String data[] = {"Bike1", "Bike2","Bike3","Bike4","Bike1", "Bike2","Bike3",
                "Bike4","Bike1", "Bike2","Bike3","Bike4","Bike1", "Bike2","Bike3","Bike4",
                "Bike1", "Bike2","Bike3","Bike4"};
        int[] imageData = {R.drawable.bike1, R.drawable.bike2, R.drawable.bike3, R.drawable.bike4,
                R.drawable.bike1, R.drawable.bike2, R.drawable.bike3, R.drawable.bike4,
                R.drawable.bike1, R.drawable.bike2, R.drawable.bike3, R.drawable.bike4,
                R.drawable.bike1, R.drawable.bike2, R.drawable.bike3, R.drawable.bike4,
                R.drawable.bike1, R.drawable.bike2, R.drawable.bike3, R.drawable.bike4,};

        recyclerView = findViewById(R.id.rec_view);
        btnLogout = findViewById(R.id.logoutbutton);
        ADApt1 adapter = new ADApt1(imageData, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(CustomerActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });

    }
}