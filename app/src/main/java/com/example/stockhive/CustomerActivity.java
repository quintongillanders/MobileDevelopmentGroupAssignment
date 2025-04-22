package com.example.stockhive;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockhive.ADApt1;
import com.example.stockhive.R;

public class Customer extends AppCompatActivity {

    private Context context;

    RecyclerView recyclerView;


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
        ADApt1 adapter = new ADApt1(imageData, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}