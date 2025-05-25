package com.example.stockhive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

public class ADApt1 extends RecyclerView.Adapter<HOLde1> {

    private int[] imageData;
    private String[] productname;
    private String[] description;
    private double[] price;
    private Context context;



    private int[] originalImageData;
    private String[] originalProductname;
    private String[] originalDescription;
    private double[] originalPrice;



    public ADApt1(int[] imageData, String[] productname, String[] description, double[] price, Context context) {
        this.imageData = imageData;
        this.productname = productname;
        this.description = description;
        this.price = price;
        this.context = context;


        this.originalImageData = imageData.clone();
        this.originalProductname = productname.clone();
        this.originalDescription = description.clone();
        this.originalPrice = price.clone();


    }



    @NonNull
    @Override
    public HOLde1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_view, parent, false);
        return new HOLde1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HOLde1 holder, int position) {
        holder.productView.setText("Product Name: " + productname[position]);
        holder.descriptionView.setText("Model: " + description[position]);
        holder.imageView.setImageResource(imageData[position]);
        holder.priceView.setText("Price: $" + String.format("%.2f", price[position]));



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getBindingAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.putExtra("imageResource", imageData[currentPosition]);
                    intent.putExtra("productName", productname[currentPosition]);
                    intent.putExtra("Description", description[currentPosition]);
                    intent.putExtra("price", price[currentPosition]);
                    context.startActivity(intent);


                }
            }
        });




        holder.btnCart.setOnClickListener(v -> {
            CartItem cartItem = new CartItem(imageData[position], productname[position], description[position], price[position]);
            ((Customer) context).addToCart(cartItem);
        });



    }



    @Override
    public int getItemCount() {

        return Math.min(Math.min(imageData.length, productname.length),
                Math.min(description.length, price.length));

    }

    public void filter(String text) {

        if (text == null || text.trim().isEmpty()) {
            resetData();
            return;
        }

        final String query = text.toLowerCase();

        new Thread(() -> {
            try {

                int count = originalProductname.length;

                ArrayList<Integer> tempImages = new ArrayList<>();
                ArrayList<String> tempNames = new ArrayList<>();
                ArrayList<String> tempDescriptions = new ArrayList<>();
                ArrayList<Double> tempPrices = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    if (originalProductname[i].toLowerCase().contains(query)) {
                        tempImages.add(originalImageData[i]);
                        tempNames.add(originalProductname[i]);
                        tempDescriptions.add(originalDescription[i]);
                        tempPrices.add(originalPrice[i]);
                    }
                }


                if (context instanceof Activity) {
                    ((Activity) context).runOnUiThread(() -> {
                        imageData = new int[tempImages.size()];
                        productname = new String[tempNames.size()];
                        description = new String[tempDescriptions.size()];
                        price = new double[tempPrices.size()];

                        for (int i = 0; i < tempImages.size(); i++) {
                            imageData[i] = tempImages.get(i);
                            productname[i] = tempNames.get(i);
                            description[i] = tempDescriptions.get(i);
                            price[i] = tempPrices.get(i);
                        }

                        notifyDataSetChanged();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void resetData() {

        imageData = originalImageData.clone();
        productname = originalProductname.clone();
        description = originalDescription.clone();
        price = originalPrice.clone();


        notifyDataSetChanged();
    }


}
