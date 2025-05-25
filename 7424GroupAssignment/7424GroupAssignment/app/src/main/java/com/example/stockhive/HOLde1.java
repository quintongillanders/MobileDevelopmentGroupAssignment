package com.example.stockhive;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HOLde1 extends RecyclerView.ViewHolder{
    TextView tv;
    ImageView imageView;

    public HOLde1(@NonNull View itemView) {
        super(itemView);

        tv = itemView.findViewById(R.id.c_name);
        imageView = itemView.findViewById(R.id.bike1);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(itemView.getContext(),
                        "Added to Cart",
                        Toast.LENGTH_LONG).show();

            }
        });

    }
}
