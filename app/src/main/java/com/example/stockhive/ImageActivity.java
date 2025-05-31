package com.example.stockhive;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ImageActivity extends AppCompatActivity {

    Button BACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ImageView imageView = findViewById(R.id.fullImageView);
        TextView productView= findViewById(R.id.fullproductname);
        TextView descriptionView = findViewById(R.id.fulldescription);
        TextView priceView = findViewById(R.id.fullprice);


        int imageResource = getIntent().getIntExtra("imageResource", -1);
        String productName = getIntent().getStringExtra("productName");
        String Description = getIntent().getStringExtra("Description");
        double price = getIntent().getDoubleExtra("price", 0.0);



        if (imageResource != -1) {

            imageView.setImageResource(imageResource);

        }
        productView.setText("Name:  " + productName);
        descriptionView.setText("Model Number: " + Description);
        String formattedPrice = String.format("%.2f", price);
        priceView.setText("Price: $"+ formattedPrice);


        BACK = findViewById(R.id.btn_back);

        BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });



    }
}
